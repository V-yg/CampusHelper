package com.example.campushelper.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.example.campushelper.ActivityManager;
import com.example.campushelper.Preferences.UseroLoginPreferenceUtil;
import com.example.campushelper.R;
import com.example.campushelper.utils.StatusBarUtil;

public class SplashActivity extends AppCompatActivity {

    private boolean firstEnter = true; // 是否首次进入

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ActivityManager.getInstance().addActivity(this);

        StatusBarUtil.transparencyBar(this);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (autoLogin()) {
                    MainActivity.start(SplashActivity.this);
                    finish();
                } else {
                    LoginActivity.start(SplashActivity.this);
                    finish();
                }
            }
        };

        new Handler().postDelayed(runnable, 1500);

//        if (firstEnter) {
//
//            firstEnter = false;
//        } else {
//            runnable.run();
//        }
    }

    @Override
    public void finish() {
        super.finish();
        //一个活动到另一个活动的过度动画
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    /**
     * 已经登陆过，自动登陆
     */
    private boolean autoLogin() {
        String account = UseroLoginPreferenceUtil.getUserAccount();
        String password = UseroLoginPreferenceUtil.getUserPassword();
        return !TextUtils.isEmpty(account) && !TextUtils.isEmpty(password);
    }

}
