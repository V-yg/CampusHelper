package com.example.campushelper.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by 一钢 on 2018/10/3.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "course.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TAG = "DBHelper";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS course" +		// 课程信息表
                "(c_id Integer primary key autoincrement," + //自增长id
                "c_num text, " +		// 课程ID
                "c_name text, " +    //课程名
                "c_week_begin integer, " + //开始周次
                "c_week_end integer, " +   //结束周次
                "c_week_type integer, " +   //周次类型  0：全上 、1：单、2：双
                "c_teacher_name text, " +   //教师名字
                "c_place_builder integer, " +//建筑类型 1：主教楼、2：综合楼、3：软件楼
                "c_place_class text, " +     //教室名字
                "c_day integer," +            //周几上课  如 周一
                "c_lesson integer )"          //第几节上课
        );
        Log.d(TAG,"----------------------------数据库create完成" );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
