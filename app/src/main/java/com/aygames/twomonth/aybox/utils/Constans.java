package com.aygames.twomonth.aybox.utils;

/**
 * Created by Administrator on 2017/11/27.
 */

public class Constans {

    public static final String URL = "http://sysdk.syyouxi.com/index.php/";
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
    //满V游戏接口地址
    public static final String URL_MANV = URL +"DataGames/getMVGames";
    //BT游戏接口地址
    public static final String URL_BTGAME = URL + "DataGames/getBTGames";
    //游戏详情页面
    public static final String URL_GAME = URL + "/DataGames/getGameinfo/gid/";
    //返利任务获取
    public static final String URL_FANLITASK = URL + "DataGames/getUserFanli/passport/";
    //返利订单获取
    public static final String URL_FANLIORDER = URL + "DataGames/getFanlilist/passport/";
    //游戏中心
    public static final String URL_CENTER_GAME = URL + "DataGames/getLabel";
    //返利订单发送
    public static final String URL_REBATEORDER = URL + "DataGames/getfanliData/";

}
