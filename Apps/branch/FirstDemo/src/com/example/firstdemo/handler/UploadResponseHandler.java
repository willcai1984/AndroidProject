package com.example.firstdemo.handler;

import org.apache.http.Header;

import com.example.firstdemo.biz.util.JsonUtil;
import com.example.firstdemo.commons.result.RestBody;
import com.loopj.android.http.TextHttpResponseHandler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * 上传文件操作回调Handler
 * 
 * @author user2
 *
 */
public class UploadResponseHandler extends TextHttpResponseHandler {

	private Handler handler;

	public UploadResponseHandler(Handler handler) {
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
		message.what = 3;
		Bundle bundle = new Bundle();
		bundle.putString("uploadResult", restBody.getMessage());
		message.setData(bundle);
		handler.sendMessage(message);
	}

}
