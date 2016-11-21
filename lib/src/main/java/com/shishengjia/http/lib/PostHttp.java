package com.shishengjia.http.lib;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 *向自己搭建的post服务发送一个post请求，传递参数
 */

public class PostHttp {
    public static void main(String args[]){
        OkHttpClient client = new OkHttpClient();
        //post请求传递参数需要这个类
        FormBody body = new FormBody.Builder().add("username","shishengjia").
                add("userage","22").build();
        Request request = new Request.Builder().
                url("http://localhost:8080/web/servlet/HelloServlet").
                post(body).build();
        try {
            Response response = client.newCall(request).execute();
            if(response.isSuccessful()){
                System.out.println(response.body().string());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
