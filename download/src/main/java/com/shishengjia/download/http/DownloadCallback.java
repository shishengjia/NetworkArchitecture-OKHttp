package com.shishengjia.download.http;

import java.io.File;

/**
 * 下载回调函数
 */
public interface DownloadCallback {

    //下载成功后传入file，供上层函数调用
    void success(File file);

    //失败传入errorCode
    void fail(int errorCode,String errorMessage);

    //传入下载进度
    void progress(int progress);
}
