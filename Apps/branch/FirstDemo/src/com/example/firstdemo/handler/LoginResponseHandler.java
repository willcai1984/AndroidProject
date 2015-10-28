package com.example.firstdemo.handler;

import org.apache.http.Header;

import com.example.firstdemo.biz.util.JsonUtil;
import com.example.firstdemo.commons.result.RestBody;
import com.loopj.android.http.TextHttpResponseHandler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * 登录操作回调Handler
 * 
 * @author user2
 *
 */
public class LoginResponseHandler extends TextHttpResponseHandler {

	private Handler handler;

	public LoginResponseHandler(Handler handler) {
		super();
		this.handler = handler;
	}

	@Override
	public void onFailure(int arg0, Header[] arg1, String arg2, Throwable arg3) {

	}

	@Override
	public void onSuccess(int arg0, Header[] arg1, String arg2) {
		RestBody<String> restBody = JsonUtil.convert(arg2);

		Message message = new Message();
		message.what = 1;
		Bundle bundle = new Bundle();
		bundle.putString("loginResult", restBody.getMessage());
		message.setData(bundle);
		handler.sendMessage(message);
	}

}
