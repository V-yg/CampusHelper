package com.example.campushelper.utils.http;

import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by 一钢 on 2018/9/29.
 */

public class HttpUtil {

    private static OkHttpClient client = null;
    private static Request request = null;

    public static void sendRequest(String url,RequestBody requestBody,okhttp3.Callback callback){

        client = new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(5, TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(5, TimeUnit.SECONDS)//设置连接超时时间;
                .retryOnConnectionFailure(false)
                .build();

        request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(callback);
    }
    public static void sendOkHttpRequestWithJson(String url, String json, okhttp3.Callback callback) {

        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        sendRequest(url,requestBody,callback);
    }

    public static void sendOkHttpRequestWithString(String url,String data,okhttp3.Callback callback){

        RequestBody requestBody = new FormBody.Builder()
                .add("account",data)
                .build();
        sendRequest(url,requestBody,callback);

    }

}
