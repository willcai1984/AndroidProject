package com.example.firstdemo.commons;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * HttpClient操作工具类
 * 
 * @author user2
 *
 */
public class HttpClientUtil {

	/**
	 * 服务端域名前半段：IP:PORT/WEB
	 */
	private static final String BASE_URL = "http://10.0.2.2:8080/cif.fnt.web/boss/";

	private static AsyncHttpClient client = new AsyncHttpClient();

	static {
		client.setTimeout(11000); // 设置链接超时，如果不设置，默认为10s
	}

	private HttpClientUtil() {

	}

	/**
	 * 获取请求的全路径
	 * 
	 * @param relativeUrl
	 * @return
	 */
	private static String getAbsoluteUrl(String relativeUrl) {
		return BASE_URL + relativeUrl;
	}

	/**
	 * 以异步方式提交POST请求
	 */
	public static void doAsyncHttpPost(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		client.post(getAbsoluteUrl(url), params, responseHandler);
	}

	/**
	 * 以异步方式提交GET请求
	 */
	public static <T> void doAsyncHttpGet(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		client.get(getAbsoluteUrl(url), params, responseHandler);
	}
}
