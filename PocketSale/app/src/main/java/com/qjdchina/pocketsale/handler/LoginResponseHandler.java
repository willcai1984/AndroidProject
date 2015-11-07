package com.qjdchina.pocketsale.handler;

import android.os.Handler;
import android.util.Log;

import com.qjdchina.pocketsale.biz.util.JsonUtil;
import com.qjdchina.pocketsale.commons.Constants;
import com.qjdchina.pocketsale.commons.enums.MessageType;
import com.qjdchina.pocketsale.commons.enums.ResultCodes;
import com.qjdchina.pocketsale.commons.result.RestBody;

import org.apache.http.Header;

/**
 * 登录操作回调Handler
 * 
 * @author user2
 *
 */
public class LoginResponseHandler extends AbstractResponseHandler {

	private static final String TAG = "LoginResponseHandler";

	private Handler handler;

	public LoginResponseHandler(Handler handler) {
		super();
		this.handler = handler;
	}

	@Override
	public int getSupportMessageType() {
		return MessageType.LOGIN.getType();
	}

	@Override
	public void onFailure(int arg0, Header[] arg1, String arg2, Throwable arg3) {
		Log.v(TAG, arg2);

		// 设置登录失败时的返回信息
		getBundle().putString(Constants.RESULT_CODE, ResultCodes.FAILED.getCode());
		getBundle().putString(Constants.RESULT_MESSAGE, "登录失败，请重新登录！");
		handler.sendMessage(wrapMessage());
	}

	@Override
	public void onSuccess(int arg0, Header[] arg1, String arg2) {
		RestBody<String> restBody = JsonUtil.convert(arg2);

		// 设置登录成功时的返回信息
		getBundle().putString(Constants.RESULT_CODE, restBody.getCode());
		getBundle().putString(Constants.RESULT_MESSAGE, restBody.getMessage());
		handler.sendMessage(wrapMessage());
	}

}
