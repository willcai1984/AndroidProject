package com.qjdchina.pocketsale.commons;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * HttpClient操作工具类
 *
 * @author user2
 */
public class HttpClientUtil {

    private static AsyncHttpClient client = new AsyncHttpClient();

    static {
        client.setTimeout(11000); // 设置链接超时，如果不设置，默认为10s
    }

    private HttpClientUtil() {

    }


    /**
     * 以异步方式提交POST请求
     */
    public static void doAsyncHttpPost(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(url, params, responseHandler);
    }

    /**
     * 以异步方式提交GET请求
     */
    public static <T> void doAsyncHttpGet(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(url, params, responseHandler);
    }
}
