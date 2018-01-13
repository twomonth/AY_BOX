package com.aygames.twomonth.aybox.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aygames.twomonth.aybox.R;
import com.aygames.twomonth.aybox.utils.APPUtil;
import com.aygames.twomonth.aybox.utils.Constans;
import com.bumptech.glide.Glide;
import com.lzy.okhttputils.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;

import okhttp3.Response;


public class SplashActivity extends Activity {
    Thread thread = new Thread();
    Message mmessage = Message.obtain();
    private static final int CODE_UPDATE_DIALOG = 1;
    private static final int CODE_ENTER_HOME = 2;
    private static final int CODE_URL_ERROR = 3;
    private static final int CODE_NETWORK_ERROR = 4;
    private static final int CODE_JSON_ERROR = 5;
    private ImageView iv_adver;
    private TextView tv_version;
    private String message,des,versionname,url;
    private int versioncode;
    private Handler mhandler = new Handler(){
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case CODE_UPDATE_DIALOG:
                    showUpdateDialog();
                    break;
                case CODE_ENTER_HOME:
                    enterHome();
                    break;
                case CODE_NETWORK_ERROR:
                    Toast.makeText(getApplicationContext(), "网络异常",Toast.LENGTH_SHORT).show();
                    enterHome();
                    break;
                case CODE_JSON_ERROR:
                    Toast.makeText(getApplicationContext(), "数据解析异常",Toast.LENGTH_SHORT).show();
                    enterHome();
                    break;
                case CODE_URL_ERROR :
                    enterHome();
                    break;
                default:
                    enterHome();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        initView();
        checkVersion();
        tv_version.setText(APPUtil.getAppName(this)+" "+APPUtil.getVersionName(this));
    }

    /**
     * 初始化组件
     */
    private void initView() {
        iv_adver = findViewById(R.id.iv_adver);
        tv_version =  findViewById(R.id.tv_version);
        Glide.with(this).load("https://b-ssl.duitang.com/uploads/item/201507/09/20150709223911_8mTN2.jpeg").error(R.mipmap.logo).into(iv_adver);
    }
    /**
     * 检测版本更新
     */
    private void checkVersion() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                Message mmessage=Message.obtain();
                long startTime=System.currentTimeMillis();
                try {
                    Response response = OkHttpUtils.get(Constans.URL_UPDATE).execute();
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    message = jsonObject.getString("message");
                    JSONObject jsonObject2 = jsonObject.getJSONObject("data");
                    versioncode = jsonObject2.getInt("versioncode");
                    des = jsonObject2.getString("des");
                    versionname = jsonObject2.getString("versionname");
                    url = jsonObject2.getString("url");
                    if (versioncode > APPUtil.getVersionCode(getApplicationContext())){
                        mmessage.what = CODE_UPDATE_DIALOG;
                    }else{
                        mmessage.what = CODE_ENTER_HOME;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    mmessage.what = CODE_NETWORK_ERROR;
                } catch (JSONException e) {
                    e.printStackTrace();
                    mmessage.what = CODE_JSON_ERROR;
                } finally {
                    long endTime = System.currentTimeMillis();
                    long timeUsed = endTime - startTime;// 访问网络使用的时间
                    try {
                        if (timeUsed < 3000) {
                            thread.sleep(3000 - timeUsed);// 强制等待一段时间, 凑够两秒钟
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mhandler.sendMessage(mmessage);
                }
            }
        }.start();
    }
    /**
     * 下载最新版本app
     */
    public void downLoadApk(){


    }
    /**
     * 跳转到HomeActivity
     */
    public void enterHome(){
        Intent intent = new Intent(this,HomeActivity.class);
        startActivity(intent);
        this.finish();
    }
    /**
     * 提示更新对话框
     */
    public void showUpdateDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);// 这里必须传一个activity对象
        builder.setTitle("发现新版本:" + versionname);
        builder.setMessage(des);
        // builder.setCancelable(false);//不可取消,点返回键弹窗不消失, 尽量不要用,用户体验不好
        builder.setPositiveButton("立即更新",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        downLoadApk();
                        Log.v("tag","调用downloadApk方法");
                        enterHome();
                    }
                });
        builder.setNegativeButton("以后再说",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        enterHome();
                    }
                });
        // 用户取消弹窗的监听,比如点返回键
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                enterHome();
            }
        });
        builder.show();
    }
}