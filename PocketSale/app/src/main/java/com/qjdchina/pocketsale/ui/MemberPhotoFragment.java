package com.qjdchina.pocketsale.ui;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.qjdchina.pocketsale.R;
import com.qjdchina.pocketsale.biz.MemberApplyManager;
import com.qjdchina.pocketsale.biz.impl.MemberApplyManagerImpl;
import com.qjdchina.pocketsale.biz.util.SDUtil;
import com.qjdchina.pocketsale.commons.Constants;
import com.qjdchina.pocketsale.commons.FileManage;
import com.qjdchina.pocketsale.commons.PropertiesUtil;
import com.qjdchina.pocketsale.commons.enums.MemberAuthFile;
import com.qjdchina.pocketsale.commons.enums.MessageType;
import com.qjdchina.pocketsale.commons.enums.ResultCodes;
import com.qjdchina.pocketsale.dto.MemberPhotoBo;
import com.qjdchina.pocketsale.handler.UploadMaterialQueryResponseHandler;
import com.qjdchina.pocketsale.handler.UploadMaterialResponseHandler;
import com.qjdchina.pocketsale.handler.UploadResponseHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.qjdchina.pocketsale.biz.util.ImageUtil.bitmap2File;
import static com.qjdchina.pocketsale.biz.util.ImageUtil.getImage;

public class MemberPhotoFragment extends Fragment implements View.OnClickListener {
    private String TAG = "MemberPictureFragment";
    private View rootView;
    private Bitmap bitmap;

    //company_license1
    private ImageView ivCompanyLicense1;
    //organization_code
    private ImageView ivOrganizationcode1;
    //other_certificates
    private ImageView ivOtherCertificates1;
    private ImageView ivOtherCertificates2;
    private ImageView ivOtherCertificates3;
    private ImageView ivOtherCertificates4;
    private ImageView ivOtherCertificates5;
    private ImageView ivOtherCertificates6;

    private ImageView ivDialog;

    private byte operationFlag = 0000;
    public static final int REQUEST_CODE_TAKE_PICTURE = 12;
    public static String FOLDER_SAVE_PATH = "/sdcard/pocketsale/"; //图片及其他数据保存文件夹
    public static String FILE_COMPANYLICENSE1 = "license1.jpeg";
    public static String FILE_PC_COMPANYLICENSE1 = "license1_pc.jpeg";
    public static String FILE_PHONE_COMPANYLICENSE1 = "license1_phone.jpeg";
    public static String FILE_ORGANIZATIONCODE1 = "organization1.jpeg";
    public static String FILE_PC_ORGANIZATIONCODE1 = "organization1_pc.jpeg";
    public static String FILE_PHONE_ORGANIZATIONCODE1 = "organization1_phone.jpeg";
    public static String FILE_OTHERCERTIFICATES1 = "other1.jpeg";
    public static String FILE_PC_OTHERCERTIFICATES1 = "other1_pc.jpeg";
    public static String FILE_PHONE_OTHERCERTIFICATES1 = "other1_phone.jpeg";
    public static String FILE_OTHERCERTIFICATES2 = "other2.jpeg";
    public static String FILE_PC_OTHERCERTIFICATES2 = "other2_pc.jpeg";
    public static String FILE_PHONE_OTHERCERTIFICATES2 = "other2_phone.jpeg";
    public static String FILE_OTHERCERTIFICATES3 = "other3.jpeg";
    public static String FILE_PC_OTHERCERTIFICATES3 = "other3_pc.jpeg";
    public static String FILE_PHONE_OTHERCERTIFICATES3 = "other3_phone.jpeg";
    public static String FILE_OTHERCERTIFICATES4 = "other4.jpeg";
    public static String FILE_PC_OTHERCERTIFICATES4 = "other4_pc.jpeg";
    public static String FILE_PHONE_OTHERCERTIFICATES4 = "other4_phone.jpeg";
    public static String FILE_OTHERCERTIFICATES5 = "other5.jpeg";
    public static String FILE_PC_OTHERCERTIFICATES5 = "other5_pc.jpeg";
    public static String FILE_PHONE_OTHERCERTIFICATES5 = "other5_phone.jpeg";
    public static String FILE_OTHERCERTIFICATES6 = "other6.jpeg";
    public static String FILE_PC_OTHERCERTIFICATES6 = "other6_pc.jpeg";
    public static String FILE_PHONE_OTHERCERTIFICATES6 = "other6_phone.jpeg";
    public static String FILE_JSON = "photo.json";
    private String companyID;
    private Dialog dialog;
    private int id;
    private List<String> licenseFiles;
    private List<String> organizetionFiles;
    private List<String> otherFiles;
    private Button btnSubmit;
    private MemberPhotoBo mpb_license1;
    private MemberPhotoBo mpb_organization1;
    private MemberPhotoBo mpb_other1;
    private MemberPhotoBo mpb_other2;
    private MemberPhotoBo mpb_other3;
    private MemberPhotoBo mpb_other4;
    private MemberPhotoBo mpb_other5;
    private MemberPhotoBo mpb_other6;
    private List<MemberPhotoBo> mpbs;
    private MemberPhotoFragmentReadJson mpbRead;
    private MemberPhotoFragmentWriteJson mpbWrite;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_member_photo, container, false);
        initParameters(rootView);
        return rootView;
    }

    /*
    *初始化各参数
    */
    private void initParameters(View view) {
        //company_license1
        ivCompanyLicense1 = (ImageView) initParameter(view, R.id.iv_company_license_1);
        //organizationCode
        ivOrganizationcode1 = (ImageView) initParameter(view, R.id.iv_organization_code_1);
        //otherCertificates
        ivOtherCertificates1 = (ImageView) initParameter(view, R.id.iv_other_certificates_1);
        ivOtherCertificates2 = (ImageView) initParameter(view, R.id.iv_other_certificates_2);
        ivOtherCertificates3 = (ImageView) initParameter(view, R.id.iv_other_certificates_3);
        ivOtherCertificates4 = (ImageView) initParameter(view, R.id.iv_other_certificates_4);
        ivOtherCertificates5 = (ImageView) initParameter(view, R.id.iv_other_certificates_5);
        ivOtherCertificates6 = (ImageView) initParameter(view, R.id.iv_other_certificates_6);
        btnSubmit = (Button) rootView.findViewById(R.id.btn_photo_submit);
        btnSubmit.setOnClickListener(this);

        Intent intent = getActivity().getIntent();
        Bundle bundle = intent.getExtras();
        companyID = bundle.getString("companyID");
        id = Integer.parseInt(bundle.getString("id"));
        FOLDER_SAVE_PATH = FOLDER_SAVE_PATH + companyID + "/";

        mpb_license1 = new MemberPhotoBo();
        mpb_organization1 = new MemberPhotoBo();
        mpb_other1 = new MemberPhotoBo();
        mpb_other2 = new MemberPhotoBo();
        mpb_other3 = new MemberPhotoBo();
        mpb_other4 = new MemberPhotoBo();
        mpb_other5 = new MemberPhotoBo();
        mpb_other6 = new MemberPhotoBo();
        mpbs = new ArrayList<MemberPhotoBo>();
        mpbRead=new MemberPhotoFragmentReadJson(FOLDER_SAVE_PATH +FILE_JSON);
        List<MemberPhotoBo> mpbList=mpbRead.getJsonData(companyID);
        mpbs.add(mpb_license1);
        mpbs.add(mpb_organization1);
        mpbs.add(mpb_other1);
        mpbs.add(mpb_other2);
        mpbs.add(mpb_other3);
        mpbs.add(mpb_other4);
        mpbs.add(mpb_other5);
    }

    /*
     *初始化单一参数
     */
    private View initParameter(View rootView, int id) {
        View view;
        view = rootView.findViewById(id);
        //Tag0:not picture yet;Tag1:have picture in this tv
        view.setTag(0);
        view.setOnClickListener(this);
        return view;
    }

    /*
    *处理点击事件
    */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_company_license_1:
                operationFlag = 0001;
                //存储为原图路径
                clickProcess(ivCompanyLicense1, FOLDER_SAVE_PATH, FILE_COMPANYLICENSE1);
                break;
            case R.id.iv_organization_code_1:
                operationFlag = 0011;
                clickProcess(ivOrganizationcode1, FOLDER_SAVE_PATH, FILE_ORGANIZATIONCODE1);
                break;
            case R.id.iv_other_certificates_1:
                operationFlag = 0021;
                clickProcess(ivOtherCertificates1, FOLDER_SAVE_PATH, FILE_OTHERCERTIFICATES1);
                break;
            case R.id.iv_other_certificates_2:
                operationFlag = 0022;
                clickProcess(ivOtherCertificates2, FOLDER_SAVE_PATH, FILE_OTHERCERTIFICATES2);
                break;
            case R.id.iv_other_certificates_3:
                operationFlag = 0023;
                clickProcess(ivOtherCertificates3, FOLDER_SAVE_PATH, FILE_OTHERCERTIFICATES3);
                break;
            case R.id.iv_other_certificates_4:
                operationFlag = 0024;
                clickProcess(ivOtherCertificates4, FOLDER_SAVE_PATH, FILE_OTHERCERTIFICATES4);
                break;
            case R.id.iv_other_certificates_5:
                operationFlag = 0025;
                clickProcess(ivOtherCertificates5, FOLDER_SAVE_PATH, FILE_OTHERCERTIFICATES5);
                break;
            case R.id.iv_other_certificates_6:
                operationFlag = 0026;
                clickProcess(ivOtherCertificates6, FOLDER_SAVE_PATH, FILE_OTHERCERTIFICATES6);
                break;
            case R.id.iv_dialog_full:
                dialog.dismiss();
                break;
            case R.id.btn_photo_submit:
                mpbWrite=new MemberPhotoFragmentWriteJson(mpbs);
                mpbWrite.setFilePath(FOLDER_SAVE_PATH+FILE_JSON);
                String jsonData=mpbWrite.getJsonData(companyID);

//                if (ivCompanyLicense1.getTag().equals(1)) {
//                    licenseFiles = new ArrayList<String>();
//                    licenseFiles.add(FOLDER_SAVE_PATH + FILE_PC_COMPANYLICENSE1);
//                    upload(id, "C10001", licenseFiles);
//                }
//                if (ivOrganizationcode1.getTag().equals(1)) {
//                    organizetionFiles = new ArrayList<String>();
//                    organizetionFiles.add(FOLDER_SAVE_PATH + FILE_PC_ORGANIZATIONCODE1);
//                    upload(id, "C10002", organizetionFiles);
//                }
//                otherFiles = new ArrayList<String>();
//                if (ivOtherCertificates1.getTag().equals(1)) {
//                    otherFiles.add(FOLDER_SAVE_PATH + FILE_PC_OTHERCERTIFICATES1);
//                }
//                if (ivOtherCertificates2.getTag().equals(1)) {
//                    otherFiles.add(FOLDER_SAVE_PATH + FILE_PC_OTHERCERTIFICATES2);
//                }
//                if (ivOtherCertificates3.getTag().equals(1)) {
//                    otherFiles.add(FOLDER_SAVE_PATH + FILE_PC_OTHERCERTIFICATES3);
//                }
//                if (ivOtherCertificates4.getTag().equals(1)) {
//                    otherFiles.add(FOLDER_SAVE_PATH + FILE_PC_OTHERCERTIFICATES4);
//                }
//                if (ivOtherCertificates5.getTag().equals(1)) {
//                    otherFiles.add(FOLDER_SAVE_PATH + FILE_PC_OTHERCERTIFICATES5);
//                }
//                if (ivOtherCertificates6.getTag().equals(1)) {
//                    otherFiles.add(FOLDER_SAVE_PATH + FILE_PC_OTHERCERTIFICATES6);
//                }
//                upload(id, "C10004", otherFiles);
                break;
        }
    }

    /*
    *处理单一点击事件
    */
    private void clickProcess(View view, String folder, String file) {
        if (view.getTag().equals(0)) {
            startCamera(folder, file);
        } else {
            //Dialog dialog = new MemberPhotoDialog(getActivity());
            dialog = new MemberPhotoDialog(getActivity(), R.style.dialog_full_screen);
            dialog.show();
            ivDialog = (ImageView) dialog.findViewById(R.id.iv_dialog_full);
            ivDialog.setOnClickListener(this);
            Bitmap bp = getImage(folder + file);
            ivDialog.setImageBitmap(bp);
            //进度条
            //ProgressBar pb= (ProgressBar) dialog.findViewById(R.id.pb_dialog_member);
            //pb.setVisibility(View.INVISIBLE);
        }
    }

    /*

    启动相机
    */
    private void startCamera(String folder, String file) {
        if (SDUtil.isHasSdcard()) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File imgFolder = new File(folder);
            if (!imgFolder.exists()) {
                imgFolder.mkdirs();
            }
            File imgFile = new File(folder, file);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imgFile));
            startActivityForResult(intent, REQUEST_CODE_TAKE_PICTURE);
        } else {
            Toast.makeText(getActivity(), "SD卡未准备好",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /*
    相机回送数据
    */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        processPictures(requestCode, resultCode, data);
    }

    /*
    *全部图片处理
    */
    private Bitmap bpPC;
    private Bitmap bpPhone;

    public void processPictures(int requestCode, int resultCode, Intent intent) {
        Log.d(TAG, "Capture picture successfully");
        if (requestCode == REQUEST_CODE_TAKE_PICTURE && resultCode == Activity.RESULT_OK) {
            switch (operationFlag) {
                case 0001:
                    processPicture(FILE_COMPANYLICENSE1, FILE_PC_COMPANYLICENSE1, FILE_PHONE_COMPANYLICENSE1, ivCompanyLicense1,mpb_license1);
                    break;
                case 0011:
                    processPicture(FILE_ORGANIZATIONCODE1, FILE_PC_ORGANIZATIONCODE1, FILE_PHONE_ORGANIZATIONCODE1, ivOrganizationcode1,mpb_organization1);
                    break;
                case 0021:
                    processPicture(FILE_OTHERCERTIFICATES1, FILE_PC_OTHERCERTIFICATES1, FILE_PHONE_OTHERCERTIFICATES1, ivOtherCertificates1,mpb_other1);
                    ivOtherCertificates2.setVisibility(View.VISIBLE);
                    break;
                case 0022:
                    processPicture(FILE_OTHERCERTIFICATES2, FILE_PC_OTHERCERTIFICATES2, FILE_PHONE_OTHERCERTIFICATES2, ivOtherCertificates2,mpb_other2);
                    ivOtherCertificates3.setVisibility(View.VISIBLE);
                    break;
                case 0023:
                    processPicture(FILE_OTHERCERTIFICATES3, FILE_PC_OTHERCERTIFICATES3, FILE_PHONE_OTHERCERTIFICATES3, ivOtherCertificates3,mpb_other3);
                    ivOtherCertificates4.setVisibility(View.VISIBLE);
                    break;
                case 0024:
                    processPicture(FILE_OTHERCERTIFICATES4, FILE_PC_OTHERCERTIFICATES4, FILE_PHONE_OTHERCERTIFICATES4, ivOtherCertificates4,mpb_other4);
                    ivOtherCertificates5.setVisibility(View.VISIBLE);
                    break;
                case 0025:
                    processPicture(FILE_OTHERCERTIFICATES5, FILE_PC_OTHERCERTIFICATES5, FILE_PHONE_OTHERCERTIFICATES5, ivOtherCertificates5,mpb_other5);
                    break;
            }
        }
    }

    /*
    *单一图片处理
    * pc端为512kb
    * phone端为128kb
    */
    public void processPicture(String file, String filePC, String filePhone, ImageView iv,MemberPhotoBo mpb) {
        bpPC = getImage(FOLDER_SAVE_PATH + file, 512);
        bpPhone = getImage(FOLDER_SAVE_PATH + file, 128);
        iv.setImageBitmap(bpPhone);
        iv.setTag(1);
        bitmap2File(bpPC, FOLDER_SAVE_PATH + filePC);
        bitmap2File(bpPhone, FOLDER_SAVE_PATH + filePhone);
        mpb.setLocalFile(FOLDER_SAVE_PATH + filePC);
    }

    /**
     * 单项文件上传
     *
     * @param memAppId     会员申请ID
     * @param fileCateCode 认证资料类别编码
     */
    private void upload(final Integer memAppId, final String fileCateCode, List<String> files) {
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
                                        //TODO 将serverFilePath存在对应的MemberPhotoBo中
                                        Toast.makeText(getActivity(), serverFilePath, Toast.LENGTH_LONG).show();
                                    } else {
                                        // 会员文件上传失败
                                        Toast.makeText(getActivity(), uploadMaterialResultMsg, Toast.LENGTH_LONG)
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
                        Toast.makeText(getActivity(), uploadResultMsg, Toast.LENGTH_LONG).show();
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
        fileManage.doBatchUpload(files, uploadResponseHandler);
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
                                Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
                            }
                        }
                    } else {
                        Toast.makeText(getActivity(), "查询上传记录失败：" + resultMessage, Toast.LENGTH_LONG).show();
                    }
                }
                return false;
            }
        });

        UploadMaterialQueryResponseHandler uploadMaterialQueryResponseHandler = new UploadMaterialQueryResponseHandler(handler);

        MemberApplyManager memberApplyManager = new MemberApplyManagerImpl();
        memberApplyManager.findMemberAuthFileUploadsByCondition(memAppId, fileCateCode, uploadMaterialQueryResponseHandler);
    }


}
