package com.example.campushelper;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 一钢 on 2018/10/1.
 */

public class ActivityManager {

    private List<Activity> AllActivitites = new ArrayList<Activity>();
    private static ActivityManager instance;

    private ActivityManager(){

    }

    public synchronized static ActivityManager getInstance() {
        if (null == instance) {
            instance = new ActivityManager();
        }
        return instance;
    }

    //在Activity基类的onCreate()方法中执行
    public void addActivity(Activity activity) {
        AllActivitites.add(activity);
    }

    //注销是销毁所有的Activity
    public void OutSign() {
        for (Activity activity : AllActivitites) {
            if (activity != null) {
                activity.finish();
            }
        }
    }

}
