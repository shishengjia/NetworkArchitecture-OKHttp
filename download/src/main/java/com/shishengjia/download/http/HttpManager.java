package com.shishengjia.download.http;

import android.content.Context;

import com.shishengjia.download.file.FileStroageManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 *
 */

public class HttpManager {

    private static final HttpManager httpManager = new HttpManager();
    private static final int NETWORK_CODE = 1;
    private Context mContext;
    private OkHttpClient mClient;


    public static HttpManager getInstance() {
        return httpManager;
    }

    private HttpManager() {
        mClient = new OkHttpClient();
    }

    public void init(Context context) {
        this.mContext = context;
    }

    /**
     * 同步请求
     *
     * @param url
     * @return
     */
    public Response syncRequest(String url) {
        Request request = new Request.Builder().url(url).build();
        try {
            return mClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 异步请求
     *
     * @param url
     * @return
     */
    public void asyncRequest(final String url, final DownloadCallback callback) {
        Request request = new Request.Builder().url(url).build();
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful() && callback != null)
                    callback.fail(NETWORK_CODE, "Request Failed");
                //根据url生成文件存储路径
                File file = FileStroageManager.getInstance().getFileByName(url);
                //文件缓冲区域
                byte[] buffer = new byte[1024 * 1024*10];
                //文件长度
                int len;
                //写入流
                FileOutputStream fileOut = new FileOutputStream(file);
                //获取输入流
                InputStream inStream = response.body().byteStream();
                //写入缓冲区buffer
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
