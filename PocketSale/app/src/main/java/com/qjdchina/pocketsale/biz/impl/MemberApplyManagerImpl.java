package com.qjdchina.pocketsale.biz.impl;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.qjdchina.pocketsale.biz.MemberApplyManager;
import com.qjdchina.pocketsale.commons.CommonHttpClientParam;
import com.qjdchina.pocketsale.commons.Constants;
import com.qjdchina.pocketsale.commons.PropertiesUtil;
import com.qjdchina.pocketsale.commons.result.Result;
import com.qjdchina.pocketsale.dto.MemberApplyBo;
import com.qjdchina.pocketsale.handler.LoginResponseHandler;
import com.qjdchina.pocketsale.handler.MemberApplyQueryResponseHandler;
import com.qjdchina.pocketsale.handler.MemberRatingQueryResponseHandler;
import com.qjdchina.pocketsale.handler.MemberRatingResponseHandler;
import com.qjdchina.pocketsale.handler.UploadMaterialQueryResponseHandler;
import com.qjdchina.pocketsale.handler.UploadMaterialResponseHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MemberApplyManagerImpl implements MemberApplyManager {

    private static final String TAG = "MemberAuthFileManagerImpl";

    /**
     * 登录校验的url
     */
    private static final String LOGIN_URL = PropertiesUtil.getProperty("login_url");

    /**
     * 获取会员申请列表的url
     */
    private static final String MEM_APP_LIST_URL = PropertiesUtil.getProperty("mem_app_list_url");

    /**
     * 上传图片的url
     */
    private static final String UPLOAD_MATERIAL_FILE_URL = PropertiesUtil.getProperty("upload_material_file_url");

    /**
     * 上传记录查询URL
     */
    private static final String UPLOAD_FILE_QUERY_URL = PropertiesUtil.getProperty("upload_file_query_url");

    /**
     * 客户评分URL
     */
    private static final String MEM_RATING_URL = PropertiesUtil.getProperty("member_rating_url");

    /**
     * 客户评分查询URL
     */
    private static final String MEM_RATING_QUERY_URL = PropertiesUtil.getProperty("member_rating_query_url");

    /**
     * 调CIF-FNT系统地址：IP:PORT/WEB
     */
    private static final String BASE_URL_CIF_FNT = PropertiesUtil.getProperty("cif_fnt_server_base_url");

    /**
     * 调CEMS系统地址：IP:PORT/WEB
     */
    private static final String BASE_URL_CEMS = PropertiesUtil.getProperty("cems_server_base_url");

    /**
     * 获取请求CIF-FNT的全路径
     *
     * @param relativeUrl
     * @return
     */
    private static String getCifAbsoluteUrl(String relativeUrl) {
        return BASE_URL_CIF_FNT + relativeUrl;
    }

    /**
     * 获取请求CEMS的全路径
     *
     * @param relativeUrl
     * @return
     */
    private static String getCemsAbsoluteUrl(String relativeUrl) {
        return BASE_URL_CEMS + relativeUrl;
    }

    @Override
    public Result<String> doLogin(String userId, String password, LoginResponseHandler loginResponseHandler) {
        CommonHttpClientParam param = new CommonHttpClientParam();
        param.addParam("userId", userId);
        param.addParam("password", password);
        try {
            BaseManage.doCommonAsyncHttpGet(getCifAbsoluteUrl(LOGIN_URL), param, loginResponseHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Result<List<MemberApplyBo>> findMemberApplyBoListByOperatorId(String operatorId, Integer pageNo,
                                                                         MemberApplyQueryResponseHandler queryResponseHandler) {
        CommonHttpClientParam param = new CommonHttpClientParam();
        param.addParam("operatorId", operatorId);
        param.addParam("pageNo", pageNo);
        try {
            BaseManage.doCommonAsyncHttpGet(getCifAbsoluteUrl(MEM_APP_LIST_URL), param, queryResponseHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Result<String> doUploadMemberAuthFile(Integer memAppId, String fileCateCode, String serverFilePath,
                                                 UploadMaterialResponseHandler uploadMaterialResponseHandler) {
        CommonHttpClientParam param = new CommonHttpClientParam();
        param.addParam("memAppId", memAppId);
        param.addParam("fileCateCode", fileCateCode);
        param.addParam("serverFilePath", serverFilePath);

        try {
            BaseManage.doCommonAsyncHttpPost(getCifAbsoluteUrl(UPLOAD_MATERIAL_FILE_URL), param, uploadMaterialResponseHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Result<List<String>> doBatchUploadMemberAuthFile(Integer memAppId, String fileCateCode,
                                                            List<String> serverFilePaths, UploadMaterialResponseHandler uploadMaterialResponseHandler) {
        if (serverFilePaths != null && !serverFilePaths.isEmpty()) {
            for (Iterator<String> iter = serverFilePaths.iterator(); iter.hasNext(); ) {
                doUploadMemberAuthFile(memAppId, fileCateCode, iter.next(), uploadMaterialResponseHandler);
            }
        }
        return null;
    }

    @Override
    public Result<Map<String, List<String>>> findMemberAuthFileUploadsByCondition(Integer memAppId, String fileCateCode, UploadMaterialQueryResponseHandler queryUploadResponseHandler) {
        CommonHttpClientParam param = new CommonHttpClientParam();
        param.addParam("memAppId", memAppId);
        param.addParam("fileCateCode", fileCateCode);

        try {
            BaseManage.doCommonAsyncHttpGet(getCifAbsoluteUrl(UPLOAD_FILE_QUERY_URL), param, queryUploadResponseHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Result<Map<String, Object>> doMemberRating(String companyId, Map<String, Object> params, MemberRatingResponseHandler memberRatingResponseHandler) {
        CommonHttpClientParam commonHttpClientParam = new CommonHttpClientParam();
        commonHttpClientParam.addParam("ratingParam", generateRatingData(companyId, params));

        try {
            BaseManage.doCommonAsyncHttpPost(getCemsAbsoluteUrl(MEM_RATING_URL), commonHttpClientParam, memberRatingResponseHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成请求报文信息
     *
     * @param companyId 公司ID
     * @param params    评分项信息
     * @return
     */
    private String generateRatingData(String companyId, Map<String, Object> params) {
        //组装ratingParam字段的Map
        Map<String, Object> ratingParamMap = new HashMap<String, Object>();
        //组装ratingData字段的Map
        Map<String, Object> ratingDataMap = new HashMap<String, Object>();
        //companyId
        ratingParamMap.put("companyId", companyId);
        ratingParamMap.put("amendReason", "app测试");//修正理由
        ratingParamMap.put("creditLine", "5000000");//授信额度


        //cat
        Map<String, Object> cat = new HashMap<String, Object>();
        cat.put("cat", "1001");//品类：mockit：电梯
        cat.put("amount", params.get(Constants.MEMBER_MARK_SALE));//年均销量
        cat.put("score", params.get(Constants.MEMBER_MARK_SALE));//该项评分
        ratingDataMap.put("cat", cat);

        //capital
        Map<String, Object> capital = new HashMap<String, Object>();
        capital.put("capital", params.get(Constants.MEMBER_MARK_CAPITAL));//企业注册资本
        capital.put("score", params.get(Constants.MEMBER_MARK_CAPITAL));//该项评分
        ratingDataMap.put("capital", capital);

        //settime
        Map<String, Object> settime = new HashMap<String, Object>();
        settime.put("settime", params.get(Constants.MEMBER_MARK_YEARS));//企业成立年限
        settime.put("score", params.get(Constants.MEMBER_MARK_YEARS));//该项评分
        ratingDataMap.put("settime", settime);

        //worktime
        Map<String, Object> worktime = new HashMap<String, Object>();
        worktime.put("worktime", params.get(Constants.MEMBER_MARK_EXPERIENCE));//实际经营者现行从业年限
        worktime.put("score", params.get(Constants.MEMBER_MARK_EXPERIENCE));//该项评分
        ratingDataMap.put("worktime", worktime);

        //ratio
        Map<String, Object> ratio = new HashMap<String, Object>();
        ratio.put("ratio", "10");//企业资产负债率：mockit 50%（含 ）以下
        ratio.put("score", "10");//该项评分：mockkit 10分
        ratingDataMap.put("ratio", ratio);

        //team
        Map<String, Object> team = new HashMap<String, Object>();
        team.put("team", params.get(Constants.MEMBER_MARK_SIZE));//企业团队规模
        team.put("score", params.get(Constants.MEMBER_MARK_SIZE));//该项评分
        ratingDataMap.put("team", team);

        //assets
        Map<String, Object> assets = new HashMap<String, Object>();
        assets.put("assets", params.get(Constants.MEMBER_MARK_WORTH));//实际控制人家庭净资产
        assets.put("score", params.get(Constants.MEMBER_MARK_WORTH));//该项评分
        ratingDataMap.put("assets", assets);

        //personal
        Map<String, Object> personal = new HashMap<String, Object>();
        personal.put("personal", "8");//个人信用状况 mockit：无借款
        personal.put("score", "8");//该项评分：mockkit 8分
        List<Map<String, Object>> personalList = new ArrayList<Map<String, Object>>();
        Map<String, Object> personalListItem = new HashMap<String, Object>();
        personalListItem.put("mode1", "0");
        personalListItem.put("mode2", "0");
        personalListItem.put("score", "0");
        personalList.add(personalListItem);
        personal.put("list", personalList);
        ratingDataMap.put("personal", personal);

        //enterprise
        Map<String, Object> enterprise = new HashMap<String, Object>();
        enterprise.put("enterprise", "8");//企业信用情况 mockit：无贷款
        enterprise.put("score", "8");//该项评分：mockkit 8分
        List<Map<String, Object>> enterpriseList = new ArrayList<Map<String, Object>>();
        Map<String, Object> enterpriseListItem = new HashMap<String, Object>();
        enterpriseListItem.put("mode1", "0");
        enterpriseListItem.put("mode2", "0");
        enterpriseListItem.put("score", "0");
        enterpriseList.add(enterpriseListItem);
        enterprise.put("list", enterpriseList);
        ratingDataMap.put("enterprise", enterprise);

        //supplier
        Map<String, Object> supplier = new HashMap<String, Object>();
        supplier.put("supplier", "1031");//上游供应商 mockit：电梯类
        supplier.put("supplier2", "3");//年销售额20亿以上
        supplier.put("score", "3");//该项评分：mockkit 3分
        ratingDataMap.put("supplier", supplier);

        //project
        Map<String, Object> project = new HashMap<String, Object>();
        project.put("project", "1042");//过往项目情况（提供过往合同） mockit：厨房电器类
        project.put("project2", "8");//单笔合同供货量在500万元（含）-1000万元
        project.put("score", "8");//该项评分：mockkit 8分
        ratingDataMap.put("project", project);


        String jsonString = JSON.toJSONString(ratingDataMap);
        Log.v(TAG, jsonString);
        ratingParamMap.put("ratingData", jsonString);
        return JSON.toJSONString(ratingParamMap);
    }

    @Override
    public Result<Map<String, Object>> findMemberRatingResult(String companyId, MemberRatingQueryResponseHandler memberRatingQueryResponseHandler) {
        CommonHttpClientParam param = new CommonHttpClientParam();
        param.addParam("companyId", companyId);

        try {
            BaseManage.doCommonAsyncHttpGet(getCemsAbsoluteUrl(MEM_RATING_QUERY_URL), param, memberRatingQueryResponseHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
