package com.aygames.twomonth.aybox.serviece;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.aygames.twomonth.aybox.utils.Constans;
import com.aygames.twomonth.aybox.utils.DeviceUtil;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.StringCallback;

import okhttp3.Call;
import okhttp3.Response;

public class LoginService extends Service {
    public static String userid;//用户唯一标识
    public static String telephone;//登录用户手机号
    public static String password;//用户密码
    public static String imei;//设备串号
    public static boolean isLogin;//识别用户是否已经登录
    public LoginService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        imei = DeviceUtil.getDeviceId(this);
        Log.i("手机串号",imei);
        if (imei!=null){
            //发送串号到后台查询用户账号密码，自动登录
            getUserInfoFromPHP(imei);
        }else {
            //查询本地数据库是否有账号密码保存,登录
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("000000","销毁");
    }
    /**
     * 发送手机串号匹配是否已经有注册账户
     */
    public void getUserInfoFromPHP(String imei){
        OkHttpUtils.get(Constans.URL_GET_USERINFO +"?iemi="+imei).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                Log.e("返回用户信息",response.body().toString());
            }
        });
    }
}
