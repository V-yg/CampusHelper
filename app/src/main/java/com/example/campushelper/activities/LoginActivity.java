package com.example.campushelper.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.campushelper.Constant;
import com.example.campushelper.Preferences.UseroLoginPreferenceUtil;
import com.example.campushelper.R;
import com.example.campushelper.entity.User;
import com.example.campushelper.utils.GsonUtil;
import com.example.campushelper.utils.StatusBarUtil;
import com.example.campushelper.utils.http.HttpUtil;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.example.campushelper.R.id.password_edit;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText accountEdit, passwordEdit;
    private Button loginButton, forgetPasswordButton;
    private String account = null;
    private String password = null;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //设置标题栏透明
        StatusBarUtil.transparencyBar(this);

        initView();

        if(savedInstanceState != null){
            accountEdit.setText(savedInstanceState.getString("account"));
            passwordEdit.setText(savedInstanceState.getString("password"));
        }

    }

    @Override
    public void onBackPressed() {
        Intent MyIntent = new Intent(Intent.ACTION_MAIN);
        MyIntent.addCategory(Intent.CATEGORY_HOME);
        startActivity(MyIntent);
    }

    public static void start(Context context) {
        start(context, null);
    }

    public static void start(Context context, Intent extras) {
        Intent intent = new Intent();
        intent.setClass(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if (extras != null) {
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }

    protected void initView() {
        accountEdit = (EditText) findViewById(R.id.account_edit);
        passwordEdit = (EditText) findViewById(password_edit);
        loginButton = (Button) findViewById(R.id.login_button);
        forgetPasswordButton = (Button) findViewById(R.id.forgetPassword_button);

        accountEdit.setFilters(new InputFilter[]{new InputFilter.LengthFilter(16)});
        passwordEdit.setFilters(new InputFilter[]{new InputFilter.LengthFilter(16)});
        accountEdit.setText(UseroLoginPreferenceUtil.getUserAccount() == null ? "" : UseroLoginPreferenceUtil.getUserAccount());
        loginButton.setOnClickListener(this);
        forgetPasswordButton.setOnClickListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("account",accountEdit.getText().toString().trim());
        outState.putString("password",passwordEdit.getText().toString().toString());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:
                login();
                break;
            case R.id.forgetPassword_button:
                forgetPassword();
                break;
            default:
                break;
        }
    }

    private void forgetPassword() {

    }

    public void login() {

        account = accountEdit.getText().toString();
        password = passwordEdit.getText().toString();

        if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "请输入账号和密码", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = new User();
        user.setAccount(account);
        user.setPassword(password);
        String url = Constant.URL_LOGIN;
        String json = GsonUtil.postJson(user);

        HttpUtil.sendOkHttpRequestWithJson(url, json, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (e instanceof SocketTimeoutException) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this, "服务器连接超时", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                if (e instanceof ConnectException) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this, "服务器连接异常", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseData = response.body().string().trim();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (responseData.equals("ok")) {
                            loginSuccess();
                        }
                        if (responseData.equals("error")) {
                            Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }

    private void loginSuccess() {

        MainActivity.start(LoginActivity.this);

        UseroLoginPreferenceUtil.saveUserAccount(account);
        UseroLoginPreferenceUtil.saveUserPassword(password);

        finish();
    }

}
