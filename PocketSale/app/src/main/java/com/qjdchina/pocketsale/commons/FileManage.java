package com.qjdchina.pocketsale.commons;

import com.qjdchina.pocketsale.biz.impl.BaseManage;
import com.qjdchina.pocketsale.commons.enums.ResultCodes;
import com.qjdchina.pocketsale.commons.result.Result;
import com.qjdchina.pocketsale.handler.UploadResponseHandler;

import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.util.Iterator;
import java.util.List;

/**
 * 公用文件操作接口实现
 *
 * @author user2
 */
public class FileManage {

    private static final String TAG = "FileManage";

    /**
     * 上传文件格式
     */
    private String contentType = "application/octet-stream";

    public FileManage() {
        super();
    }

    public FileManage(String url) {
        super();
        this.url = url;
    }

    /**
     * 服务器处理地址
     */
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 通用文件上传方法
     *
     * @param filePath              文件在本地存储路径
     * @param uploadResponseHandler 上传回调方法
     * @return
     */
    public Result<String> doUpload(String filePath, UploadResponseHandler uploadResponseHandler) {
        if (StringUtils.isBlank(filePath)) {
            return new Result<String>(ResultCodes.FAILED.getCode(), "待上传文件路径不能为空");
        }

        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        }
        CommonHttpClientParam param = new CommonHttpClientParam();
        param.addParam("fileName", file);
        param.addParam("contentType", contentType);

        try {
            BaseManage.doCommonAsyncHttpPost(url, param, uploadResponseHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 批量上传方法
     *
     * @param filePaths             文件在本地存储路径
     * @param uploadResponseHandler 上传回调方法
     * @return
     */
    public Result<List<String>> doBatchUpload(List<String> filePaths, UploadResponseHandler uploadResponseHandler) {
        if (filePaths == null || filePaths.isEmpty()) {
            return new Result<List<String>>(ResultCodes.FAILED.getCode(), "待上传文件列表不能为空");
        }

        for (Iterator<String> iter = filePaths.iterator(); iter.hasNext(); ) {
            doUpload(iter.next(), uploadResponseHandler);
        }
        return null;

    }

}
