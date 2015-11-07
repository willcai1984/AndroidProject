package com.qjdchina.pocketsale.commons.enums;

/**
 * 手机通讯消息状态
 *
 * @author user2
 */
public enum MessageType {

    /**
     * 登录
     **/
    LOGIN(1),

    /**
     * 会员申请列表查询
     **/
    MEMAPP_QUERY(2),

    /**
     * 上传文件
     **/
    UPLOAD_FILE(3),

    /**
     * 上传会员认证文件
     **/
    UPLOAD_MATERIAL(4),

    /**
     * 客户评分
     */
    RATING(5),

    /**
     * 客户评分查询
     */
    QUERY_RATING(6),

    /**
     * 上传记录查询
     */
    QUERY_UPLOAD(7);


    private int type;

    MessageType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

}
