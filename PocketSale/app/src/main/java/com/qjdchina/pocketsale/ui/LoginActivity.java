package com.qjdchina.pocketsale.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.qjdchina.pocketsale.R;
import com.qjdchina.pocketsale.biz.MemberApplyManager;
import com.qjdchina.pocketsale.biz.impl.MemberApplyManagerImpl;
import com.qjdchina.pocketsale.commons.Constants;
import com.qjdchina.pocketsale.commons.enums.MessageType;
import com.qjdchina.pocketsale.commons.enums.ResultCodes;
import com.qjdchina.pocketsale.handler.LoginResponseHandler;

public class LoginActivity extends Activity {
    private static final String TAG = "LoginActivity";
    private EditText etUser;
    private EditText etPasswd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUser = (EditText) findViewById(R.id.et_user);
        etPasswd = (EditText) findViewById(R.id.et_passwd);
        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = etUser.getText().toString();
                String passwd = etPasswd.getText().toString();
                login(user, passwd);

            }
        });
    }


    /**
     * 登录
     */
    private void login(String user, String passwd) {
        Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == MessageType.LOGIN.getType()) {
                    Bundle bundle = msg.getData();
                    String resultCode = bundle.getString(Constants.RESULT_CODE);
                    Log.v(TAG, resultCode);
                    String resultMsg = bundle.getString(Constants.RESULT_MESSAGE);
                    Toast.makeText(LoginActivity.this, resultMsg, Toast.LENGTH_LONG).show();
                    if (ResultCodes.SUCCESS.getCode().equals(resultCode)) {
                        Intent intent = new Intent(LoginActivity.this, MemberListActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "用户名密码错误",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });
        LoginResponseHandler loginResponseHandler = new LoginResponseHandler(handler);
        MemberApplyManager memberApplyManager = new MemberApplyManagerImpl();
        memberApplyManager.doLogin(user, passwd, loginResponseHandler);

    }

}
