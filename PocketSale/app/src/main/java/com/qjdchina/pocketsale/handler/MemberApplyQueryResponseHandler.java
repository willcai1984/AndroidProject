package com.qjdchina.pocketsale.handler;

import java.util.Collections;
import java.util.List;

import org.apache.http.Header;

import com.qjdchina.pocketsale.biz.util.JsonUtil;
import com.qjdchina.pocketsale.commons.Constants;
import com.qjdchina.pocketsale.commons.enums.MessageType;
import com.qjdchina.pocketsale.commons.enums.ResultCodes;
import com.qjdchina.pocketsale.commons.result.RestBody;
import com.qjdchina.pocketsale.dto.MemberApplyBo;
import com.qjdchina.pocketsale.model.MemberApplyParcelable;

import android.os.Handler;
import android.util.Log;

/**
 * 查询会员申请列表回调Handler
 * 
 * @author user2
 *
 */
public class MemberApplyQueryResponseHandler extends AbstractResponseHandler {

	private static final String TAG = "MemberApplyQueryResponseHandler";

	private Handler handler;

	public MemberApplyQueryResponseHandler(Handler handler) {
		super();
		this.handler = handler;
	}

	@Override
	public void onFailure(int arg0, Header[] arg1, String arg2, Throwable arg3) {
		Log.v(TAG, arg2);

		handler.sendMessage(wrapMessage());
		getBundle().putString(Constants.RESULT_CODE, ResultCodes.FAILED.getCode());
		getBundle().putString(Constants.RESULT_MESSAGE, "接口异常");
	}

	@Override
	public void onSuccess(int arg0, Header[] arg1, String arg2) {
		RestBody<List<MemberApplyBo>> memAppResult = JsonUtil.convertMemberApplyBos(arg2);
		handler.sendMessage(wrapMessage());
		getBundle().putString(Constants.RESULT_CODE, memAppResult.getCode());
		getBundle().putString(Constants.RESULT_MESSAGE, memAppResult.getMessage());

		if (ResultCodes.SUCCESS.getCode().equals(memAppResult.getCode())) {
			List<MemberApplyBo> memberApplyBos = memAppResult.getData();
			if (memberApplyBos == null) {
				memberApplyBos = Collections.emptyList();
			}
			MemberApplyParcelable value = new MemberApplyParcelable(memberApplyBos);
			getBundle().putParcelable(Constants.MEMBER_APPLY_RESULT, value);
		} else {
			Log.v(TAG, "member apply query fail");
		}
	}

	@Override
	protected int getSupportMessageType() {
		return MessageType.MEMAPP_QUERY.getType();
	}

}
