package com.aygames.twomonth.aybox.utils;

/**
 * Created by Administrator on 2017/11/27.
 */

public class Constans {

    public static final String URL = "http://sdk.aooyou.com/index.php/";
    //注册
    public static final String URL_REGISTER = URL + "UserAPI/getusermobile";
    //登录
    public static final String URL_LOGIN = URL + "UserAPI/getuserlogin";
    //修改密码
    public static final String URL_CHANGE_PASSWORD = URL + "UserAPI/repass";
    //小号注册
    public static final String URL_SMALLCODE_REGISTER = URL + "UserAPI/getzcxh";
    //获取验证码
    public static final String URL_GET_CAPTCHA = URL + "/SDKconfigAPI/sendMobile";
    //退出接口
    public static final String URL_LOGOUT = URL + "SDKconfigAPI/xhloginout";
    //获取游戏版本号
    public static final String URL_GET_VERSION = URL + "SDKconfigAPI/gamebanben";
    //新闻获取接口
    public static final String URL_GET_NEW = URL + "SDKconfigAPI/gamenews";
    //支付宝签名
    public static final String URL_GET_SIGN_ALI = URL + "Pay/rsa_alipay";
    //支付宝回调笛子
    public static final String URL_ALIPAY_CALLBACK = URL + "Pay/WxPayCallback";
    //微信签名
    public static final String URL_GET_SIGN_WX = URL + "";
    //微信回调
    public static final String URL_WXPAY_CALLBACK = URL + "";

}
