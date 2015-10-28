package com.example.firstdemo.dto;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 数据传输对象：会员申请信息实体封装
 * 
 * @author user2
 *
 */
public class MemberApplyBo implements Serializable, Parcelable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -726483688136459152L;

	/** 申请表ID **/
	private Integer id;

	/** 公司名称 **/
	private String corpName;

	/** 公司地址 **/
	private String address;
	/**
	 * 当前步骤
	 */
	private Integer step;

	/**
	 * 网站信息验证结果
	 */
	private String status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCorpName() {
		return corpName;
	}

	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getStep() {
		return step;
	}

	public void setStep(Integer step) {
		this.step = step;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "MemberApplyBo [id=" + id + ", corpName=" + corpName + ", address=" + address + ", step=" + step
				+ ", status=" + status + "]";
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.id);
		dest.writeString(corpName);
		dest.writeString(address);
		dest.writeInt(this.step);
		dest.writeString(status);
	}

}
