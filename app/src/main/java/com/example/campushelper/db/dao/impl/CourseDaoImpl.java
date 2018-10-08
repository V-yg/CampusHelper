package com.example.campushelper.db.dao.impl;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.campushelper.db.DBHelper;
import com.example.campushelper.db.dao.CoureDao;
import com.example.campushelper.entity.Course;

import java.util.LinkedList;

/**
 * Created by 一钢 on 2018/10/3.
 */

public class CourseDaoImpl implements CoureDao {

    private static final String TAG = "CourseDaoImpl";
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public CourseDaoImpl(Context context) {
        dbHelper = new DBHelper(context);
    }

    @Override
    public int getCount() {
        db = dbHelper.getWritableDatabase();
        if (db.rawQuery("select * from course", null).getCount() == 0) {
            return 0;
        }
        return db.rawQuery("select * from course", null).getCount();

    }

    @Override
    public void insertToLocal(LinkedList<Course> courses) {

        deleteAll();

        db = dbHelper.getWritableDatabase();
        String sql = "insert into course(c_num,c_name,c_week_begin,c_week_end,c_week_type," +
                "c_teacher_name,c_place_builder,c_place_class,c_day,c_lesson) " +
                "values(?,?,?,?,?,?,?,?,?,?)";

        Log.d(TAG, "--------------------------------进入入 ");
        for (Course course : courses) {
            db.execSQL(sql, new Object[]{
                    course.getNum(),
                    course.getName(),
                    course.getWeekBegin(),
                    course.getWeekEnd(),
                    course.getWeekType(),
                    course.getTeacherName(),
                    course.getPlaceBuilder(),
                    course.getPlace(),
                    course.getDay(),
                    course.getLesson()
            });
        }
        db.close();
        Log.d(TAG, "--------------------------------本次往course表中插入 " + getCount() + " 条数据");
    }

    @Override
    public boolean delete(int cNum) {
        db = dbHelper.getWritableDatabase();
        try {
            String sql = "DELETE FROM course WHERE c_num=?";
            db.execSQL(sql, new Object[]{cNum});
            db.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean deleteAll() {
        db = dbHelper.getWritableDatabase();

        String sql = "DELETE FROM course ";
        db.execSQL(sql);
        db.close();
        return true;

    }

    // 查询某用户的所有课程
    @Override
    public LinkedList<Course> queryAll() {
        db = dbHelper.getWritableDatabase();
        try {
            LinkedList<Course> courses = new LinkedList<Course>();

//            String sql = "SELECT * FROM course WHERE cid IN (SELECT cid FROM user_course WHERE uid=?)";
            String sql = "select * from course";
//            Cursor c = db.rawQuery(sql, new String[]{String.valueOf(uid)});
            Cursor c = db.rawQuery(sql,null);
            while (c.moveToNext()) {

                Course course = new Course();

                course.setId(c.getInt(c.getColumnIndex("c_id")));
                course.setNum(c.getString(c.getColumnIndex("c_num")));
                course.setName(c.getString(c.getColumnIndex("c_name")));
                course.setWeekBegin(c.getInt(c.getColumnIndex("c_week_begin")));
                course.setWeekEnd(c.getInt(c.getColumnIndex("c_week_end")));
                course.setWeekType(c.getInt(c.getColumnIndex("c_week_type")));
                course.setTeacherName(c.getString(c.getColumnIndex("c_teacher_name")));
                course.setPlaceBuilder(c.getInt(c.getColumnIndex("c_place_builder")));
                course.setPlace(c.getString(c.getColumnIndex("c_place_class")));
                course.setDay(c.getInt(c.getColumnIndex("c_day")));
                course.setLesson(c.getInt(c.getColumnIndex("c_lesson")));

                courses.add(course);
            }
            db.close();
            return courses;
        } catch (Exception e) {
            return null;
        }

    }
}
