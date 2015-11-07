package com.qjdchina.pocketsale.biz;

import java.util.List;
import java.util.Map;

import com.qjdchina.pocketsale.commons.result.Result;
import com.qjdchina.pocketsale.dto.MemberApplyBo;
import com.qjdchina.pocketsale.handler.LoginResponseHandler;
import com.qjdchina.pocketsale.handler.MemberApplyQueryResponseHandler;
import com.qjdchina.pocketsale.handler.MemberRatingQueryResponseHandler;
import com.qjdchina.pocketsale.handler.MemberRatingResponseHandler;
import com.qjdchina.pocketsale.handler.UploadMaterialQueryResponseHandler;
import com.qjdchina.pocketsale.handler.UploadMaterialResponseHandler;

/**
 * 会员操作接口
 *
 * @author user2
 */
public interface MemberApplyManager {

    /**
     * 会员登录接口
     *
     * @param userId               账号
     * @param password             密码
     * @param loginResponseHandler 登录回调
     * @return
     */
    public Result<String> doLogin(String userId, String password, LoginResponseHandler loginResponseHandler);

    /**
     * 根据操作员ID查询对应会员申请列表
     *
     * @param operatorId           操作員ID
     * @param pageNo               查询第几页的数据
     * @param queryResponseHandler 查询结果回调
     * @return
     */
    public Result<List<MemberApplyBo>> findMemberApplyBoListByOperatorId(String operatorId, Integer pageNo,
                                                                         MemberApplyQueryResponseHandler queryResponseHandler);

    /**
     * 会员认证所需文件上传接口
     *
     * @param memAppId                      会员申请ID
     * @param fileCateCode                  认证资料类别编码
     * @param serverFilePath                服务端文件存储路径
     * @param uploadMaterialResponseHandler 上传回调
     * @return
     */
    public Result<String> doUploadMemberAuthFile(Integer memAppId, String fileCateCode, String serverFilePath,
                                                 UploadMaterialResponseHandler uploadMaterialResponseHandler);

    /**
     * 批量上传会员认证所需文件接口
     *
     * @param memAppId                      会员申请ID
     * @param fileCateCode                  认证资料类别编码
     * @param serverFilePaths               服务端文件存储路径
     * @param uploadMaterialResponseHandler 上传回调
     * @return
     */
    public Result<List<String>> doBatchUploadMemberAuthFile(Integer memAppId, String fileCateCode,
                                                            List<String> serverFilePaths, UploadMaterialResponseHandler uploadMaterialResponseHandler);


    /**
     * 根据会员ID、认证资料类别编码查询文件上传记录
     *
     * @param memAppId                   会员申请ID
     * @param fileCateCode               认证资料类别编码
     * @param queryUploadResponseHandler 查询回调
     * @return
     */
    public Result<Map<String, List<String>>> findMemberAuthFileUploadsByCondition(Integer memAppId, String fileCateCode, UploadMaterialQueryResponseHandler queryUploadResponseHandler);


    /**
     * 会员评分操作
     *
     * @param companyId                   公司ID
     * @param params                      参数封装map
     * @param memberRatingResponseHandler 评分回调
     * @return
     */
    public Result<Map<String, Object>> doMemberRating(String companyId, Map<String, Object> params, MemberRatingResponseHandler memberRatingResponseHandler);

    /**
     * 会员评分查询
     *
     * @param companyId                        公司ID
     * @param memberRatingQueryResponseHandler 查询回调
     * @return
     */
    public Result<Map<String, Object>> findMemberRatingResult(String companyId, MemberRatingQueryResponseHandler memberRatingQueryResponseHandler);
}
