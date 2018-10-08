package com.example.campushelper.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.campushelper.ActivityManager;
import com.example.campushelper.Preferences.UserInfoPreferenceUtil;
import com.example.campushelper.Preferences.UseroLoginPreferenceUtil;
import com.example.campushelper.R;

/**
 * Created by 一钢 on 2018/10/1.
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    private Button quieButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setTitleText("我的设置");
        ActivityManager.getInstance().addActivity(this);

        quieButton = bindView(R.id.quit_button);
        quieButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.quit_button:
                quit();
                break;
        }
    }

    public void quit(){
        UserInfoPreferenceUtil.clearUserInfo();
        UseroLoginPreferenceUtil.clearUserLogin();
        LoginActivity.start(SettingActivity.this);

        ActivityManager.getInstance().OutSign();
        finish();

    }
}
