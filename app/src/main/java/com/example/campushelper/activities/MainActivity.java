package com.example.campushelper.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.example.campushelper.ActivityManager;
import com.example.campushelper.R;
import com.example.campushelper.fragment.HomeFragment;
import com.example.campushelper.fragment.MapFragment;
import com.example.campushelper.fragment.TimeTableFragment;
import com.example.campushelper.fragment.UserFragment;
import com.example.campushelper.utils.StatusBarUtil;
import com.example.campushelper.utils.helper.BottomNavigationViewHelper;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView navigation;
    private UserFragment userFragment;
    private MapFragment mapFragment;
    private HomeFragment homeFragment;
    private TimeTableFragment timeTableFragment;
    private long exitTime= 0;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityManager.getInstance().addActivity(this);

        StatusBarUtil.transparencyBar(this);
        StatusBarUtil.StatusBarLightMode(this);

        initView();
        initNavigation();
    }

    public static void start(Context context) {
        start(context, null);
    }

    public static void start(Context context, Intent extras) {
        Intent intent = new Intent();
        intent.setClass(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if (extras != null) {
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }

    // 注销
    public void logout() {
        MainActivity.this.finish();
    }

    protected void initView() {
        navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(this);
    }

    private void initNavigation() {
        userFragment = new UserFragment();
        mapFragment = new MapFragment();
        homeFragment = new HomeFragment();
        timeTableFragment = new TimeTableFragment();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"----------------------------主界面活动退出");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"----------------------------主界面活动暂停");
    }

    //    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
//            if ((System.currentTimeMillis() - exitTime) > 2000) {
//                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
//                exitTime = System.currentTimeMillis();
//            } else {
//                finish();
//                System.exit(0);
//            }
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    protected void initData() {

    }

    @Override
    public void onBackPressed() {
        Intent MyIntent = new Intent(Intent.ACTION_MAIN);
        MyIntent.addCategory(Intent.CATEGORY_HOME);
        startActivity(MyIntent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, homeFragment).commit();
                break;
            case R.id.navigation_map:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, mapFragment).commit();
                break;
            case R.id.navigation_user:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, userFragment).commit();
                break;
            case R.id.navigation_timetable:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, timeTableFragment).commit();
                break;
            default:
                break;
        }
        return true;
    }
}
