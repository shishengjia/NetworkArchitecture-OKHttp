package com.shishengjia.http.lib;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 *测试 http缓存
 */

public class CacheHttp {
    public static void main(String args[]){
        int maxCacheSize = 10*1024*1024;
        Cache cache = new Cache(new File("F:\\Temp"),maxCacheSize);
        OkHttpClient client = new OkHttpClient.Builder().cache(cache).build();
        Request request = new Request.Builder().url("http://www.qq.com").build();
        //通过CacheControl中的noCache方法设置为不缓存，
        Request request_2 = new Request.Builder().url("http://www.qq.com").
                cacheControl(new CacheControl.Builder().noCache().build()).
                build();
        try {
            Response response_1 = client.newCall(request).execute();
            response_1.body().string();//读取网络请求的内容
            //如果内容从网络获取，response_1.networkResponse()不为空
            System.out.println("network response"+response_1.networkResponse());
            //如果内容从缓存中获取，response_1.cacheResponse()不为空
            System.out.println("cache response"+response_1.cacheResponse());

            System.out.print("--------------------------------------\n");

            Response response_2 = client.newCall(request).execute();
            response_2.body().string();
            System.out.println("network response"+response_2.networkResponse());
            System.out.println("cache response"+response_2.cacheResponse());

        } catch (IOException e) {
            e.printStackTrace();
        }

//        network responseResponse{protocol=http/1.1, code=200, message=OK, url=http://www.qq.com/}
//        cache responsenull
//            --------------------------------------
//        network responsenull
//        cache responseResponse{protocol=http/1.1, code=200, message=OK, url=http://www.qq.com/}

    }
}
