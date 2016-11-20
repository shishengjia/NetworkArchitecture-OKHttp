package com.shishengjia.http.lib;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 测试HTTP请求头
 */

public class HeadHttp {
    public static void main(String args[]) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().
                url("https://www.baidu.com").addHeader("User-Agent", "from shishengjia").build();
        try {
            Response response = client.newCall(request).execute();
            Headers headers = response.headers();
            if(response.isSuccessful()){
                for(int i=0;i<headers.size();i++)
                    System.out.println(headers.name(i)+":"+headers.value(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
