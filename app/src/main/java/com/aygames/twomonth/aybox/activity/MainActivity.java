package com.aygames.twomonth.aybox.activity;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import android.widget.ImageView;
import android.widget.TextView;

import com.aygames.twomonth.aybox.R;
import com.aygames.twomonth.aybox.fragment.BT_Fragment;
import com.aygames.twomonth.aybox.fragment.H5_Fragment;


public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout ;
    private TextView tv_h5,tv_bt;
    private FrameLayout fl_content_main;
    private ImageView iv_user,iv_share;
    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager = getSupportFragmentManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}
