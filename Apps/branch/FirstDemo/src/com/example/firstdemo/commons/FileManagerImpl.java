package com.example.firstdemo.commons;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.json.JSONObject;

import com.example.firstdemo.commons.enums.ResultCodes;
import com.example.firstdemo.commons.result.Result;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.util.Log;

/**
 * 公用文件操作接口实现
 * 
 * @author user2
 *
 */
public class FileManagerImpl implements FileManager {

	private static final String TAG = "FileManagerImpl";

	/**
	 * 上传文件格式
	 */
	private String contentType = "application/octet-stream";

	/**
	 * 服务器处理地址
	 */
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public Result<String> doUpload(String filePath) {
		if (StringUtils.isBlank(filePath)) {
			return new Result<String>(ResultCodes.FAILED.getCode(), "待 上传文件路径不能为空");
		}

		File file = new File(filePath);
		RequestParams params = new RequestParams();
		try {
			params.put("fileName", file, contentType);
			HttpClientUtil.doAsyncHttpPost(filePath, params, new JsonHttpResponseHandler() {

				@Override
				public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
					super.onFailure(statusCode, headers, throwable, errorResponse);
				}

				@Override
				public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
					super.onSuccess(statusCode, headers, response);
				}

			});

		} catch (FileNotFoundException e) {
			Log.v(TAG, "文件不存在！", e);
			return new Result<String>(ResultCodes.FAILED.getCode());
		}
		// 模拟path
		String serverFilePath = "/srv/upload/*.png";
		return new Result<String>(serverFilePath);
	}

	@Override
	public Result<List<String>> doBatchUpload(List<String> filePaths) {
		if (filePaths == null || filePaths.isEmpty()) {
			return new Result<List<String>>(ResultCodes.FAILED.getCode(), "待 上传文件列表不能为空");
		}

		List<String> results = new ArrayList<String>();
		for (Iterator<String> iter = filePaths.iterator(); iter.hasNext();) {
			Result<String> result = doUpload(iter.next());
			if (result.isSuccess()) {
				results.add(result.getDataModel());
			}
		}
		return new Result<List<String>>(results);

	}

}
