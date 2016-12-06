package com.shishengjia.http.download;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 测试http相关字段 Range，content-length
 */

public class RangeHttp {
    public static void main(String args[]){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().
                addHeader("Range","bytes=0-2048"). //截取指定长度的内容
                url("http://t1.zngirls.com/gallery/19705/19815/003.jpg").
                build();
        try {
            Response response = client.newCall(request).execute();
            //如果响应头没有content-length字段或者content-length=0，则返回-1.
            System.out.println("content-length: "+response.body().contentLength());
            if(response.isSuccessful()){
                Headers headers = response.headers();
                //打印响应头信息
                for(int i=0;i<headers.size();i++)
                    System.out.println(headers.name(i)+" : "+headers.value(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
