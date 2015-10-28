package com.example.firstdemo;

import java.util.Iterator;
import java.util.List;

import com.example.firstdemo.biz.MemberApplyManager;
import com.example.firstdemo.biz.impl.MemberApplyManagerImpl;
import com.example.firstdemo.commons.result.Result;
import com.example.firstdemo.dto.MemberApplyBo;
import com.example.firstdemo.handler.LoginResponseHandler;
import com.example.firstdemo.handler.MemberApplyQueryResponseHandler;
import com.example.firstdemo.model.MemberApplyParcelable;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	private static final String TAG = "MainActivity";

	private Button loginButton;

	private EditText loginName;

	private EditText password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.v(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		loginButton = (Button) findViewById(R.id.loginButton);
		loginButton.setOnClickListener(this);
	}

	@Override
	protected void onStart() {
		Log.v(TAG, "onStart");
		super.onStart();
	}

	@Override
	protected void onResume() {
		Log.v(TAG, "onResume");
		super.onResume();
	}

	@Override
	protected void onPause() {
		Log.v(TAG, "onPause");
		super.onPause();
	}

	@Override
	protected void onStop() {
		Log.v(TAG, "onStop");
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		Log.v(TAG, "onDestroy");
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * 查询会员列表
	 */
	private void search() {
		// 需要在当前主线程：UI线程中启动新线程调用接口
		Handler handler = new Handler(new Handler.Callback() {
			@Override
			public boolean handleMessage(Message msg) {
				if (msg.what == 2) {
					Bundle bundle = msg.getData();
					MemberApplyParcelable applyParcelable = bundle.getParcelable("memApplys");
					List<MemberApplyBo> memberApplyBos = applyParcelable.getMemApplyBos();
					if (memberApplyBos != null) {
						for (Iterator<MemberApplyBo> iter = memberApplyBos.iterator(); iter.hasNext();) {
							Log.v(TAG, iter.next().toString());
						}
						Toast.makeText(MainActivity.this, "共查询"+memberApplyBos.size()+"条记录", Toast.LENGTH_LONG).show();
					}
				}
				return false;
			}
		});

		MemberApplyManager memberApplyManager = new  MemberApplyManagerImpl();
		MemberApplyQueryResponseHandler queryResponseHandler =new MemberApplyQueryResponseHandler(handler);
		memberApplyManager.findMemberApplyBoListByOperatorId("1", queryResponseHandler);
	}

	private void login() {
		Handler handler = new Handler(new Handler.Callback() {
			@Override
			public boolean handleMessage(Message msg) {
				if (msg.what == 1) {
					Bundle bundle = msg.getData();
					String loginResult = bundle.getString("loginResult");
					Toast.makeText(MainActivity.this, loginResult, Toast.LENGTH_LONG).show();
				}
				return false;
			}
		});
		
		MemberApplyManager memberApplyManager = new  MemberApplyManagerImpl();
		LoginResponseHandler loginResponseHandler = new LoginResponseHandler(handler);
		memberApplyManager.doLogin("wangzheng", "wangzheng", loginResponseHandler);
		
	}

	@Override
	public void onClick(View v) {
		// 1.login
		//login();

		
		// 2.search
		search();
	}
}
