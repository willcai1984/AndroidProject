package com.qjdchina.qjdsale;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

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
                if (loginTest(etUser.getText().toString(), etPasswd.getText().toString())) {
                    Intent intent = new Intent(LoginActivity.this, MemberInfoActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "用户名密码错误",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean loginTest(String user, String passwd) {
        if (user.equals("will") && passwd.equals("123456")) {
            return true;
        } else {
            return false;
        }
    }

}
