package com.aygames.twomonth.aybox.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;

import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.aygames.twomonth.aybox.R;
import com.aygames.twomonth.aybox.fragment.BT_Fragment;
import com.aygames.twomonth.aybox.fragment.H5_Fragment;
import com.aygames.twomonth.aybox.service.AyboxService;


public class MainActivity extends AppCompatActivity {


    //手指按下的点为(x1, y1)手指离开屏幕的点为(x2, y2)
//    float x1 = 0;
//    float x2 = 0;
//    float y1 = 0;
//    float y2 = 0;

    private RelativeLayout drawerLayout ;
    private TextView tv_h5,tv_bt;
    private FrameLayout fl_content_main;
    private ImageView iv_user,iv_share;
    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager = getSupportFragmentManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //启动登录服务
        final Intent intent = new Intent(this,AyboxService.class);
        startService(intent);

        initView();
        initData();

        tv_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initTab();
                GradientDrawable gd = new GradientDrawable();//创建drawable
                gd.setColor(Color.parseColor("#058aff"));
                gd.setCornerRadius(50);
                gd.setStroke(5, Color.parseColor("#bebebe"));//边框颜色
                tv_bt.setBackgroundDrawable(gd);
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fl_content_main , new BT_Fragment());
                fragmentTransaction.commit();
            }
        });
        tv_h5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initTab();
                GradientDrawable gd = new GradientDrawable();//创建drawable
                gd.setColor(Color.parseColor("#058aff"));
                gd.setCornerRadius(50);
                gd.setStroke(5, Color.parseColor("#bebebe"));//边框颜色
                tv_h5.setBackgroundDrawable(gd);
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fl_content_main , new H5_Fragment());
                fragmentTransaction.commit();
            }
        });
        iv_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AyboxService.isLogin == true){
                    Intent intent = new Intent(MainActivity.this,UserActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent1 = new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(intent1);
                }
            }
        });

        iv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this,DownloadingActivity.class);
                startActivity(intent1);
            }
        });

    }

    private void initData() {
        initTab();
        initTab();
        GradientDrawable gd = new GradientDrawable();//创建drawable
        gd.setColor(Color.parseColor("#058aff"));
        gd.setCornerRadius(50);
        gd.setStroke(5, Color.parseColor("#bebebe"));//边框颜色
        tv_bt.setBackgroundDrawable(gd);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_content_main , new BT_Fragment());
        fragmentTransaction.commit();

    }

    private void initView() {
        drawerLayout =  findViewById(R.id.dl_main);
        tv_h5 = (TextView) findViewById(R.id.tv_h5);
        tv_bt = (TextView) findViewById(R.id.tv_bt);
        iv_share = (ImageView) findViewById(R.id.iv_share);
        iv_user = (ImageView) findViewById(R.id.iv_user);
        fl_content_main = (FrameLayout) findViewById(R.id.fl_content_main);

    }

    void initTab(){
        GradientDrawable gd = new GradientDrawable();//创建drawable
        gd.setColor(Color.parseColor("#3F51B5"));
        gd.setCornerRadius(50);
        gd.setStroke(5, Color.parseColor("#058aff"));
        tv_bt.setBackgroundDrawable(gd);
        tv_h5.setBackgroundDrawable(gd);
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        //继承了Activity的onTouchEvent方法，直接监听点击事件
//        if(event.getAction() == MotionEvent.ACTION_DOWN) {
//            //当手指按下的时候
//            x1 = event.getX();
//            y1 = event.getY();
//        }
//        if(event.getAction() == MotionEvent.ACTION_UP) {
//            //当手指离开的时候
//            x2 = event.getX();
//            y2 = event.getY();
//            if(y1 - y2 > 50) {
//                Toast.makeText(MainActivity.this, "向上滑", Toast.LENGTH_SHORT).show();
//            } else if(y2 - y1 > 50) {
//                Toast.makeText(MainActivity.this, "向下滑", Toast.LENGTH_SHORT).show();
//            } else if(x1 - x2 > 50) {
//                Toast.makeText(MainActivity.this, "向左滑", Toast.LENGTH_SHORT).show();
//            } else if(x2 - x1 > 50) {
//                Toast.makeText(MainActivity.this, "向右滑", Toast.LENGTH_SHORT).show();
//            }
//        }
//        return super.onTouchEvent(event);
//    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
        return super.onKeyDown(keyCode, event);
    }
}
