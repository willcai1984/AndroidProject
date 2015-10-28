package com.example.firstdemo.thread;

import com.example.firstdemo.biz.MemberApplyManager;
import com.example.firstdemo.biz.impl.MemberApplyManagerImpl;
import com.example.firstdemo.handler.LoginResponseHandler;

import android.os.Handler;

/**
 * 登录线程
 * 
 * @author user2
 *
 */
public class LoginTask extends Thread {

	private Handler handler;

	public LoginTask(Handler handler) {
		this.handler = handler;
	}

	@Override
	public void run() {
		MemberApplyManager authFileManager = new MemberApplyManagerImpl();
		LoginResponseHandler loginResponseHandler = new LoginResponseHandler(handler);

		authFileManager.doLogin("wangzheng", "wangzheng", loginResponseHandler);
	}

}
