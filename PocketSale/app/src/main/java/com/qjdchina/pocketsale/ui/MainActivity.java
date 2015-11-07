package com.qjdchina.pocketsale.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.qjdchina.pocketsale.R;
import com.qjdchina.pocketsale.biz.MemberApplyManager;
import com.qjdchina.pocketsale.biz.impl.MemberApplyManagerImpl;
import com.qjdchina.pocketsale.commons.Constants;
import com.qjdchina.pocketsale.commons.FileManage;
import com.qjdchina.pocketsale.commons.PropertiesUtil;
import com.qjdchina.pocketsale.commons.enums.MemberAuthFile;
import com.qjdchina.pocketsale.commons.enums.MessageType;
import com.qjdchina.pocketsale.commons.enums.ResultCodes;
import com.qjdchina.pocketsale.dto.MemberApplyBo;
import com.qjdchina.pocketsale.handler.LoginResponseHandler;
import com.qjdchina.pocketsale.handler.MemberApplyQueryResponseHandler;
import com.qjdchina.pocketsale.handler.MemberRatingQueryResponseHandler;
import com.qjdchina.pocketsale.handler.MemberRatingResponseHandler;
import com.qjdchina.pocketsale.handler.UploadMaterialQueryResponseHandler;
import com.qjdchina.pocketsale.handler.UploadMaterialResponseHandler;
import com.qjdchina.pocketsale.handler.UploadResponseHandler;
import com.qjdchina.pocketsale.model.MemberApplyParcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity implements OnClickListener {

    private static final String TAG = "MainActivity";

    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);

        Button btn = (Button) findViewById(R.id.btn_jump);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        Button btnMember = (Button) findViewById(R.id.btn_jump_memberlist);
        btnMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MemberListActivity.class));
            }
        });

        Button btnInfo = (Button) findViewById(R.id.btn_jump_memberinfo);
        try {
            btnInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent i = new Intent(MainActivity.this, MemberDetailActivity.class);
                    startActivity(i);

                }
            });
        } catch (Exception e) {
            Log.e(TAG, e.toString());

        }
    }

    /**
     * 查询会员列表
     */
    private void search() {
        // 需要在当前主线程：UI线程中启动新线程调用接口
        Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == MessageType.MEMAPP_QUERY.getType()) {
                    Bundle bundle = msg.getData();
                    String resultCode = bundle.getString(Constants.RESULT_CODE);
                    String resultMessage = bundle.getString(Constants.RESULT_MESSAGE);
                    if (ResultCodes.SUCCESS.getCode().equals(resultCode)) {
                        MemberApplyParcelable applyParcelable = bundle.getParcelable(Constants.MEMBER_APPLY_RESULT);
                        List<MemberApplyBo> memberApplyBos = applyParcelable.getMemApplyBos();
                        if (memberApplyBos != null) {
                            for (Iterator<MemberApplyBo> iter = memberApplyBos.iterator(); iter.hasNext(); ) {
                                Log.v(TAG, iter.next().toString());
                            }
                            Toast.makeText(MainActivity.this, "共查询" + memberApplyBos.size() + "条记录", Toast.LENGTH_LONG)
                                    .show();
                        }
                    } else {

                        Toast.makeText(MainActivity.this, "查询失败：" + resultMessage, Toast.LENGTH_LONG).show();
                    }
                }
                return false;
            }
        });

        MemberApplyQueryResponseHandler queryResponseHandler = new MemberApplyQueryResponseHandler(handler);

        MemberApplyManager memberApplyManager = new MemberApplyManagerImpl();
        memberApplyManager.findMemberApplyBoListByOperatorId("1", 1, queryResponseHandler);
    }

    /**
     * 登录
     *
     * @param userId   登录账号
     * @param password 登录密码
     */
    private void login(String userId, String password) {
        Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == MessageType.LOGIN.getType()) {
                    Bundle bundle = msg.getData();
                    String resultCode = bundle.getString(Constants.RESULT_CODE);
                    Log.v(TAG, resultCode);

                    String resultMsg = bundle.getString(Constants.RESULT_MESSAGE);
                    Toast.makeText(MainActivity.this, resultMsg, Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });
        LoginResponseHandler loginResponseHandler = new LoginResponseHandler(handler);
        MemberApplyManager memberApplyManager = new MemberApplyManagerImpl();
        memberApplyManager.doLogin(userId, password, loginResponseHandler);
    }

    /**
     * 文件上传
     *
     * @param memAppId     会员申请ID
     * @param fileCateCode 认证资料类别编码
     */
    private void upload(final Integer memAppId, final String fileCateCode) {
        final MemberApplyManager memberApplyManager = new MemberApplyManagerImpl();
        // 单独上传文件handler
        Handler uploadHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == MessageType.UPLOAD_FILE.getType()) {
                    Bundle bundle = msg.getData();
                    String uploadResultCode = bundle.getString(Constants.RESULT_CODE);
                    String uploadResultMsg = bundle.getString(Constants.RESULT_MESSAGE);
                    if (ResultCodes.SUCCESS.getCode().equals(uploadResultCode)) {
                        // 文件单独上传成功后，再调用会员的上传接口，绑定文件与认证资料的关系
                        Handler materialHandler = new Handler(new Handler.Callback() {
                            @Override
                            public boolean handleMessage(Message msg) {
                                if (msg.what == MessageType.UPLOAD_MATERIAL.getType()) {
                                    Bundle bundle = msg.getData();
                                    String uploadMaterialResultCode = bundle.getString(Constants.RESULT_CODE);
                                    String uploadMaterialResultMsg = bundle.getString(Constants.RESULT_MESSAGE);
                                    // 会员文件上传成功
                                    if (ResultCodes.SUCCESS.getCode().equals(uploadMaterialResultCode)) {
                                        // 上传成功后返回文件路径
                                        String serverFilePath = bundle.getString(Constants.SERVER_MATERIAL_FILE_PATH);
                                        Toast.makeText(MainActivity.this, serverFilePath, Toast.LENGTH_LONG).show();
                                    } else {
                                        // 会员文件上传失败
                                        Toast.makeText(MainActivity.this, uploadMaterialResultMsg, Toast.LENGTH_LONG)
                                                .show();
                                    }
                                }
                                return false;
                            }
                        });

                        final UploadMaterialResponseHandler materialResponseHandler = new UploadMaterialResponseHandler(
                                materialHandler);

                        String serverFilePath = bundle.getString(Constants.SERVER_FILE_PATH);
                        memberApplyManager.doUploadMemberAuthFile(memAppId, fileCateCode, serverFilePath,
                                materialResponseHandler);
                    } else {
                        // 文件上传失败
                        Log.v(TAG, uploadResultMsg);
                        Toast.makeText(MainActivity.this, uploadResultMsg, Toast.LENGTH_LONG).show();
                    }
                }
                return false;
            }
        });

        UploadResponseHandler uploadResponseHandler = new UploadResponseHandler(uploadHandler);
        FileManage fileManage = new FileManage();
        String baseUrl = PropertiesUtil.getProperty("cif_fnt_server_base_url");
        String uploadUrl = PropertiesUtil.getProperty("upload_common_file_url");

        fileManage.setUrl(baseUrl + uploadUrl);
        String externalPath = Environment.getExternalStorageDirectory().getPath();
        // 测试不支持的文件格式上传
        // String filePath = "/Pictures/test.vm";
        String filePath = "/Pictures/test_upload.png";
        String filePath2 = "/Pictures/test_upload2.png";
        // fileManage.doUpload(externalPath + filePath, uploadResponseHandler);
        List<String> fileLists = new ArrayList<String>();
        fileLists.add(externalPath + filePath);
        fileLists.add(externalPath + filePath2);
        fileManage.doBatchUpload(fileLists, uploadResponseHandler);
    }

    /**
     * 查询上传图片记录
     *
     * @param memAppId     会员申请ID
     * @param fileCateCode 认证资料类别编码
     */
    private void queryUploadMaterial(Integer memAppId, String fileCateCode)

    {
        // 需要在当前主线程：UI线程中启动新线程调用接口
        Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == MessageType.QUERY_UPLOAD.getType()) {
                    Bundle bundle = msg.getData();
                    String resultCode = bundle.getString(Constants.RESULT_CODE);
                    String resultMessage = bundle.getString(Constants.RESULT_MESSAGE);
                    if (ResultCodes.SUCCESS.getCode().equals(resultCode)) {
                        MemberAuthFile[] MemberAuthFiles = MemberAuthFile.values();
                        for (MemberAuthFile memberAuthFile : MemberAuthFiles) {
                            ArrayList<String> filePaths = bundle.getStringArrayList(memberAuthFile.getValue());
                            if (filePaths != null && !filePaths.isEmpty()) {
                                Log.v(TAG, filePaths.toString());
                                String text = "查询上传记录：" + memberAuthFile.getValue() + ", " + filePaths.size() + "条";
                                Toast.makeText(MainActivity.this, text, Toast.LENGTH_LONG).show();
                            }
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "查询上传记录失败：" + resultMessage, Toast.LENGTH_LONG).show();
                    }
                }
                return false;
            }
        });

        UploadMaterialQueryResponseHandler uploadMaterialQueryResponseHandler = new UploadMaterialQueryResponseHandler(handler);

        MemberApplyManager memberApplyManager = new MemberApplyManagerImpl();
        memberApplyManager.findMemberAuthFileUploadsByCondition(memAppId, fileCateCode, uploadMaterialQueryResponseHandler);
    }

    /**
     * 评分
     *
     * @param companyId
     */
    private void doRating(String companyId) {
        // 需要在当前主线程：UI线程中启动新线程调用接口
        Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == MessageType.RATING.getType()) {
                    Bundle bundle = msg.getData();
                    String resultCode = bundle.getString(Constants.RESULT_CODE);
                    String resultMessage = bundle.getString(Constants.RESULT_MESSAGE);
                    if (ResultCodes.SUCCESS.getCode().equals(resultCode)) {
                        String mark_sale_score = bundle.getString(Constants.MEMBER_MARK_SALE_SCORE);
                        String mark_capital_score = bundle.getString(Constants.MEMBER_MARK_CAPITAL_SCORE);
                        String mark_years_score = bundle.getString(Constants.MEMBER_MARK_YEARS_SCORE);
                        String mark_experience_score = bundle.getString(Constants.MEMBER_MARK_EXPERIENCE_SCORE);
                        String mark_size_score = bundle.getString(Constants.MEMBER_MARK_SIZE_SCORE);
                        String mark_worth_score = bundle.getString(Constants.MEMBER_MARK_WORTH_SCORE);
                        StringBuilder builder = new StringBuilder().append("年销量评分值: ").append(mark_sale_score).append(",").append("注册资本评分值: ").append(mark_capital_score).append(",")
                                .append("成立年限评分值: ").append(mark_years_score).append(",").append("管理者行业经验评分值: ").append(mark_experience_score).append(",")
                                .append("团队规模评分值: ").append(mark_size_score).append(",").append("家庭净资产评分值: ").append(mark_worth_score);
                        Toast.makeText(MainActivity.this, "评分结果：" + builder.toString(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MainActivity.this, "评分失败：" + resultMessage, Toast.LENGTH_LONG).show();
                    }
                }
                return false;
            }
        });

        MemberRatingResponseHandler memberRatingResponseHandler = new MemberRatingResponseHandler(handler);
        MemberApplyManager memberApplyManager = new MemberApplyManagerImpl();
        Map<String, Object> params = new HashMap<String, Object>();//参数封装 评分项-评分值
        params.put(Constants.MEMBER_MARK_SALE, "10");
        params.put(Constants.MEMBER_MARK_CAPITAL, "4");
        params.put(Constants.MEMBER_MARK_YEARS, "3");
        params.put(Constants.MEMBER_MARK_EXPERIENCE, "4");
        params.put(Constants.MEMBER_MARK_SIZE, "10");
        params.put(Constants.MEMBER_MARK_WORTH, "6");

        memberApplyManager.doMemberRating(companyId, params, memberRatingResponseHandler);
    }

    /**
     * 查询评分结果
     *
     * @param companyId
     */
    private void queryRating(String companyId) {
        // 需要在当前主线程：UI线程中启动新线程调用接口
        Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == MessageType.QUERY_RATING.getType()) {
                    Bundle bundle = msg.getData();
                    String resultCode = bundle.getString(Constants.RESULT_CODE);
                    String resultMessage = bundle.getString(Constants.RESULT_MESSAGE);
                    if (ResultCodes.SUCCESS.getCode().equals(resultCode)) {
                        String mark_sale_score = bundle.getString(Constants.MEMBER_MARK_SALE_SCORE);
                        String mark_capital_score = bundle.getString(Constants.MEMBER_MARK_CAPITAL_SCORE);
                        String mark_years_score = bundle.getString(Constants.MEMBER_MARK_YEARS_SCORE);
                        String mark_experience_score = bundle.getString(Constants.MEMBER_MARK_EXPERIENCE_SCORE);
                        String mark_size_score = bundle.getString(Constants.MEMBER_MARK_SIZE_SCORE);
                        String mark_worth_score = bundle.getString(Constants.MEMBER_MARK_WORTH_SCORE);
                        StringBuilder builder = new StringBuilder().append("年销量评分值: ").append(mark_sale_score).append(",").append("注册资本评分值: ").append(mark_capital_score).append(",")
                                .append("成立年限评分值: ").append(mark_years_score).append(",").append("管理者行业经验评分值: ").append(mark_experience_score).append(",")
                                .append("团队规模评分值: ").append(mark_size_score).append(",").append("家庭净资产评分值: ").append(mark_worth_score);
                        Toast.makeText(MainActivity.this, "查询评分结果：" + builder.toString(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MainActivity.this, "评分失败：" + resultMessage, Toast.LENGTH_LONG).show();
                    }
                }
                return false;
            }
        });

        MemberRatingQueryResponseHandler memberRatingQueryResponseHandler = new MemberRatingQueryResponseHandler(handler);
        MemberApplyManager memberApplyManager = new MemberApplyManagerImpl();
        memberApplyManager.findMemberRatingResult(companyId, memberRatingQueryResponseHandler);
    }

    @Override
    public void onClick(View v) {
        // 1.测试登录
//        login("wangzheng", "wangzheng");


        // 2.测试会员申请列表查询
//        search();

        // 3.测试文件上传
//        upload(62, MemberAuthFile.C10001.getValue());

        // 4.测试文件上传记录查询
//        queryUploadMaterial(62, MemberAuthFile.C10001.getValue());

        //5.测试提交评分数据
        doRating("201511060100010001DMW90000000001");

        //6.测试评分记录查询
//        queryRating("201511060100010001DMW90000000001");
    }
}
