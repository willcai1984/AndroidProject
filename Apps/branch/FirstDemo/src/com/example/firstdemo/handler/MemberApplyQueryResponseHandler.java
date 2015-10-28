package com.example.firstdemo.handler;

import java.util.Collections;
import java.util.List;

import org.apache.http.Header;

import com.example.firstdemo.biz.util.JsonUtil;
import com.example.firstdemo.commons.enums.ResultCodes;
import com.example.firstdemo.commons.result.RestBody;
import com.example.firstdemo.dto.MemberApplyBo;
import com.example.firstdemo.model.MemberApplyParcelable;
import com.loopj.android.http.TextHttpResponseHandler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * 查询会员申请列表回调Handler
 * 
 * @author user2
 *
 */
public class MemberApplyQueryResponseHandler extends TextHttpResponseHandler {

	private static final String TAG = "MemberApplyQueryResponseHandler";

	private Handler handler;

	public MemberApplyQueryResponseHandler(Handler handler) {
		this.handler = handler;
	}

	@Override
	public void onFailure(int arg0, Header[] arg1, String arg2, Throwable arg3) {
		Log.v(TAG, arg2);
	}

	@Override
	public void onSuccess(int arg0, Header[] arg1, String arg2) {
		RestBody<List<MemberApplyBo>> memAppResult = JsonUtil.convertMemberApplyBos(arg2);
		if (ResultCodes.SUCCESS.getCode().equals(memAppResult.getCode())) {
			List<MemberApplyBo> memberApplyBos = memAppResult.getData();
			if (memberApplyBos == null) {
				memberApplyBos = Collections.emptyList();
			}
			MemberApplyParcelable value = new MemberApplyParcelable(memberApplyBos);
			Message message = new Message();
			message.what = 2;
			Bundle bundle = new Bundle();
			bundle.putParcelable("memApplys", value);
			message.setData(bundle);
			handler.sendMessage(message);
		} else {
			Log.v(TAG, "member apply query fail");
		}
	}

}
