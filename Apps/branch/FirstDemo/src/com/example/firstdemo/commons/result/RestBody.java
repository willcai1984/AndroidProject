package com.example.firstdemo.commons.result;

import com.example.firstdemo.commons.enums.ResultCodes;

/**
 * 与服务端返回报文字段对应，以此解析报文
 * 
 * @author user2
 *
 * @param <T>
 */
public class RestBody<T> {

	/**
	 * 状态码
	 */
	private String code;

	/**
	 * 消息
	 */
	private String message;

	/**
	 * 数据
	 */
	private T data;

	/**
	 * 此私有构造方法必须显示定义，否则服务端返回报文解析报错
	 * 
	 * @see com.example.firstdemo.biz.util.JsonUtil#convert(String,
	 *      Class)
	 */
	private RestBody() {

	}

	public RestBody(String code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public RestBody(String code, String message) {
		this(code, message, null);
	}

	public RestBody(ResultCodes result) {
		this(result.getCode(), result.getMsg(), null);
	}

	public RestBody(ResultCodes result, T data) {
		this(result.getCode(), result.getMsg(), data);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "RestBody [code=" + code + ", message=" + message + ", data=" + data + "]";
	}
}
