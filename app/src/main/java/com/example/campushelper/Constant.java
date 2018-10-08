package com.example.campushelper;

/**
 * Created by 一钢 on 2018/9/24.
 */

public class Constant {

    public static final String URL_WEN_SERVER = "http://yigang.iask.in/A_Server";
//public static final String URL_WEN_SERVER = "http://192.168.1.102:8080/A_Server/";
    public static final String URL_LOGIN = URL_WEN_SERVER + "/LoginServlet";

    public static final String URL_USERINFO = URL_WEN_SERVER + "/UserInfoServlet";

    public static final String URL_USERCOURSE = URL_WEN_SERVER + "/UserCourseServlet";

    //验证用户是否登录的内部类
    public static class LoginMsg {
        public static boolean isLogin = false;
    }
}
