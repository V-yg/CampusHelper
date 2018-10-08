package com.example.campushelper.Preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.campushelper.MyApplication;

/**
 * Created by 一钢 on 2018/9/15.
 */

public class UseroLoginPreferenceUtil {

    private static SharedPreferences preferences =MyApplication.getContext()
            .getSharedPreferences("login",Context.MODE_PRIVATE);

    public static void saveInfo(String key,String value){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public static void clearUserLogin(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }

    public static void saveUserPassword(String password){
        saveInfo("password",password);
    }

    public static void saveUserAccount(String account){
        saveInfo("account",account);
    }

    public static String getUserPassword(){
        return preferences.getString("password",null);
    }

    public static String getUserAccount(){
        return preferences.getString("account",null);
    }
}
