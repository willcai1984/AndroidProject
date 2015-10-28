package com.example.firstdemo.biz.impl;

import java.util.Iterator;
import java.util.Map;

import com.example.firstdemo.commons.CommonHttpClientParam;
import com.example.firstdemo.commons.HttpClientUtil;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

/**
 * 基础业务操作类
 * 
 * @author user2
 *
 */
public class BaseManage {

	public static <T> void doCommonAsyncHttpGet(String url, CommonHttpClientParam param,
			TextHttpResponseHandler responseHandler) {

		RequestParams requestParams = changeParam(param);

		HttpClientUtil.doAsyncHttpGet(url, requestParams, responseHandler);

	}

	private static RequestParams changeParam(CommonHttpClientParam param) {
		Iterator<Map.Entry<String, Object>> iter = param.getParam().entrySet().iterator();
		RequestParams requestParams = new RequestParams();
		while (iter.hasNext()) {
			Map.Entry<String, Object> entry = iter.next();
			requestParams.put(entry.getKey(), entry.getValue());
		}
		return requestParams;
	}
}
