package com.example.firstdemo.biz.util;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.firstdemo.commons.result.RestBody;
import com.example.firstdemo.dto.MemberApplyBo;

/**
 * 与服务端报文解析的工具类
 * 
 * @author user2
 *
 */
public class JsonUtil {
	private JsonUtil() {

	}

	/**
	 * 解析单个对象
	 * 
	 * @param jsonResponse
	 * @return
	 */
	public static <T> RestBody<T> convert(String jsonResponse) {
		TypeReference<RestBody<T>> reference = new TypeReference<RestBody<T>>() {
		};

		RestBody<T> restbody = JSON.parseObject(jsonResponse, reference);
		return restbody;
	}

	/**
	 * 解析对象list 注：暂定，待优化
	 * 
	 * @param response
	 * @return
	 */
	public static RestBody<List<MemberApplyBo>> convertMemberApplyBos(String response) {
		TypeReference<RestBody<List<MemberApplyBo>>> reference = new TypeReference<RestBody<List<MemberApplyBo>>>() {
		};

		RestBody<List<MemberApplyBo>> restbody = JSON.parseObject(response, reference);
		return restbody;
	}

}
