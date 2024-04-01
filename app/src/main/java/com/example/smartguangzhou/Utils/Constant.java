package com.example.smartguangzhou.Utils;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;

public class Constant {

    public static boolean ISLOGIN = false;

    public static boolean AUTOLOGIN = false;


    public static String TOKEN = "";
    public static String IP = "http://124.93.196.45:10001";

    public static String METROE_INFO_DATA = "/prod-api/api/metro/list"; //地铁查询(需加params)

    public static String METROLINE = "/prod-api/api/metro/line/";//地铁线路(需加params)
    public static String HOMEBANNER = "/prod-api/api/rotation/list";//首页轮播图

    public static String GET_INFO_URL = "/prod-api/api/common/user/getInfo";//个人信息

    public static String RESET_PWD = "/prod-api/api/common/user/resetPwd";//修改密码 (put)

    public static String LOGIN = "/prod-api/api/login";//登录 (post)

    public static String REGISTER = "/prod-api/api/register";//注册(post)

    public static String NEWS_URL = "/prod-api/press/press/list"; //新闻列表

    public static String UPDATE_USER_URL = "/prod-api/api/common/user"; //更新用户信息(post)
    public static String ALL_SERVICE = "/prod-api/api/service/list";//全部服务

    public static String ADD_FEEDBACK_URL = "/prod-api/api/common/feedback"; //意见反馈

    public static String CATEGORY_URL = "/prod-api/press/category/list"; //下新闻列表

    public static String NEWS_DETAIL =  "/prod-api/press/press/";//新闻详情

    public static void setOffLine(){
        ISLOGIN = false;
        TOKEN = "";
    }
}
