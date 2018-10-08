package com.example.campushelper.widge;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.example.campushelper.R;

/**
 * Created by 一钢 on 2018/9/29.
 */

public class CustomProgressDialog extends ProgressDialog {
    private String message = "";

    public CustomProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    public CustomProgressDialog(Context context, int theme,
                                String message) {
        super(context, theme);
        this.message = message;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progressdialog_loading_layout);
        //dialog弹出后点击物理返回键Dialog消失，但是点击屏幕不会消失
        this.setCanceledOnTouchOutside(false);

        //dialog弹出后点击屏幕或物理返回键，dialog都不消失
        //this.setCancelable(false);
        if (message != null) {//message不为空，则设置
            ((TextView) findViewById(R.id.progress_text)).setText(message);
        }
    }
}
