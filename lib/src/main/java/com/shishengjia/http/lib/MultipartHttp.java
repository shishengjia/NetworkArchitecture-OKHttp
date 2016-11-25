package com.shishengjia.http.lib;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 利用HTTP协议中的multipart/form-data请求上传文件
 */

class MultipartHttp {
    public static void main(String args[]){
        //将图片装载在imageBody中
        RequestBody imageBody = RequestBody.create(MediaType.parse("image/jpeg"),
                new File("F:\\Cosplay\\New folder\\49195832663.jpg"));
        //封装提交的数据
        MultipartBody body = new MultipartBody.Builder().setType(MultipartBody.FORM).
                addFormDataPart("username","shishengjia"). //对应服务端的各个字段
                addFormDataPart("filename","test.jpg",imageBody).build();

        OkHttpClient client = new OkHttpClient();
        Request request  = new Request.Builder().
                url("http://localhost:8080/web/servlet/UpLoadServlet").post(body).build();
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
