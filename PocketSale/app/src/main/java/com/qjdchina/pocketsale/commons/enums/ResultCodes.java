package com.qjdchina.pocketsale.commons.enums;

/**
 * 这里的错误编码，和前端页面保持了一致，每个枚举编码不能随意变动！！！
 * 
 * @author user2
 *
 */
public enum ResultCodes {

	SUCCESS("0", "成功"), FAILED("1", "失败");

	private String code;
	private String msg;

	private ResultCodes(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
