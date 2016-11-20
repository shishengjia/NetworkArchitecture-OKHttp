package com.shishengjia.http.lib;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * http get请求添加参数
 */

public class QueryHttp {
    public static void main(String args[]) {
        OkHttpClient client = new OkHttpClient();
        //调用聚合数据的api
        HttpUrl httpUrl = HttpUrl.parse("http://v.juhe.cn/weather/citys").newBuilder().
                addQueryParameter("cityname", "北京"). //添加参数
                addQueryParameter("key", "9d6107fdabb0479b127fb18fac5c3534").
                build();
        String url = httpUrl.toString();
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful())
                System.out.println(response.body().string());//打印返回结果
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
