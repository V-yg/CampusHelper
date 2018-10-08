package com.example.campushelper.entity;

/**
 * Created by 一钢 on 2018/10/3.
 */

public class Course {
    private int id;
    private String name;
    private String num;
    private String teacherName;
    private String place;
    private int weekBegin;
    private int weekEnd;
    private int weekType;
    private int lesson;
    private int day;
    private int placeBuilder;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getWeekBegin() {
        return weekBegin;
    }

    public void setWeekBegin(int weekBegin) {
        this.weekBegin = weekBegin;
    }

    public int getWeekEnd() {
        return weekEnd;
    }

    public void setWeekEnd(int weekEnd) {
        this.weekEnd = weekEnd;
    }

    public int getWeekType() {
        return weekType;
    }

    public void setWeekType(int weekType) {
        this.weekType = weekType;
    }

    public int getLesson() {
        return lesson;
    }

    public void setLesson(int lesson) {
        this.lesson = lesson;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getPlaceBuilder() {
        return placeBuilder;
    }

    public void setPlaceBuilder(int placeBuilder) {
        this.placeBuilder = placeBuilder;
    }
}
