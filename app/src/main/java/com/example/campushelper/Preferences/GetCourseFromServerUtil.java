package com.example.campushelper.Preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.campushelper.MyApplication;

/**
 * Created by 一钢 on 2018/10/4.
 */

public class GetCourseFromServerUtil {
    private static SharedPreferences preferences = MyApplication.getContext()
            .getSharedPreferences("firstGet", Context.MODE_PRIVATE);

    public static void saveInfo(String key,Boolean value){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key,value);
        editor.commit();
    }

    public static void setFromLocal(Boolean value){
        saveInfo("hasGetCourse",value);
    }

    public static Boolean getFromLocal(){
        return preferences.getBoolean("hasGetCourse",false);
    }
}
