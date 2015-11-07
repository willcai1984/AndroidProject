package com.qjdchina.pocketsale.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.qjdchina.pocketsale.R;
import com.qjdchina.pocketsale.biz.MemberApplyManager;
import com.qjdchina.pocketsale.biz.impl.MemberApplyManagerImpl;
import com.qjdchina.pocketsale.commons.Constants;
import com.qjdchina.pocketsale.commons.enums.MessageType;
import com.qjdchina.pocketsale.commons.enums.ResultCodes;
import com.qjdchina.pocketsale.dto.MemberApplyBo;
import com.qjdchina.pocketsale.handler.MemberApplyQueryResponseHandler;
import com.qjdchina.pocketsale.model.MemberApplyParcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemberListActivity extends Activity {
    private static final String TAG = "MemberListActivity";
    private List<Map<String, Object>> data;
    private ListView lv;
    private Map<String, Object> map;
    private MemberApplyBo mb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_list);
        //topbar
        ImageView topbar = (ImageView) findViewById(R.id.topbar);
        topbar.setImageResource(R.drawable.topbar);
        //list
        lv = (ListView) findViewById(R.id.lv);
        data = new ArrayList<Map<String, Object>>();
        search();
    }

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
                            for (int index = 0; index < memberApplyBos.size(); index++) {
                                mb = memberApplyBos.get(index);
                                map = new HashMap<String, Object>();
                                //Add para for Transfer data to member info activity
                                map.put("id", mb.getId());
                                map.put("companyID", mb.getCompanyId());
                                //Add para for list view
                                map.put("companyname", mb.getCorpName());
                                map.put("address", mb.getAddress());
                                map.put("date", mb.getCreateDate());
                                //TODO 方方把status改为整型
                                map.put("status", mb.getStatus());
                                //0 未完成 1 已完成
                                map.put("statusicon", "0".equals(mb.getStatus()) ? R.drawable.icon_goto : R.drawable.icon_check);
                                data.add(map);
                            }
                            Toast.makeText(MemberListActivity.this, "共查询" + memberApplyBos.size() + "条记录", Toast.LENGTH_LONG)
                                    .show();
                            //设置listview点击事件
                            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    map = data.get(position);
                                    if(map.get("companyID")==null){
                                        Toast.makeText(MemberListActivity.this, "此公司还未通过实名认证", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    Intent intent = new Intent(MemberListActivity.this, MemberDetailActivity.class);
                                    Bundle b = new Bundle();
                                    String mID = map.get("id") == null ? null : map.get("id").toString();
                                    String mCompanyID = map.get("companyID") == null ? null : map.get("companyID").toString();
                                    String mCompantName = map.get("companyname") == null ? null : map.get("companyname").toString();
                                    b.putString("id", mID);
                                    b.putString("companyID", mCompanyID);
                                    b.putString("companyName", mCompantName);
                                    intent.putExtras(b);
                                    startActivity(intent);
                                }
                            });

                            lv.setAdapter(new SimpleAdapter(MemberListActivity.this,
                                    data,
                                    R.layout.list_item,
                                    new String[]{"companyname",
                                            "address",
                                            "date",
                                            "status",
                                            "statusicon"
                                    },
                                    new int[]{R.id.tv_companyname,
                                            R.id.tv_address,
                                            R.id.tv_date,
                                            R.id.tv_status,
                                            R.id.iv_statusicon
                                    }
                            ));
                        }
                    } else {
                        Toast.makeText(MemberListActivity.this, "查询失败：" + resultMessage, Toast.LENGTH_LONG).show();
                    }
                }
                return false;
            }
        });

        MemberApplyQueryResponseHandler queryResponseHandler = new MemberApplyQueryResponseHandler(handler);
        MemberApplyManager memberApplyManager = new MemberApplyManagerImpl();
        memberApplyManager.findMemberApplyBoListByOperatorId("1", 1, queryResponseHandler);
    }

}
