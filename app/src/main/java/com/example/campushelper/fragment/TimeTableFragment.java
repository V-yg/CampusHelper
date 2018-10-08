package com.example.campushelper.fragment;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.campushelper.Constant;
import com.example.campushelper.MyApplication;
import com.example.campushelper.Preferences.GetCourseFromServerUtil;
import com.example.campushelper.Preferences.UseroLoginPreferenceUtil;
import com.example.campushelper.R;
import com.example.campushelper.db.dao.impl.CourseDaoImpl;
import com.example.campushelper.entity.Course;
import com.example.campushelper.utils.DataUtil;
import com.example.campushelper.utils.GsonUtil;
import com.example.campushelper.utils.http.HttpUtil;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Date;
import java.util.LinkedList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by 一钢 on 2018/10/3.
 */

public class TimeTableFragment extends BaseFragment implements View.OnClickListener {

    private TextView[][] course_tv = new TextView[5][6];
    private TextView currentWeek_tv, currentTime_tv, teachWeek_tv;
    private LinearLayout weekSelect_ll;
    private Button bt;
    private int week_;

    private Handler handler = null;

    private ListPopupWindow popupWindow;

    private String[] popWindowItems = {"第一周","第二周","第三周","第四周","第五周","第六周","第七周","第八周"
            ,"第九周","第十周","第十一周","第十二周","第十三周","第十四周","第十五周","第十六周"};
    private static final String TAG = "TimeTableFragment";

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_timetable;
    }

    @Override
    protected void initView() {

        currentTime_tv = findViewById(R.id.current_time_tv);
        currentWeek_tv = findViewById(R.id.current_week_tv);
        teachWeek_tv = findViewById(R.id.teach_week_tv);
        weekSelect_ll = findViewById(R.id.week_select_ll);
        bt = findViewById(R.id.other_bt);

        weekSelect_ll.setOnClickListener(this);
        bt.setOnClickListener(this);

        initCourseView();

    }

    private void initCourseView() {
        course_tv[1][1] = findViewById(R.id.course_tv_11);
        course_tv[1][2] = findViewById(R.id.course_tv_12);
        course_tv[1][3] = findViewById(R.id.course_tv_13);
        course_tv[1][4] = findViewById(R.id.course_tv_14);
        course_tv[1][5] = findViewById(R.id.course_tv_15);
        course_tv[2][1] = findViewById(R.id.course_tv_21);
        course_tv[2][2] = findViewById(R.id.course_tv_22);
        course_tv[2][3] = findViewById(R.id.course_tv_23);
        course_tv[2][4] = findViewById(R.id.course_tv_24);
        course_tv[2][5] = findViewById(R.id.course_tv_25);
        course_tv[3][1] = findViewById(R.id.course_tv_31);
        course_tv[3][2] = findViewById(R.id.course_tv_32);
        course_tv[3][3] = findViewById(R.id.course_tv_33);
        course_tv[3][4] = findViewById(R.id.course_tv_34);
        course_tv[3][5] = findViewById(R.id.course_tv_35);
        course_tv[4][1] = findViewById(R.id.course_tv_41);
        course_tv[4][2] = findViewById(R.id.course_tv_42);
        course_tv[4][3] = findViewById(R.id.course_tv_43);
        course_tv[4][4] = findViewById(R.id.course_tv_44);
        course_tv[4][5] = findViewById(R.id.course_tv_45);
    }

    @Override
    protected void initData(Bundle arguments) {

        handler = new Handler();

        Date date = new Date(System.currentTimeMillis());
        week_ = DataUtil.getWeeks("2018-9-6");
        currentTime_tv.setText(DataUtil.dataToString(date));
        teachWeek_tv.setText("第 " + String.valueOf(week_) + " 教学周");
        currentWeek_tv.setText("第" + String.valueOf(week_) + "周");

        if(!GetCourseFromServerUtil.getFromLocal()){
            getCourseFromServer();
        }else {
            getCourseFromLocal(0);
        }

        Log.d(TAG, "--------------------------------course表中共有 " + new CourseDaoImpl(MyApplication.getContext()).getCount() + " 条数据");
    }

    private void getCourseFromServer() {

        String url = Constant.URL_USERCOURSE;

        HttpUtil.sendOkHttpRequestWithString(url, UseroLoginPreferenceUtil.getUserAccount(), new Callback() {
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
                if (response.isSuccessful()) {
                    String responseData = response.body().string().trim();
                    Type type = new TypeToken<LinkedList<Course>>() {}.getType();
                    LinkedList<Course> courses = GsonUtil.getJson(responseData, type);

                    CourseDaoImpl courseDao = new CourseDaoImpl(MyApplication.getContext());
                    courseDao.insertToLocal(courses);

                    new Thread() {
                        public void run(){
                            handler.post(runnableUi);
                        }
                    }.start();

                    Log.d(TAG,"---------------------------数据库中有 " + courseDao.getCount());
                    if(courseDao.getCount() > 2){
                        GetCourseFromServerUtil.setFromLocal(true);
                    }

                }
            }
        });
    }

    Runnable runnableUi = new Runnable() {
        @Override
        public void run() {
            getCourseFromLocal(0);
        }
    };

    public void getCourseFromLocal(int week){
        CourseDaoImpl courseDao = new CourseDaoImpl(MyApplication.getContext());
        LinkedList<Course> courses = courseDao.queryAll();
        showCourse(courses,week);
    }

    public void showCourse(LinkedList<Course> mCourse,int week) {

        if(week == 0){
            week = week_;
        }

        for (Course c : mCourse) {
            for (int i = 1; i <= 4; i++) {
                for (int j = 1; j <= 5; j++) {
                    if (c.getDay() == j && c.getLesson() == i && week >= c.getWeekBegin() && week <= c.getWeekEnd()) {

                        String type = "全周";
                        if (c.getWeekType() == 1) {
                            type = "单周";
                        }
                        if (c.getWeekType() == 2) {
                            type = "双周";
                        }

                        String currType = "全周";
                        if (week % 2 == 0) {
                            currType = "双周";
                        }
                        if (week % 2 == 1) {
                            currType = "单周";
                        }
                        course_tv[i][j].setText(c.getName() + "-" + c.getPlace() + "-" + type);
                        if (!type.equals(currType)) {
                            course_tv[i][j].setText("");
                        }
                        if (type.equals("全周")) {
                            course_tv[i][j].setText(c.getName() + "-" + c.getPlace() + "-" + type);
                        }
                    }
                }
            }
        }
    }

    /**
     * 显示周数下拉列表悬浮窗
     */
    private void showWeekListWindow(Context context, View anchorView) {
        popupWindow = new ListPopupWindow(context);
        popupWindow.setAdapter(new ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, popWindowItems));
        popupWindow.setAnchorView(anchorView);//设置参照控件
        popupWindow.setWidth(250);
        popupWindow.setHeight(600);
        popupWindow.setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.transparent1)));
        popupWindow.setModal(true);//模态框，设置为true响应物理键
        popupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getCourseFromLocal(position + 1);
                currentWeek_tv.setText("第" + (position + 1) + "周");
                popupWindow.dismiss();
            }
        });
        popupWindow.show();
    }


    @Override
    public void onClick(View v) {
        showWeekListWindow(MyApplication.getContext(), v);
    }

}
