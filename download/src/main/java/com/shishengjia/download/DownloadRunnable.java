package com.shishengjia.download;

import com.shishengjia.download.file.FileStroageManager;
import com.shishengjia.download.http.DownloadCallback;
import com.shishengjia.download.http.HttpManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.Response;

/**
 *
 */

public class DownloadRunnable implements Runnable{

    //下载开始位置
    private long mStart;

    //下载结束位置
    private long mEnd;

    private String mUrl;

    private DownloadCallback mCallback;

    public DownloadRunnable(long mStart, long mEnd, String mUrl, DownloadCallback callback) {
        this.mStart = mStart;
        this.mEnd = mEnd;
        this.mUrl = mUrl;
        this.mCallback = callback;
    }

    @Override
    public void run() {

        Response response = HttpManager.getInstance().syncRequestByRange(mUrl,mStart,mEnd);

        if(response==null&&mCallback!=null){
            mCallback.fail(HttpManager.NETWORK_ERROR_CODE,"Network Error");
            return;
        }

        File file = FileStroageManager.getInstance().getFileByName(mUrl);

        try {
            //采用多线程下载，所以使用可以任意的访问文件的任何地方的RandomAccessFile类
            RandomAccessFile randomAccessFile = new RandomAccessFile(file,"rwd");
            //设置一个文件读取位置的偏移量
            randomAccessFile.seek(mStart);
            byte[] buffer = new byte[1024 * 1024*10];
            //文件长度
            int len;
            //获取输入流
            InputStream inStream = response.body().byteStream();
            //写入缓冲区buffer
            while ((len = inStream.read(buffer, 0, buffer.length)) != -1) {
                //写入到文件中
                randomAccessFile.write(buffer,0,len);
            }
            mCallback.success(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
