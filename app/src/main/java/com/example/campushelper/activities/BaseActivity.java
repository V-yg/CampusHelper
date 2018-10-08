package com.example.campushelper.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.campushelper.ActivityManager;
import com.example.campushelper.R;
import com.example.campushelper.utils.StatusBarUtil;
import com.example.campushelper.widge.CustomProgressDialog;

public abstract class BaseActivity extends AppCompatActivity {

    public Activity _this;
    private RelativeLayout topTooBar_root;
    private ImageView leftBack_iv;
    private TextView textView;
    private CustomProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base);

        StatusBarUtil.transparencyBar(BaseActivity.this);
        StatusBarUtil.StatusBarLightMode(BaseActivity.this);

        ActivityManager.getInstance().addActivity(this);

        //不传文本
        progressDialog = new CustomProgressDialog(this, R.style.progress_dialog_loading);
        //传递文本
        progressDialog = new CustomProgressDialog(this, R.style.progress_dialog_loading, "玩命加载中。。。");

        findTopView();

        _this = this;

    }

    private void findTopView() {
        topTooBar_root = bindView(R.id.topToolBarRoot);
        leftBack_iv = bindView(R.id.left_back_iv);
        textView = bindView(R.id.titleTextView);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        View view = getLayoutInflater().inflate(layoutResID, null);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        lp.addRule(RelativeLayout.BELOW, R.id.topToolBar);

        if (null != topTooBar_root)
            topTooBar_root.addView(view, lp);

        leftBack_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _this.finish();
            }
        });
    }

    public void setTitleText(String title) {
        if (textView != null)
            textView.setText(title);
    }

    public <T extends View> T bindView(int id) {

        return (T) findViewById(id);

    }

    protected void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * [页面跳转]
     */

    public void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }

    /**
     * [携带数据的页面跳转]
     */

    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * [携带数据需要返回数据的页面跳转]
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * [防止快速点击]
     */
    private boolean fastClick() {
        long lastClick = 0;
        if (System.currentTimeMillis() - lastClick <= 1000) {
            return false;
        }
        lastClick = System.currentTimeMillis();
        return true;
    }

    /**
     * 显示加载提示框
     */
    public void showLoadDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.show();
            }
        });
    }

    /**
     * 隐藏加载提示框
     */
    public void hideLoadDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        });
    }

}
