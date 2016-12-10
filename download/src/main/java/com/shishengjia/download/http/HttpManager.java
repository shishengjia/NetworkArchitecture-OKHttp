package com.shishengjia.download.http;

import android.content.Context;

import com.shishengjia.download.file.FileStroageManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 *Http请求类
 */

public class HttpManager {

    private static final HttpManager httpManager = new HttpManager();
    public static final int NETWORK_ERROR_CODE = 1;
    public static final int CONTENT_LENGTH_ERROR_CODE = 2;
    private Context mContext;
    private OkHttpClient mClient;

    //单例模式，返回唯一实例
    public static HttpManager getInstance() {
        return httpManager;
    }

    //对外隐藏构造方法，确保唯一实例
    private HttpManager() {
        mClient = new OkHttpClient();
    }

    public void init(Context context) {
        this.mContext = context;
    }

    /**
     * 同步请求
     * @param url
     * @param start 请求文件开始位置
     * @param end   请求文件结束位置
     * @return response
     */
    public Response syncRequestByRange(String url, long start, long end) {
        Request request = new Request.Builder().url(url)
                .addHeader("Range", "bytes=" + start + "-" + end)
                .build();
        try {
            return mClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 异步请求
     * @param url
     * @param callback
     */
    public void asyncRequest(final String url, Callback callback) {
        Request request = new Request.Builder().url(url).build();
        mClient.newCall(request).enqueue(callback);
    }

    /**
     * 异步请求
     * @param url
     * @param callback
     */
    public void asyncRequest(final String url, final DownloadCallback callback) {
        Request request = new Request.Builder().url(url).build();
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful() && callback != null) {
                    callback.fail(NETWORK_ERROR_CODE, "Request Failed");
                }
                //根据url生成file实例
                File file = FileStroageManager.getInstance().getFileByName(url);
                //文件缓冲区域,10Mb
                byte[] buffer = new byte[1024 * 1024 * 10];
                //文件长度
                int len;
                //文件写入流,写到file中
                FileOutputStream fileOut = new FileOutputStream(file);
                //获取输入流
                InputStream inStream = response.body().byteStream();
                //写入缓冲区buffer,随后写入到文件中
                while ((len = inStream.read(buffer, 0, buffer.length)) != -1) {
                    //写入到文件中
                    fileOut.write(buffer, 0, len);
                    fileOut.flush();
                }
                callback.success(file);
            }
        });
    }

}
