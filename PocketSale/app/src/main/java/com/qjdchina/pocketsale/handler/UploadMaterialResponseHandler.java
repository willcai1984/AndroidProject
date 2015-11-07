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
 * 上传会员认证文件操作回调Handler
 * 
 * @author user2
 *
 */
public class UploadMaterialResponseHandler extends AbstractResponseHandler {

	private static final String TAG = "UploadMaterialResponseHandler";

	private Handler handler;

	public UploadMaterialResponseHandler(Handler handler) {
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
		RestBody<String> uploadMaterialResult = JsonUtil.convert(arg2);
		handler.sendMessage(wrapMessage());
		getBundle().putString(Constants.RESULT_CODE, uploadMaterialResult.getCode());
		getBundle().putString(Constants.RESULT_MESSAGE, uploadMaterialResult.getMessage());

		if (ResultCodes.SUCCESS.getCode().equals(uploadMaterialResult.getCode())) {
			getBundle().putString(Constants.SERVER_MATERIAL_FILE_PATH, uploadMaterialResult.getData());
		} else {
			Log.v(TAG, "upload material file fail");
		}
	}

	@Override
	protected int getSupportMessageType() {
		return MessageType.UPLOAD_MATERIAL.getType();
	}

}
