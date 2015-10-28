package com.example.firstdemo.biz.impl;

import java.util.List;

import com.example.firstdemo.biz.MemberApplyManager;
import com.example.firstdemo.commons.CommonHttpClientParam;
import com.example.firstdemo.commons.result.Result;
import com.example.firstdemo.dto.MemberApplyBo;
import com.example.firstdemo.handler.LoginResponseHandler;
import com.example.firstdemo.handler.MemberApplyQueryResponseHandler;
import com.example.firstdemo.handler.UploadResponseHandler;

public class MemberApplyManagerImpl implements MemberApplyManager {

	private static final String TAG = "MemberAuthFileManagerImpl";

	/**
	 * 登录校验的url
	 */
	private static final String LOGIN_URL = "admin/appLogin";

	/**
	 * 获取会员申请列表的url
	 */
	private static final String MEM_APP_LIST_URL = "app/memAppList";

	/**
	 * 上传图片的url
	 */
	private static final String UPLOAD_FILE_URL = "app/upload";

	@Override
	public Result<String> doLogin(String userId, String password, LoginResponseHandler loginResponseHandler) {
		CommonHttpClientParam param = new CommonHttpClientParam();
		param.addParam("userId", userId);
		param.addParam("password", password);
		BaseManage.doCommonAsyncHttpGet(LOGIN_URL, param, loginResponseHandler);
		return null;
	}

	@Override
	public Result<String> doUploadMemberAuthFile(String memAppId, String fileCateCode, String serverFilePath,
			UploadResponseHandler uploadResponseHandler) {
		CommonHttpClientParam param = new CommonHttpClientParam();
		param.addParam("applyId", memAppId);
		param.addParam("fileCateCode", fileCateCode);
		param.addParam("serverFilePath", serverFilePath);

		BaseManage.doCommonAsyncHttpGet(UPLOAD_FILE_URL, param, uploadResponseHandler);
		return null;
	}

	@Override
	public Result<List<String>> doBatchUploadMemberAuthFile(String memAppId, String fileCateCode,
			List<String> serverFilePaths, UploadResponseHandler uploadResponseHandler) {
		return null;
	}

	@Override
	public Result<List<MemberApplyBo>> findMemberApplyBoListByOperatorId(String operatorId,
			MemberApplyQueryResponseHandler queryResponseHandler) {
		CommonHttpClientParam param = new CommonHttpClientParam();
		param.addParam("operatorId", operatorId);
		BaseManage.doCommonAsyncHttpGet(MEM_APP_LIST_URL, param, queryResponseHandler);
		return null;
	}
}
