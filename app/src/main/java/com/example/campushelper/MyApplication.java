package com.example.campushelper;

import android.app.Application;
import android.content.Context;

/**
 * Created by 一钢 on 2018/9/15.
 */

public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }

}
