package com.example.campushelper.Preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.campushelper.MyApplication;

/**
 * Created by 一钢 on 2018/9/30.
 */

public class UserInfoPreferenceUtil {

    private static SharedPreferences preferences = MyApplication.getContext()
            .getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

    public static void saveInfo(String key,String value){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public static void clearUserInfo(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }

    public static void saveUserName(String name){
        saveInfo("name",name);
    }

    public static void saveUserNum(String num){
        saveInfo("num",num);
    }

    public static void saveUserCollege(String college){
        saveInfo("college",college);
    }

    public static void saveUserSchool(String school){
        saveInfo("school",school);
    }

    public static String getUserName(){
        return preferences.getString("name",null);
    }

    public static String getUserNum(){
        return preferences.getString("num",null);
    }

    public static String getUserCollege(){
        return preferences.getString("college",null);
    }

    public static String getUserSchool(){
        return preferences.getString("school",null);
    }
}
