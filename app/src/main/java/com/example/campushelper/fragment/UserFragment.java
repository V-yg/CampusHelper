package com.example.campushelper.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.campushelper.Constant;
import com.example.campushelper.MyApplication;
import com.example.campushelper.Preferences.UserInfoPreferenceUtil;
import com.example.campushelper.Preferences.UseroLoginPreferenceUtil;
import com.example.campushelper.R;
import com.example.campushelper.activities.SettingActivity;
import com.example.campushelper.entity.UserInfo;
import com.example.campushelper.utils.GsonUtil;
import com.example.campushelper.utils.http.HttpUtil;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by 一钢 on 2018/9/29.
 */

public class UserFragment extends BaseFragment implements View.OnClickListener {

    private TextView userName_tv;
    private TextView userCollege_tv;
    private TextView userSchool_tv;
    private TextView userNum_tv;
    private CircleImageView userHead_civ;
    private CircleImageView userSchool_civ;
    private RelativeLayout userSetting_rl;
    private RelativeLayout userTimetable_rl;
    private static final String TAG = "UserFragment";
    private Handler handler = null;
    private UserInfo userInfo = null;

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_user;
    }

    @Override
    protected void initView() {
        userName_tv = findViewById(R.id.user_name_tv);
        userCollege_tv = findViewById(R.id.user_college_tv);
        userSchool_tv = findViewById(R.id.user_school_tv);
        userNum_tv = findViewById(R.id.user_num_tv);
        userHead_civ = findViewById(R.id.user_head_circleimageview);
        userSchool_civ = findViewById(R.id.user_school_circleimageview);
        userSetting_rl = findViewById(R.id.my_setting);
        userTimetable_rl = findViewById(R.id.my_timetable);
    }

    @Override
    protected void setListener() {
        userHead_civ.setOnClickListener(this);
        userSchool_civ.setOnClickListener(this);
        userSetting_rl.setOnClickListener(this);
        userTimetable_rl.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_head_circleimageview:
                break;
            case R.id.user_school_circleimageview:
                break;
            case R.id.my_setting:
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.my_timetable:
                break;
            default:
                break;
        }
    }

    @Override
    protected void initData(Bundle arguments) {

        handler = new Handler();

        if(UserInfoPreferenceUtil.getUserNum() == null ||
                !UserInfoPreferenceUtil.getUserNum().equals(UseroLoginPreferenceUtil.getUserAccount())){
            getUserFromServer();
        }else {
            getUserInfoFromLocal();
        }

    }

    public void getUserInfoFromLocal(){
        userNum_tv.setText(UserInfoPreferenceUtil.getUserNum());
        userName_tv.setText(UserInfoPreferenceUtil.getUserName());
        userSchool_tv.setText(UserInfoPreferenceUtil.getUserSchool());
        userCollege_tv.setText(UserInfoPreferenceUtil.getUserCollege());
    }

    public void getUserFromServer(){

        HttpUtil.sendOkHttpRequestWithString(Constant.URL_USERINFO, UseroLoginPreferenceUtil.getUserAccount(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                if (e instanceof SocketTimeoutException) {
                    Toast.makeText(MyApplication.getContext(), "服务器连接超时", Toast.LENGTH_SHORT).show();
                }
                if (e instanceof ConnectException) {
                    Toast.makeText(MyApplication.getContext(), "服务器连接异常", Toast.LENGTH_SHORT).show();
                }
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    String responseData = response.body().string().trim();
                    userInfo = GsonUtil.getJson(responseData, UserInfo.class);

                    new Thread() {
                        public void run(){
                            handler.post(runnableUi);
                        }
                    }.start();
                }
            }
        });
    }

    Runnable runnableUi = new Runnable() {
        @Override
        public void run() {
            if (userInfo == null) {
                return;
            }
            UserInfoPreferenceUtil.saveUserNum(userInfo.getNum().trim());
            UserInfoPreferenceUtil.saveUserName(userInfo.getName().trim());
            UserInfoPreferenceUtil.saveUserCollege(userInfo.getCollege().trim());
            UserInfoPreferenceUtil.saveUserSchool(userInfo.getSchool().trim());

            userNum_tv.setText(UserInfoPreferenceUtil.getUserNum());
            userName_tv.setText(UserInfoPreferenceUtil.getUserName());
            userSchool_tv.setText(UserInfoPreferenceUtil.getUserSchool());
            userCollege_tv.setText(UserInfoPreferenceUtil.getUserCollege());


        }
    };

}
