package com.shishengjia.download;

import com.shishengjia.download.http.DownloadCallback;
import com.shishengjia.download.http.HttpManager;

import java.io.IOException;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 多线程下载管理类
 */

public class DownloadManager {
    //核心线程数
    private static final int MAX_THREAD = 2;
    private static final DownloadManager dManager = new DownloadManager();

    //定义各个线程池
    private static final ThreadPoolExecutor sThreadPool = new ThreadPoolExecutor(MAX_THREAD, MAX_THREAD,
            60, TimeUnit.MILLISECONDS, new SynchronousQueue<Runnable>(), new ThreadFactory() {
        private AtomicInteger mInteger = new AtomicInteger(1);
        @Override
        public Thread newThread(Runnable runable) {
            Thread thread = new Thread(runable, "download thread #" + mInteger.getAndIncrement());
            return thread;
        }
    });

    //单例
    public static DownloadManager getInstance() {
        return dManager;
    }

    private DownloadManager() {
    }

    /**
     * 下载
     * @param url
     * @param callback
     */
    public void download(final String url, final DownloadCallback callback) {

        HttpManager.getInstance().asyncRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful() && callback != null) {
                    callback.fail(HttpManager.NETWORK_ERROR_CODE, "Network Error");
                    return;
                }
                //获取文件长度
                long length = response.body().contentLength();
                if (length == -1) {
                    callback.fail(HttpManager.CONTENT_LENGTH_ERROR_CODE, "content length -1");
                    return;
                }

                processDownload(url,length,callback);

            }
        });

    }

    /**
     * 处理下载
     * @param url
     * @param length
     * @param callBack
     */
    private void processDownload(String url, long length, DownloadCallback callBack) {
        //每个线程下载文件的大小
        long threadDownloadSize = length / MAX_THREAD;
        //动态设置文件下载起始位置，并进行下载
        for(int i = 0;i<MAX_THREAD;i++){
            long start = i*threadDownloadSize;
            long end = (i+1)*threadDownloadSize-1;
            sThreadPool.execute(new DownloadRunnable(start,end,url,callBack));
        }
    }


}
