package com.qjdchina.pocketsale.handler;

import org.apache.http.Header;

import com.qjdchina.pocketsale.biz.util.JsonUtil;
import com.qjdchina.pocketsale.commons.Constants;
import com.qjdchina.pocketsale.commons.enums.MessageType;
import com.qjdchina.pocketsale.commons.enums.ResultCodes;
import com.qjdchina.pocketsale.commons.result.RestBody;

import android.os.Handler;
import android.util.Log;

/**
 * 上传文件操作回调Handler
 * 
 * @author user2
 *
 */
public class UploadResponseHandler extends AbstractResponseHandler {

	private static final String TAG = "UploadResponseHandler";

	private Handler handler;

	public UploadResponseHandler(Handler handler) {
		super();
		this.handler = handler;
	}

	@Override
	public void onFailure(int arg0, Header[] arg1, String arg2, Throwable arg3) {
		Log.v(TAG, arg2);

		handler.sendMessage(wrapMessage());
		getBundle().putString(Constants.RESULT_CODE, ResultCodes.FAILED.getCode());
		getBundle().putString(Constants.RESULT_MESSAGE, "接口异常");
	}

	@Override
	public void onSuccess(int arg0, Header[] arg1, String arg2) {
		RestBody<String> uploadResult = JsonUtil.convert(arg2);
		handler.sendMessage(wrapMessage());
		getBundle().putString(Constants.RESULT_CODE, uploadResult.getCode());
		getBundle().putString(Constants.RESULT_MESSAGE, uploadResult.getMessage());

		if (ResultCodes.SUCCESS.getCode().equals(uploadResult.getCode())) {
			getBundle().putString(Constants.SERVER_FILE_PATH, uploadResult.getData());
		} else {
			Log.v(TAG, "upload file fail");
		}
	}

	@Override
	protected int getSupportMessageType() {
		return MessageType.UPLOAD_FILE.getType();
	}

}
