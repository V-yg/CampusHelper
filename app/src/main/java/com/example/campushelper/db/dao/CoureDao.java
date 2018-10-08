package com.example.campushelper.db.dao;

import com.example.campushelper.entity.Course;

import java.util.LinkedList;

/**
 * Created by 一钢 on 2018/10/3.
 */

public interface CoureDao {

    public void insertToLocal(LinkedList<Course> courses);  //从服务器获取数据插入到本地数据库
    public boolean delete(int cNum);                           //根据课程号删除课程
    public boolean deleteAll();                                 //删除所有课程
    public LinkedList<Course> queryAll();                 //查询所有课程---根据用户num
    public int getCount();                                     //获取数据库中有多少数据

}
