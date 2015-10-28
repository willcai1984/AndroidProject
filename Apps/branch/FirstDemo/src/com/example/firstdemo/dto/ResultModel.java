package com.example.firstdemo.dto;

import java.io.Serializable;

public class ResultModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4461197640505888612L;
	/**
	 * 错误代码
	 */
	private String code;

	/**
	 * 错误详细信息
	 */
	private String message;

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

	@Override
	public String toString() {
		return "ResultModel [code=" + code + ", message=" + message + "]";
	}

}
