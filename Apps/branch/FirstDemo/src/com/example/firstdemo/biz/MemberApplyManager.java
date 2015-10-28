package com.example.firstdemo.biz;

import java.util.List;

import com.example.firstdemo.commons.result.Result;
import com.example.firstdemo.dto.MemberApplyBo;
import com.example.firstdemo.handler.LoginResponseHandler;
import com.example.firstdemo.handler.MemberApplyQueryResponseHandler;
import com.example.firstdemo.handler.UploadResponseHandler;

/**
 * 会员操作接口
 * 
 * @author user2
 *
 */
public interface MemberApplyManager {

	/**
	 * 会员登录接口
	 * 
	 * @param userId
	 *            账号
	 * @param password
	 *            密码
	 * @param loginResponseHandler
	 *            登录回调
	 * @return
	 */
	public Result<String> doLogin(String userId, String password, LoginResponseHandler loginResponseHandler);

	/**
	 * 会员认证所需文件上传接口
	 * 
	 * @param memAppId
	 *            会员申请ID
	 * @param fileCateCode
	 *            认证资料类别编码
	 * @param serverFilePath
	 *            服务端文件存储路径
	 * @param uploadResponseHandler
	 *            上传回调
	 * @return
	 */
	public Result<String> doUploadMemberAuthFile(String memAppId, String fileCateCode, String serverFilePath,
			UploadResponseHandler uploadResponseHandler);

	/**
	 * 批量上传会员认证所需文件接口
	 * 
	 * @param memAppId
	 *            会员申请ID
	 * @param fileCateCode
	 *            认证资料类别编码
	 * @param serverFilePaths
	 *            服务端文件存储路径
	 * @param uploadResponseHandler
	 *            上传回调
	 * @return
	 */
	public Result<List<String>> doBatchUploadMemberAuthFile(String memAppId, String fileCateCode,
			List<String> serverFilePaths, UploadResponseHandler uploadResponseHandler);

	/**
	 * 根据操作员ID查询对应会员申请列表
	 * 
	 * @param operatorId
	 *            操作員ID
	 * @param queryResponseHandler
	 *            查询结果回调
	 * @return
	 */
	public Result<List<MemberApplyBo>> findMemberApplyBoListByOperatorId(String operatorId,
			MemberApplyQueryResponseHandler queryResponseHandler);
}
