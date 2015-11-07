package com.qjdchina.pocketsale.handler;

import android.os.Handler;
import android.util.Log;

import com.qjdchina.pocketsale.biz.util.JsonUtil;
import com.qjdchina.pocketsale.commons.Constants;
import com.qjdchina.pocketsale.commons.enums.MessageType;
import com.qjdchina.pocketsale.commons.enums.ResultCodes;
import com.qjdchina.pocketsale.commons.result.RestBody;

import org.apache.http.Header;

import java.util.Iterator;
import java.util.Map;

/**
 * 会员评分接口回调Handler
 * Created by user2 on 2015/11/2.
 */
public class MemberRatingResponseHandler extends AbstractResponseHandler {

    private static final String TAG = "MemberRatingResponseHandler";

    private Handler handler;

    public MemberRatingResponseHandler(Handler handler) {
        this.handler = handler;
    }

    @Override
    protected int getSupportMessageType() {
        return MessageType.RATING.getType();
    }

    @Override
    public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
        Log.v(TAG, s);

        handler.sendMessage(wrapMessage());
        getBundle().putString(Constants.RESULT_CODE, ResultCodes.FAILED.getCode());
        getBundle().putString(Constants.RESULT_MESSAGE, "接口异常");
    }

    @Override
    public void onSuccess(int i, Header[] headers, String s) {
        RestBody<String> memAppResult = JsonUtil.convert(s);
        handler.sendMessage(wrapMessage());
        getBundle().putString(Constants.RESULT_CODE, memAppResult.getCode());
        getBundle().putString(Constants.RESULT_MESSAGE, memAppResult.getMessage());

        if (ResultCodes.SUCCESS.getCode().equals(memAppResult.getCode())) {
            String dataContent = memAppResult.getData();//返回json格式的评分记录信息
            Map<String, String> dataContentMap = JsonUtil.convertToMap2(dataContent);
            String companyId = (String) dataContentMap.get("companyId");//公司ID
            Log.v(TAG, "公司ID: " + companyId);
            //获取评分项数据字段信息
            String ratingData = (String) dataContentMap.get("ratingData");
            Map<String, Map<String, Object>> ratingDataMap = JsonUtil.convertToMap1(ratingData);

            try {
                //年销量
                Map<String, Object> cat = ratingDataMap.get("cat");
                String catScore = (String) cat.get("score");
                Log.v(TAG, "年销量评分：" + catScore);
                getBundle().putString(Constants.MEMBER_MARK_SALE_SCORE, catScore);

                //注册资本
                Map<String, Object> capital = ratingDataMap.get("capital");
                String capitalScore = (String) capital.get("score");
                Log.v(TAG, "注册资本评分：" + capitalScore);
                getBundle().putString(Constants.MEMBER_MARK_CAPITAL_SCORE, capitalScore);

                //成立年限
                Map<String, Object> settime = ratingDataMap.get("settime");
                String settimeScore = (String) settime.get("score");
                Log.v(TAG, "成立年限评分：" + settimeScore);
                getBundle().putString(Constants.MEMBER_MARK_YEARS_SCORE, settimeScore);

                //管理者行业经验
                Map<String, Object> worktime = ratingDataMap.get("worktime");
                String worktimeScore = (String) worktime.get("score");
                Log.v(TAG, "管理者行业经验：" + worktimeScore);
                getBundle().putString(Constants.MEMBER_MARK_EXPERIENCE_SCORE, worktimeScore);

                //团队规模
                Map<String, Object> team = ratingDataMap.get("cat");
                String teamScore = (String) team.get("score");
                Log.v(TAG, "团队规模：" + teamScore);
                getBundle().putString(Constants.MEMBER_MARK_SIZE_SCORE, teamScore);

                //家庭净资产
                Map<String, Object> assets = ratingDataMap.get("assets");
                String assetsScore = (String) assets.get("score");
                Log.v(TAG, "家庭净资产：" + assetsScore);
                getBundle().putString(Constants.MEMBER_MARK_WORTH_SCORE, assetsScore);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Log.v(TAG, "rating fail");
        }
    }
}
