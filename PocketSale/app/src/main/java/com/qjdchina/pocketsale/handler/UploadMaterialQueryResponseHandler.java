package com.qjdchina.pocketsale.handler;

import android.os.Handler;
import android.util.Log;

import com.qjdchina.pocketsale.biz.util.JsonUtil;
import com.qjdchina.pocketsale.commons.Constants;
import com.qjdchina.pocketsale.commons.enums.MessageType;
import com.qjdchina.pocketsale.commons.enums.ResultCodes;
import com.qjdchina.pocketsale.commons.result.RestBody;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 客户图片上传记录查询接口回调Handler
 * Created by user2 on 2015/11/2.
 */
public class UploadMaterialQueryResponseHandler extends AbstractResponseHandler {

    private static final String TAG = "UploadMaterialQueryResponseHandler";

    private Handler handler;

    public UploadMaterialQueryResponseHandler(Handler handler) {
        this.handler = handler;
    }

    @Override
    protected int getSupportMessageType() {
        return MessageType.QUERY_UPLOAD.getType();
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
            String dataContent = memAppResult.getData();
            Map<String, List<String>> dataMap = JsonUtil.convertToMap(dataContent);
            for (Iterator<Map.Entry<String, List<String>>> iter = dataMap.entrySet().iterator(); iter.hasNext(); ) {
                Map.Entry<String, List<String>> entry = iter.next();
                getBundle().putStringArrayList(entry.getKey(), (ArrayList<String>) entry.getValue());
            }
        } else {
            Log.v(TAG, "member material upload query fail: " + memAppResult.getMessage());
        }
    }

}
