package com.aygames.twomonth.aybox.activity;


import java.io.IOException;
import java.util.ArrayList;


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
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
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
import com.aygames.twomonth.aybox.utils.DialogUtil;
import com.aygames.twomonth.aybox.utils.Util;

public class LoginActivity extends Activity {

    private final static String TAG = "LoginActivity";

    private TextView tv_register ,tv_forget;
    private EditText et_login_telephone,et_login_password;
    private Button bt_login;
    public String agaent;
    private JSONObject jsonObject;
    private ArrayList<SmallCode> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();

        SharedPreferences sharedPreferences = getSharedPreferences("fy_passport", Context.MODE_PRIVATE);
        et_login_password.setText(sharedPreferences.getString("password", ""));
        et_login_telephone.setText(sharedPreferences.getString("telephone", ""));

        //跳转到注册界面
        tv_register.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                LoginActivity.this.finish();
            }
        });
        //跳转到修改密码界面
        tv_forget.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,ChangeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                LoginActivity.this.finish();

            }
        });
        //登录按钮
        bt_login.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                DialogUtil.showDialog(LoginActivity.this, "正在登录...");
                agaent = Util.getChannel(LoginActivity.this);
                jsonObject = new JSONObject();
                try {
                    jsonObject.put("telephone", et_login_telephone.getText().toString());
                    jsonObject.put("password", et_login_password.getText().toString());
                    jsonObject.put("gameid", "GID149094004624");
                    jsonObject.put("agaent", agaent.equals("CH1149060018297")?"":agaent);
                    jsonObject.put("userreg", "1");
                } catch (JSONException e) {
                    Log.e(TAG,"JSON 建立异常");
                    e.printStackTrace();
                }
                OkHttpClient mOkHttpClient = new OkHttpClient();
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), jsonObject.toString());
                Request request = new Request.Builder().url(Constans.URL_LOGIN).post(requestBody).build();
                mOkHttpClient.newCall(request).enqueue(new Callback() {

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String callBackString = response.body().string().toString();
                        Log.i(TAG,callBackString);
                        try {
                            jsonObject = new JSONObject(callBackString);
                            int code = jsonObject.getInt("code");
                            if (code == 0) {
                                //1.修改本地文件保存的账号密码信息
                                SharedPreferences sharedPreferences = getSharedPreferences("fy_passport", MODE_PRIVATE);
                                Editor editor = sharedPreferences.edit();
                                editor.putString("telephone", jsonObject.getString("telephone"));
                                editor.putString("password",jsonObject.getString("password"));
                                editor.putBoolean("isHave", true);
                                editor.commit();

                                AyboxService.passport = jsonObject.getString("uid");
                                AyboxService.telephone = jsonObject.getString("telephone");
                                AyboxService.password = jsonObject.getString("password");

                                //解析小号数据
                                String smallColde = jsonObject.getString("userinfo");
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
                                AyboxService.isLogin = true;
                                AyboxService.username = list.get(0).username;
                                LoginActivity.this.finish();
                            }else if (code == 1) {
                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        Toast.makeText(LoginActivity.this, "该手手机号尚未注册", Toast.LENGTH_SHORT).show();
                                        try {
                                            DialogUtil.dismissDialog();
                                        } catch (Exception e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        }
                                    }
                                });

                            }else if (code == 2) {
                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                                        try {
                                            DialogUtil.dismissDialog();
                                        } catch (Exception e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        }
                                    }
                                });
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
                                Toast.makeText(LoginActivity.this, "网络连接异常", Toast.LENGTH_SHORT).show();

                            }
                        });

                    }
                });

            }
        });
    }

    void initView(){
        tv_register = findViewById(R.id.tv_register);
        tv_forget = findViewById(R.id.tv_forget);
        et_login_telephone = findViewById(R.id.et_login_telephone);
        et_login_password = findViewById(R.id.et_login_password);
        bt_login = findViewById(R.id.bt_login);
    }

    /**
     * 返回键监听
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            return false;
//        }else {
//            finish();
//            return super.onKeyDown(keyCode, event);
//        }
        finish();
        return super.onKeyDown(keyCode, event);
    }

}

