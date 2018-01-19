package com.aygames.twomonth.aybox.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.aygames.twomonth.aybox.bean.SmallCode;
import com.aygames.twomonth.aybox.utils.Constans;
import com.aygames.twomonth.aybox.utils.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.lzy.okgo.utils.HttpUtils.runOnUiThread;

public class AyboxService extends Service {

    private final String TAG = "AYBOXSERVICE";

    private SharedPreferences sharedPreferences;
    private boolean isHave;
    private JSONObject jsonObject;
    public static String passport;//通行证
    public static String username;//正在登录的小号
    public static String telephone;//手机号
    public static String password;//密码
    public static String nickname;//小号昵称
    public static boolean isLogin;
    public static int fbkg;//浮标控制开关
    private static  ArrayList<SmallCode> list;
    public AyboxService() {
        Log.i(TAG,"服务初始化");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Log.i(TAG,"服务启动");

        sharedPreferences= getSharedPreferences("fy_passport", MODE_PRIVATE);
        //判断本地文件是否存在
        isHave = sharedPreferences.getBoolean("isHave", false);
        if (isHave) {
            //调用登录接口，发送本地文件中的登录信息
            telephone = sharedPreferences.getString("telephone", "");
            password = sharedPreferences.getString("password", "");
            startLogin();
        } else {
            isLogin = false;
            Log.i(TAG,"未找到本地文件。");
        }
        super.onStart(intent, startId);
    }

    @Override
    public ComponentName startService(Intent service) {


        return super.startService(service);
    }

    /**
     * 检测服务是否启动
     * @param ctx
     * @return
     */
    public static boolean isServiceRunning(Context ctx) {
        ActivityManager am = (ActivityManager) ctx
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runServices = am
                .getRunningServices(Integer.MAX_VALUE);
        if (null == runServices || 0 == runServices.size()) {
            return false;
        }
        for (int i = 0; i < runServices.size(); i++) {
            if (runServices.get(i).service.getClassName().equals(
                    AyboxService.class.getName())) {
                return true;
            }
        }
        return false;
    }


    /**
     * 执行登录
     */
    public void startLogin(){
        String agaent = Util.getChannel(getApplicationContext());
        jsonObject = new JSONObject();
        try {
            jsonObject.put("telephone", telephone);
            jsonObject.put("password", password);
            jsonObject.put("gameid", "GID149094004624");
            jsonObject.put("agaent", agaent.equals("CH1149060018297")?"":agaent);
            jsonObject.put("userreg", "1");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), jsonObject.toString());
        Request request = new Request.Builder().url(Constans.URL_LOGIN).post(requestBody).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String callBackString = response.body().string();
                Log.i(TAG,callBackString);
                try {
                    JSONObject jsonObject2 = new JSONObject(callBackString);
                    int code = jsonObject2.getInt("code");
                    if (code == 0) {
                        //1.保存通行证的手机号密码到本地，方便下次自动登录
                        sharedPreferences = getSharedPreferences("fy_passport", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("telephone", jsonObject2.getString("telephone"));
                        editor.putString("password",jsonObject2.getString("password"));
                        editor.putBoolean("isHave", true);
                        editor.commit();
                        //2.保存通行证到sdkservice
                        AyboxService.passport = jsonObject2.getString("uid");
                        AyboxService.telephone = jsonObject2.getString("telephone");
                        AyboxService.password = jsonObject2.getString("password");
                        //3.解析小号数据
                        String smallColde = jsonObject2.getString("userinfo");
                        JSONArray jsonArray = new JSONArray(smallColde);
                        Log.i(TAG,jsonArray.toString());
                        list = new ArrayList<SmallCode>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            jsonObject = jsonArray.getJSONObject(i);
                            SmallCode smallCode = new SmallCode(
                                    jsonObject.getString("username"),
                                    jsonObject.getString("nickname"),
                                    jsonObject.getString("create_time"));
                            list.add(smallCode);
                        }
                        username = list.get(0).username;
                        isLogin = true;
                    }else if (code == 1) {

                        Toast.makeText(getApplicationContext(), "账号错误,APP登录失败", Toast.LENGTH_SHORT).show();
                    }else if (code == 2) {
                        Toast.makeText(getApplicationContext(), "密码错误,APP登录失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
            @Override
            public void onFailure(Call arg0, IOException arg1) {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }
}