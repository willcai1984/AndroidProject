package com.qjdchina.pocketsale.handler;

import android.os.Bundle;
import android.os.Message;

import com.loopj.android.http.TextHttpResponseHandler;

/**
 * responseHandler抽象类 ，封装公共方法
 * 
 * @author user2
 *
 */
public abstract class AbstractResponseHandler extends TextHttpResponseHandler {

	/**
	 * 提供给UI主线程获取信息的Bundle
	 */
	private Bundle bundle = new Bundle();

	protected Bundle getBundle() {
		return bundle;
	}

	/**
	 * 由子类实现的表示当前handler支持的消息类型
	 * 
	 * @return
	 */
	protected abstract int getSupportMessageType();

	/**
	 * 封装Message，在UI主线程中handleMessage处理内容
	 * 
	 * @return
	 */
	public Message wrapMessage() {
		Message message = new Message();
		message.what = getSupportMessageType();
		message.setData(getBundle());
		return message;
	}
}
