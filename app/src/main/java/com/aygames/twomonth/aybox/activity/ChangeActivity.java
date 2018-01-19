package com.aygames.twomonth.aybox.activity;



        import java.io.IOException;
        import java.util.regex.Matcher;
        import java.util.regex.Pattern;

        import okhttp3.Call;
        import okhttp3.Callback;
        import okhttp3.MediaType;
        import okhttp3.OkHttpClient;
        import okhttp3.Request;
        import okhttp3.RequestBody;
        import okhttp3.Response;

        import org.json.JSONException;
        import org.json.JSONObject;


        import android.app.Activity;
        import android.content.Intent;


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
        import com.aygames.twomonth.aybox.utils.Constans;
        import com.aygames.twomonth.aybox.utils.DialogUtil;

public class ChangeActivity extends Activity{

    private final static String TAG = "ChangeActivity";

    private EditText et_change_telephone , et_change_captcha , et_change_password;
    private Button bt_get_captcha_change,bt_change;
    private TextView tv_tologin_change ,tv_timer_change;
    private String telephone,newPassword;
    private JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
        initView();
        //获取验证码
        bt_get_captcha_change.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Pattern pattern = Pattern.compile("1\\d{10}");
                Matcher matcher = pattern.matcher(et_change_telephone.getText().toString());
                if (!matcher.matches()) {
                    Toast.makeText(ChangeActivity.this, "手机号码错误", Toast.LENGTH_SHORT).show();
                }else {
                    //发送验证码
                    telephone = et_change_telephone.getText().toString();
                    et_change_telephone.setFocusable(false);
                    et_change_telephone.setFocusableInTouchMode(false);
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
                    bt_get_captcha_change.setVisibility(View.GONE);
                    tv_timer_change.setVisibility(View.VISIBLE);
                    MyCountDownTimer mc = new MyCountDownTimer(60000, 1000);
                    mc.start();
                }

            }
        });
        //确认修改
        bt_change.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                telephone = et_change_telephone.getText().toString();
                Pattern pat = Pattern.compile("[\u4e00-\u9fa5]");
                newPassword = et_change_password.getText().toString();
                if (!et_change_telephone.getText().toString().equals(telephone)) {
                    Toast.makeText(ChangeActivity.this, "手机号码错误", Toast.LENGTH_SHORT).show();
                }else if (newPassword.length()<6 || newPassword.length()>12) {
                    Toast.makeText(ChangeActivity.this, "密码只能由6至12位英文或数字组成",Toast.LENGTH_SHORT).show();
                }else if (pat.matcher(newPassword).find()) {
                    Toast.makeText(ChangeActivity.this, "密码只能由6至12位英文或数字组成",Toast.LENGTH_SHORT).show();
                }else {
                    DialogUtil.showDialog(ChangeActivity.this, "正在为您修改密码...");
                    jsonObject = new JSONObject();
                    try {
                        jsonObject.put("telephone", telephone);
                        jsonObject.put("phonecode", et_change_captcha.getText().toString());
                        jsonObject.put("password", newPassword);
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    Log.i(TAG,"修改密码提交数据"+jsonObject.toString());
                    OkHttpClient okHttpClient = new OkHttpClient();
                    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), jsonObject.toString());
                    Request request = new Request.Builder().url(Constans.URL_CHANGE_PASSWORD).post(requestBody).build();
                    okHttpClient.newCall(request).enqueue(new Callback() {

                        @Override
                        public void onResponse(Call arg0, Response response) throws IOException {
//							Logger.msg("修改密码返回数据"+response.toString());
                            String callbackString = response.body().string().toString();
                            try {
                                jsonObject = new JSONObject(callbackString);
                                Log.i(TAG,"修改密码返回数据"+jsonObject.toString());
                                switch (jsonObject.getInt("code")) {
                                    case 0:

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(ChangeActivity.this, "修改成功，请重新填写密码", Toast.LENGTH_SHORT).show();
                                                try {
                                                    DialogUtil.dismissDialog();
                                                    Intent intent = new Intent(ChangeActivity.this,LoginActivity.class);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    startActivity(intent);
                                                    ChangeActivity.this.finish();
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        });
                                        break;
                                    case 1:
                                        runOnUiThread( new Runnable() {
                                            public void run() {
                                                Toast.makeText(ChangeActivity.this, "该手机号尚未注册", Toast.LENGTH_SHORT).show();
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
                                                Toast.makeText(ChangeActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                                                try {
                                                    DialogUtil.dismissDialog();
                                                } catch (Exception e) {
                                                    // TODO Auto-generated catch block
                                                    e.printStackTrace();
                                                }
                                            }
                                        });
                                        break;
                                    case 3:
                                        runOnUiThread( new Runnable() {
                                            public void run() {
                                                Toast.makeText(ChangeActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                                                try {
                                                    DialogUtil.dismissDialog();
                                                } catch (Exception e) {
                                                    // TODO Auto-generated catch block
                                                    e.printStackTrace();
                                                }
                                            }
                                        });
                                    case 4:
                                        runOnUiThread( new Runnable() {
                                            public void run() {
                                                Toast.makeText(ChangeActivity.this, "与原密码相同无法修改", Toast.LENGTH_SHORT).show();
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
                                                Toast.makeText(ChangeActivity.this, "网络数据异常", Toast.LENGTH_SHORT).show();
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
                                runOnUiThread( new Runnable() {
                                    public void run() {
                                        Toast.makeText(ChangeActivity.this, "数据解析异常", Toast.LENGTH_SHORT).show();
                                        try {
                                            DialogUtil.dismissDialog();
                                        } catch (Exception e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        }
                                    }
                                });
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
                                    Toast.makeText(ChangeActivity.this, "网络数据异常", Toast.LENGTH_SHORT).show();
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
        tv_tologin_change.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChangeActivity.this,LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

    }

    void initView(){
        et_change_telephone = findViewById(R.id.et_change_telephone);
        et_change_password = findViewById(R.id.et_change_password);
        et_change_captcha = findViewById(R.id.et_change_captcha);
        bt_get_captcha_change = findViewById(R.id.bt_get_captcha_change);
        bt_change = findViewById(R.id.bt_change);
        tv_tologin_change = findViewById(R.id.tv_tologin_change);
        tv_timer_change = findViewById(R.id.tv_timer_change);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Intent intent = new Intent(ChangeActivity.this,LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
        return super.onKeyDown(keyCode, event);
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
            tv_timer_change.setVisibility(View.GONE);
            bt_get_captcha_change.setVisibility(View.VISIBLE);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            tv_timer_change.setText(millisUntilFinished/1000+"s");
        }
    }


}

