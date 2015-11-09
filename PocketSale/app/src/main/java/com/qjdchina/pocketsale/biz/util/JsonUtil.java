package com.qjdchina.pocketsale.biz.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.qjdchina.pocketsale.commons.result.RestBody;
import com.qjdchina.pocketsale.dto.MemberApplyBo;

import java.util.List;
import java.util.Map;

/**
 * 与服务端报文解析的工具类
 *
 * @author user2
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

    public static Map<String, List<String>> convertToMap(String jsonResponse) {
        TypeReference<Map<String, List<String>>> reference = new TypeReference<Map<String, List<String>>>() {
        };

        Map<String, List<String>> restbody = JSON.parseObject(jsonResponse, reference);
        return restbody;
    }

    public static Map<String, Map<String, Object>> convertToMap1(String jsonResponse) {
        TypeReference<Map<String, Map<String, Object>>> reference = new TypeReference<Map<String, Map<String, Object>>>() {
        };

        Map<String, Map<String, Object>> restbody = JSON.parseObject(jsonResponse, reference);
        return restbody;
    }

    public static Map<String, String> convertToMap2(String jsonResponse) {
        TypeReference<Map<String, String>> reference = new TypeReference<Map<String, String>>() {
        };

        Map<String, String> restbody = JSON.parseObject(jsonResponse, reference);
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
