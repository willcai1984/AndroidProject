package com.qjdchina.pocketsale.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * 数据传输对象：会员申请信息实体封装
 *
 * @author user2
 */
public class MemberApplyBo implements Serializable, Parcelable {
    /**
     *
     */
    private static final long serialVersionUID = -726483688136459152L;

    /**
     * 申请表ID
     **/
    private Integer id;

    /**
     * 公司名称
     **/
    private String corpName;

    /**
     * 公司地址
     **/
    private String address;
    /**
     * 当前步骤
     */
    private Integer step;

    /**
     * 审核结果：是否上传完成+是否提交评分
     * 1-完成；0-未完成
     */
    private String status;

    /**
     * 会员申请日期
     */
    private String createDate;

    /**
     * 会员ID
     */
    private String memberId;

    /**
     * 公司ID
     */
    private String companyId;

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

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getGmtCreated() {
        return createDate;
    }

    public void setGmtCreated(String gmtCreated) {
        this.createDate = gmtCreated;
    }

    @Override
    public String toString() {
        return "MemberApplyBo{" +
                "address='" + address + '\'' +
                ", id=" + id +
                ", corpName='" + corpName + '\'' +
                ", step=" + step +
                ", status='" + status + '\'' +
                ", createDate='" + createDate + '\'' +
                ", memberId='" + memberId + '\'' +
                ", companyId='" + companyId + '\'' +
                '}';
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
        dest.writeString(createDate);
        dest.writeString(memberId);
        dest.writeString(companyId);
    }

}
