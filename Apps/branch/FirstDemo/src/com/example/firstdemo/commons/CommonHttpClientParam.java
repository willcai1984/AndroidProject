package com.example.firstdemo.commons;

import java.util.HashMap;
import java.util.Map;

/**
 * 请求服务端的参数进行封装
 * 
 * @author user2
 *
 */
public class CommonHttpClientParam {

	private Map<String, Object> param = new HashMap<String, Object>();

	public void addParam(String key, Object value) {
		param.put(key, value);
	}

	public Map<String, Object> getParam() {
		return param;
	}

	public void setParam(Map<String, Object> param) {
		this.param = param;
	}

}
