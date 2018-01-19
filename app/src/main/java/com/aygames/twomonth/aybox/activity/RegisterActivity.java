package com.aygames.twomonth.aybox.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aygames.twomonth.aybox.R;
import com.aygames.twomonth.aybox.bean.SmallCode;
import com.aygames.twomonth.aybox.service.AyboxService;
import com.aygames.twomonth.aybox.utils.Constans;
import com.aygames.twomonth.aybox.utils.DeviceUtil;
import com.aygames.twomonth.aybox.utils.DialogUtil;
import com.aygames.twomonth.aybox.utils.Util;

public class RegisterActivity extends Activity{

    private final static String TAG = "RegisterActivity";

    private EditText et_register_telephone , et_register_captcha , et_register_password;
    private Button bt_get_captcha,bt_register;
    private TextView tv_tologin ,tv_timer;
    private String telephone,password,user_tel,os_vel,gameID,imei,agaent,brand,model;
    private SharedPreferences sharedPreferences;
    private JSONObject jsonObject;
    private ArrayList<SmallCode> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        tv_tologin.setText("<<返回到登录");
        tv_tologin.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        initData();
        //获取验证码
        bt_get_captcha.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Pattern pattern = Pattern.compile("1\\d{10}");
                Matcher matcher = pattern.matcher(et_register_telephone.getText().toString());
                if (!matcher.matches()) {
                    Toast.makeText(RegisterActivity.this, "手机号码错误", Toast.LENGTH_SHORT).show();
                }else {
                    //发送验证码
                    telephone = et_register_telephone.getText().toString();
                    et_register_telephone.setFocusable(false);
                    et_register_telephone.setFocusableInTouchMode(false);
                    new Thread(){
                        @Override
                        public void run() {
                            OkHttpClient mOkHttpClient = new OkHttpClient();
                            Request request = new Request.Builder().url(Constans.URL_GET_CAPTCHA
                                    +"/phone/"+telephone)
                                    .build();
                            Call call = mOkHttpClient.newCall(request);
                            try {
                                Response response = call.execute();
                               Log.i(TAG,response.toString());
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }.start();
                    bt_get_captcha.setVisibility(View.GONE);
                    tv_timer.setVisibility(View.VISIBLE);
                    MyCountDownTimer mc = new MyCountDownTimer(60000, 1000);
                    mc.start();
                }

            }
        });
        //注册按钮
        bt_register.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                telephone = et_register_telephone.getText().toString();
                password = et_register_password.getText().toString();

                Pattern pat = Pattern.compile("[\u4e00-\u9fa5]");
                //判断手机号标准
                Pattern p = Pattern.compile("1\\d{10}");
                Matcher m = p.matcher(telephone);

                if (!m.matches()) {
                    Toast.makeText(RegisterActivity.this, "手机号码错误", Toast.LENGTH_SHORT).show();
                }else if (password.length()<6 || password.length()>12) {
                    Toast.makeText(RegisterActivity.this, "密码只能由6至12位英文或数字组成",Toast.LENGTH_SHORT).show();
                }else if (pat.matcher(password).find()) {
                    Toast.makeText(RegisterActivity.this, "密码只能由6至12位英文或数字组成",Toast.LENGTH_SHORT).show();
                }else {
                    DialogUtil.showDialog(RegisterActivity.this, "正在为您注册...");
                    jsonObject = new JSONObject();
                    try {
                        jsonObject.put("telephone", telephone);
                        jsonObject.put("password", password);
                        jsonObject.put("user_tel", user_tel);
                        jsonObject.put("os_vel", DeviceUtil.getBuildVersion());
                        jsonObject.put("gameid", gameID);
                        jsonObject.put("device", 2+"");
                        jsonObject.put("imei", imei);
                        jsonObject.put("agaent", agaent);
                        jsonObject.put("brand", brand);
                        jsonObject.put("model", model);
                        jsonObject.put("phonecode",et_register_captcha.getText().toString());
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    Log.i(TAG,"注册发送数据	==="+jsonObject.toString());
                    OkHttpClient okHttpClient = new OkHttpClient();
                    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), jsonObject.toString());
                    Request request = new Request.Builder().url(Constans.URL_REGISTER).post(requestBody).build();
                    okHttpClient.newCall(request).enqueue(new Callback() {

                        @Override
                        public void onResponse(Call arg0, Response response) throws IOException {
                            String callbackString = response.body().string().toString();
                            try {
                                jsonObject = new JSONObject(callbackString);
                                Log.i(TAG,"注册返回数据"+jsonObject.toString());
                                switch (jsonObject.getInt("code")) {
                                    case 0:
                                        sharedPreferences =getSharedPreferences("fy_passport", MODE_WORLD_WRITEABLE);
                                        Editor editor = sharedPreferences.edit();
                                        editor.putString("telephone", jsonObject.getString("telephone"));
                                        editor.putString("password", jsonObject.getString("password"));
                                        editor.putBoolean("isHave", true);
                                        editor.commit();

                                        AyboxService.passport = jsonObject.getString("uid");
                                        AyboxService.telephone = jsonObject.getString("telephone");
                                        AyboxService.password = jsonObject.getString("password");
                                        //解析小号数据
                                        String smallColde = jsonObject.getString("userinfo");
                                        JSONArray jsonArray = new JSONArray(smallColde);
                                        Log.i(TAG,"smallcode==="+jsonArray.toString());
                                        list = new ArrayList<SmallCode>();
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            jsonObject = jsonArray.getJSONObject(i);
                                            SmallCode smallCode = new SmallCode(
                                                    jsonObject.getString("username"),
                                                    jsonObject.getString("nickname"),
                                                    jsonObject.getString("create_time"));
                                            list.add(smallCode);
                                        }
                                        AyboxService.username = list.get(0).username;
                                        AyboxService.isLogin = true;
                                        finish();
                                        break;
                                    case 1:
                                        runOnUiThread( new Runnable() {
                                            public void run() {
                                                Toast.makeText(RegisterActivity.this, "通行证已存在", Toast.LENGTH_SHORT).show();
                                                try {
                                                    DialogUtil.dismissDialog();
                                                } catch (Exception e) {
                                                    // TODO Auto-generated catch block
                                                    e.printStackTrace();
                                                }
                                            }
                                        });
                                        break;
                                    case 2:
                                        runOnUiThread( new Runnable() {
                                            public void run() {
                                                Toast.makeText(RegisterActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                                                try {
                                                    DialogUtil.dismissDialog();
                                                } catch (Exception e) {
                                                    // TODO Auto-generated catch block
                                                    e.printStackTrace();
                                                }
                                            }
                                        });
                                        break;
                                    default:
                                        runOnUiThread( new Runnable() {
                                            public void run() {
                                                Toast.makeText(RegisterActivity.this, "网络数据异常", Toast.LENGTH_SHORT).show();
                                                try {
                                                    DialogUtil.dismissDialog();
                                                } catch (Exception e) {
                                                    // TODO Auto-generated catch block
                                                    e.printStackTrace();
                                                }
                                            }
                                        });
                                        break;
                                }

                            } catch (JSONException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }


                        }

                        @Override
                        public void onFailure(Call arg0, IOException arg1) {
                            runOnUiThread( new Runnable() {
                                public void run() {
                                    Toast.makeText(RegisterActivity.this, "网络连接异常", Toast.LENGTH_SHORT).show();
                                    try {
                                        DialogUtil.dismissDialog();
                                    } catch (Exception e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                }
                            });

                        }
                    });
                }
            }
        });
        //返回到登录
        tv_tologin.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    void initView(){
        et_register_telephone = findViewById(R.id.et_register_telephone);
        et_register_password = findViewById(R.id.et_register_password);
        et_register_captcha = findViewById(R.id.et_register_captcha);
        bt_get_captcha = findViewById(R.id.bt_get_captcha);
        bt_register = findViewById(R.id.bt_register);
        tv_tologin = findViewById(R.id.tv_tologin);
        tv_timer = findViewById(R.id.tv_timer);

    }

    void initData(){
        imei = DeviceUtil.getDeviceId(RegisterActivity.this);//手机串号
        user_tel = DeviceUtil.getUser_tel(RegisterActivity.this);//读取设备手机号码
        os_vel = DeviceUtil.getBuildVersion();//读取系统版本
        brand = DeviceUtil.getPhoneBrand();
        model = DeviceUtil.getPhoneModel();
        gameID = "GID149094004624" ;
        agaent = Util.getChannel(RegisterActivity.this);
        Log.i(TAG,imei +"\n"+user_tel+"\n"+os_vel+"\n"+brand+"\n"+model+"\n"+agaent);

    }

    /**
     * 计时器
     * @author twomonth
     *
     */
    class MyCountDownTimer extends CountDownTimer {
        /**
         *
         * @param millisInFuture
         *      表示以毫秒为单位 倒计时的总数
         *
         *      例如 millisInFuture=1000 表示1秒
         *
         * @param countDownInterval
         *      表示 间隔 多少微秒 调用一次 onTick 方法
         *
         *      例如: countDownInterval =1000 ; 表示每1000毫秒调用一次onTick()
         *
         */
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            tv_timer.setVisibility(View.GONE);
            bt_get_captcha.setVisibility(View.VISIBLE);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            tv_timer.setText(millisUntilFinished/1000+"s");
        }
    }

    /**
     * 返回键监听
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }

    }


}

