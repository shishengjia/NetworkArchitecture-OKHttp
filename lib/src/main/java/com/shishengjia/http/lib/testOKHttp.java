package com.shishengjia.http.lib;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by shi on 16/11/2016.
 */

public class testOKHttp {

    //同步请求
    public static void sendSyncRequest(String url) {
        //请求前获取当前线程ID
        System.out.println(Thread.currentThread().getId());
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                //请求后获取当前线程ID
                System.out.print(Thread.currentThread().getId());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //异步请求
    public static void sendAsyncRequest(String url) {
        //请求前获取当前线程ID
        System.out.println(Thread.currentThread().getId());
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Error");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //请求后获取当前线程ID
                System.out.println(Thread.currentThread().getId());
            }
        });
    }

    public static void main(String[] args) {
//        sendSyncRequest("https://www.baidu.com");
        sendAsyncRequest("https://www.baidu.com");

    }
}
