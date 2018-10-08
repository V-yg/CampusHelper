package com.example.campushelper.utils;

import android.icu.text.SimpleDateFormat;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by 一钢 on 2018/10/4.
 */

public class DataUtil {

    //日期格式转换  String转换为 Data
    public static Date StringToData(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Date date = null;
        try {
            date = format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    //日期格式转换 Data 转换为 String
    public static String dataToString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        return format.format(date);
    }

    //根据开学日期获取现在是第几教学周
    static public int getWeeks(String termBegin) {
        try {
            Date currentTime = new Date();

            Date date = StringToData(termBegin);

            Calendar calendar = new GregorianCalendar();
            calendar.setFirstDayOfWeek(Calendar.MONDAY);  		//将星期一作为一个星期的开始

            calendar.setTime(date);
            int weeks2 = calendar.get(Calendar.WEEK_OF_YEAR);	// 开学星期数

            calendar.setTime(currentTime);
            int weeks1 = calendar.get(Calendar.WEEK_OF_YEAR);	// 当前星期数

            if (date.after(currentTime)) {
                return 0;
            }
            else {
                int n = (weeks1 - weeks2 >= 0) ? (weeks1 - weeks2 + 1 ) : (weeks1 - weeks2 + 53 );
                return n;
            }
        } catch (Exception e) {
            return 0;
        }
    }

    //根据开学日期和给定时间获取是第几教学周
    static public int getWeeksByDates(String termBegin, String currDate) {
        try {

            Date bDate = StringToData(termBegin);
            Date cDate = StringToData(currDate);

            Calendar calendar = new GregorianCalendar();
            calendar.setFirstDayOfWeek(Calendar.MONDAY);  		//将星期一作为一个星期的开始

            calendar.setTime(bDate);
            int weeks2 = calendar.get(Calendar.WEEK_OF_YEAR);	// 开学星期数

            calendar.setTime(cDate);
            int weeks1 = calendar.get(Calendar.WEEK_OF_YEAR);	// 当前日期对应星期数


            if (bDate.after(cDate)) {
                return 0;
            }
            else {
                int n = (weeks1 - weeks2 >= 0) ? (weeks1 - weeks2 + 1 ) : (weeks1 - weeks2 + 53 );
                return n;
            }
        } catch (Exception e) {
            return 0;
        }
    }
}
