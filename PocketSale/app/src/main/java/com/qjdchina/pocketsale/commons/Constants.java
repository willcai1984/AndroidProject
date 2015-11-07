package com.qjdchina.pocketsale.commons;

/**
 * 定义用到的一些常量
 *
 * @author user2
 */
public interface Constants {

    /***************** 参数名定义 start *****************/
    /**
     * 返回编码，一般在Handler中获取用来判断接口调用结果
     **/
    public static final String RESULT_CODE = "result_code";

    /**
     * 返回信息，一般在Handler中获取用来判断接口调用结果信息
     **/
    public static final String RESULT_MESSAGE = "result_message";

    /**
     * 返回会员列表信息
     **/
    public static final String MEMBER_APPLY_RESULT = "member_apply_result";

    /**
     * 返回上传文件成功后在服务器存放路径信息
     **/
    public static final String SERVER_FILE_PATH = "server_file_path";

    /**
     * 返回会员文件上传成功后在服务器存放路径信息
     **/
    public static final String SERVER_MATERIAL_FILE_PATH = "server_material_file_path";
    /***************** 参数名定义 start *****************/

    /**
     * 会员评分项 年销量
     **/
    public static final String MEMBER_MARK_SALE = "mark_sale";

    /**
     * 会员评分项 年销量评分值
     **/
    public static final String MEMBER_MARK_SALE_SCORE = "mark_sale_score";
    /**
     * 会员评分项 注册资本
     **/
    public static final String MEMBER_MARK_CAPITAL = "mark_capital";

    /**
     * 会员评分项 注册资本评分值
     **/
    public static final String MEMBER_MARK_CAPITAL_SCORE = "mark_capital_score";
    /**
     * 会员评分项 成立年限
     **/
    public static final String MEMBER_MARK_YEARS = "mark_years";
    /**
     * 会员评分项 成立年限评分值
     **/
    public static final String MEMBER_MARK_YEARS_SCORE = "mark_years_score";
    /**
     * 会员评分项 管理者行业经验
     **/
    public static final String MEMBER_MARK_EXPERIENCE = "mark_experience";

    /**
     * 会员评分项 管理者行业经验评分值
     **/
    public static final String MEMBER_MARK_EXPERIENCE_SCORE = "mark_experience_score";
    /**
     * 会员评分项 团队规模
     **/
    public static final String MEMBER_MARK_SIZE = "mark_size";
    /**
     * 会员评分项 团队规模评分值
     **/
    public static final String MEMBER_MARK_SIZE_SCORE = "mark_size_score";
    /**
     * 会员评分项 家庭净资产
     **/
    public static final String MEMBER_MARK_WORTH = "mark_worth";

    /**
     * 会员评分项 家庭净资产评分值
     **/
    public static final String MEMBER_MARK_WORTH_SCORE = "mark_worth_score";
}
