package com.qjdchina.pocketsale.dto;

import android.os.Parcel;
import android.os.Parcelable;

import com.qjdchina.pocketsale.commons.enums.MemberAuthFile;

import java.io.Serializable;

/**
 * 数据传输对象：会员申请信息实体封装
 *
 * @author user2
 */
public class MemberPhotoBo implements Serializable {

    private static final long serialVersionUID = -726483688136459152L;

    /**
     * 服务器代号
     **/
    private MemberAuthFile authFile;

    /**
     * 本地图片路径
     **/
    private String localFile;

    /**
     * 服务器图片路径
     **/
    private String serverFile;

    public MemberAuthFile getAuthFile() {
        return authFile;
    }

    public void setAuthFile(MemberAuthFile authFile) {
        this.authFile = authFile;
    }

    public String getLocalFile() {
        return localFile;
    }

    public void setLocalFile(String localFile) {
        this.localFile = localFile;
    }

    public String getServerFile() {
        return serverFile;
    }

    public void setServerFile(String serverFile) {
        this.serverFile = serverFile;
    }

    @Override
    public String toString() {
        return "MemberPhotoBo{" +
                "authFile='" + authFile + '\'' +
                ", localFile=" + localFile +
                ", serverFile='" + serverFile + '\'' +
                '}';
    }

}
