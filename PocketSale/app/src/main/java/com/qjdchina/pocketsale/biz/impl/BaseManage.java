package com.qjdchina.pocketsale.biz.impl;

import  com.qjdchina.pocketsale.commons.CommonHttpClientParam;
import  com.qjdchina.pocketsale.commons.HttpClientUtil;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Map;

/**
 * 基础业务操作类
 * 
 * @author user2
 *
 */
public class BaseManage {

	/**
	 * GET请求
	 * 
	 * @param url
	 * @param param
	 * @param responseHandler
	 * @throws Exception
	 */
	public static <T> void doCommonAsyncHttpGet(String url, CommonHttpClientParam param,
			TextHttpResponseHandler responseHandler) throws Exception {

		RequestParams requestParams = changeParam(param);

		HttpClientUtil.doAsyncHttpGet(url, requestParams, responseHandler);

	}

	/**
	 * POST请求
	 * 
	 * @param url
	 * @param param
	 * @param responseHandler
	 * @throws Exception
	 */
	public static <T> void doCommonAsyncHttpPost(String url, CommonHttpClientParam param,
			TextHttpResponseHandler responseHandler) throws Exception {

		RequestParams requestParams = changeParam(param);

		HttpClientUtil.doAsyncHttpPost(url, requestParams, responseHandler);

	}
	
	/**
	 * CommonHttpClientParam类型转换为RequestParams封装参数
	 * @param param
	 * @return
	 * @throws FileNotFoundException
	 */
	private static RequestParams changeParam(CommonHttpClientParam param) throws FileNotFoundException {
		Iterator<Map.Entry<String, Object>> iter = param.getParam().entrySet().iterator();
		RequestParams requestParams = new RequestParams();
		while (iter.hasNext()) {
			Map.Entry<String, Object> entry = iter.next();
			if (entry.getValue() instanceof File) {
				String contentType = (String) param.getParam().get("contentType");
				requestParams.put(entry.getKey(), (File) entry.getValue(), contentType);
			} else {
				requestParams.put(entry.getKey(), entry.getValue());
			}
		}
		return requestParams;
	}
}
