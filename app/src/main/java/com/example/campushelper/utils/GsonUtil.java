package com.example.campushelper.utils;


import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;

/**
 * Created by 一钢 on 2018/9/30.
 */

public class GsonUtil {

    //把对象通过GSON封装成string便于在http上传输
    public  static <T> String postJson(T type){
        Gson gson = new Gson();
        String result = gson.toJson(type);
        return result;
    }

    //把从http上接收的对象通过GSON解析为泛型
    public static <T> T getJson(String responseData, Type type){
        T t = null;
        try {
            t = new Gson().fromJson(responseData,type);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return t;
    }
}
