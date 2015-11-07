package com.qjdchina.pocketsale.commons.result;

import com.qjdchina.pocketsale.commons.enums.ResultCodes;

import java.io.Serializable;
import java.util.Map;

/**
 * 统一接口返回类型
 * @author user2
 *
 * @param <T>
 */
public final class Result<T> implements Serializable {

	private static final long serialVersionUID = 5609042603379249954L;

	/**
	 * 错误代码
	 */
	private String code;

	/**
	 * 错误详细信息
	 */
	private String message;

	/**
	 * 错误信息
	 */
	private Map<String, String> errorMsgs;

	/**
	 * 操作是否成功
	 */
	private boolean success = false;

	/**
	 * 返回的数据，调用失败的是，返回数据为null
	 */
	private T dataModel;

	public Result(int code, String message) {
		this.code = "" + code;
		this.message = message;
	}

	public Result(ResultCodes resultCode) {
		this.code = resultCode.getCode();
		this.message = resultCode.getMsg();
	}

	// TODO 添加resultcodes构造器
	public Result(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public Result(T dataModel) {
		this.dataModel = dataModel;
		this.success = true;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public boolean isSuccess() {
		return success;
	}

	public T getDataModel() {
		return dataModel;
	}

	public Map<String, String> getErrorMsgs() {
		return errorMsgs;
	}

	public void setErrorMsgs(Map<String, String> errorMsgs) {
		this.errorMsgs = errorMsgs;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setDataModel(T dataModel) {
		this.dataModel = dataModel;
	}

	@Override
	public String toString() {
		return "Result [code=" + code + ", message=" + message + ", errorMsgs=" + errorMsgs + ", success=" + success
				+ ", dataModel=" + dataModel + "]";
	}

}
