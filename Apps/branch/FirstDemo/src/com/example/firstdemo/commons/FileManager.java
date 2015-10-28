package com.example.firstdemo.commons;

import java.util.List;

import com.example.firstdemo.commons.result.Result;

/**
 * 公用文件操作接口
 * 
 * @author user2
 *
 */
public interface FileManager {
	/**
	 * 上传文件至服务器
	 * 
	 * @param filePath
	 *            文件在本地的存储路径
	 * @return Result中的data为服务器保存文件路径
	 */
	public Result<String> doUpload(String filePath);

	/**
	 * 批量上传文件
	 * 
	 * @param filePaths
	 * @return
	 */
	public Result<List<String>> doBatchUpload(List<String> filePaths);
}
