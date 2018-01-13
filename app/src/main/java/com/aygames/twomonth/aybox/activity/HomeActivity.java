package com.aygames.twomonth.aybox.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.aygames.twomonth.aybox.R;
import com.aygames.twomonth.aybox.fragment.AllFragment;
import com.aygames.twomonth.aybox.fragment.ClassifyFragment;
import com.aygames.twomonth.aybox.fragment.HotFragment;
import com.aygames.twomonth.aybox.fragment.MainFragment;
import com.aygames.twomonth.aybox.fragment.UserFragment;
import com.aygames.twomonth.aybox.serviece.LoginService;

/**
 * Created by Administrator on 2017/12/4.
 */

public class HomeActivity extends AppCompatActivity {

    private LinearLayout ll_all,ll_hot,ll_main,ll_user,ll_classify;
    private FrameLayout fl_home_content;
    private ImageView iv_down_all,iv_down_hot,iv_down_main,iv_down_classify,iv_down_user;
    private TextView  tv_down_all,tv_down_hot,tv_down_main,tv_down_classify,tv_down_user;
    private FragmentTransaction ft;
    FragmentManager fragmentManager = getSupportFragmentManager();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        
        ft = fragmentManager.beginTransaction();
        ft.replace(R.id.fl_home_content,new MainFragment());
        ft.commit();
        iv_down_all.setBackgroundResource(R.mipmap.ic_down_all);
        iv_down_hot.setBackgroundResource(R.mipmap.ic_down_hot);
        iv_down_main.setBackgroundResource(R.mipmap.ic_down_main_black);
        iv_down_classify.setBackgroundResource(R.mipmap.ic_down_classify);
        iv_down_user.setBackgroundResource(R.mipmap.ic_down_user);
        tv_down_all.setTextColor(Color.GRAY);
        tv_down_hot.setTextColor(Color.GRAY);
        tv_down_main.setTextColor(Color.BLACK);
        tv_down_classify.setTextColor(Color.GRAY);
        tv_down_user.setTextColor(Color.GRAY);

        Intent intent = new Intent(this, LoginService.class);
        startService(intent);
        ll_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ft = fragmentManager.beginTransaction();
                ft.replace(R.id.fl_home_content,new AllFragment());
                ft.commit();
                iv_down_all.setBackgroundResource(R.mipmap.ic_down_all_black);
                iv_down_hot.setBackgroundResource(R.mipmap.ic_down_hot);
                iv_down_main.setBackgroundResource(R.mipmap.ic_down_main);
                iv_down_classify.setBackgroundResource(R.mipmap.ic_down_classify);
                iv_down_user.setBackgroundResource(R.mipmap.ic_down_user);
                tv_down_all.setTextColor(Color.BLACK);
                tv_down_hot.setTextColor(Color.GRAY);
                tv_down_main.setTextColor(Color.GRAY);
                tv_down_classify.setTextColor(Color.GRAY);
                tv_down_user.setTextColor(Color.GRAY);
            }
        });
        ll_hot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ft = fragmentManager.beginTransaction();
                ft.replace(R.id.fl_home_content,new HotFragment());
                ft.commit();
                iv_down_all.setBackgroundResource(R.mipmap.ic_down_all);
                iv_down_hot.setBackgroundResource(R.mipmap.ic_down_hot_black);
                iv_down_main.setBackgroundResource(R.mipmap.ic_down_main);
                iv_down_classify.setBackgroundResource(R.mipmap.ic_down_classify);
                iv_down_user.setBackgroundResource(R.mipmap.ic_down_user);
                tv_down_all.setTextColor(Color.GRAY);
                tv_down_hot.setTextColor(Color.BLACK);
                tv_down_main.setTextColor(Color.GRAY);
                tv_down_classify.setTextColor(Color.GRAY);
                tv_down_user.setTextColor(Color.GRAY);
            }
        });
        ll_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ft = fragmentManager.beginTransaction();
                ft.replace(R.id.fl_home_content,new MainFragment());
                ft.commit();
                iv_down_all.setBackgroundResource(R.mipmap.ic_down_all);
                iv_down_hot.setBackgroundResource(R.mipmap.ic_down_hot);
                iv_down_main.setBackgroundResource(R.mipmap.ic_down_main_black);
                iv_down_classify.setBackgroundResource(R.mipmap.ic_down_classify);
                iv_down_user.setBackgroundResource(R.mipmap.ic_down_user);
                tv_down_all.setTextColor(Color.GRAY);
                tv_down_hot.setTextColor(Color.GRAY);
                tv_down_main.setTextColor(Color.BLACK);
                tv_down_classify.setTextColor(Color.GRAY);
                tv_down_user.setTextColor(Color.GRAY);
            }
        });
        ll_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ft = fragmentManager.beginTransaction();
                ft.replace(R.id.fl_home_content,new UserFragment());
                ft.commit();
                iv_down_all.setBackgroundResource(R.mipmap.ic_down_all);
                iv_down_hot.setBackgroundResource(R.mipmap.ic_down_hot);
                iv_down_main.setBackgroundResource(R.mipmap.ic_down_main);
                iv_down_classify.setBackgroundResource(R.mipmap.ic_down_classify);
                iv_down_user.setBackgroundResource(R.mipmap.ic_down_user_black);
                tv_down_all.setTextColor(Color.GRAY);
                tv_down_hot.setTextColor(Color.GRAY);
                tv_down_main.setTextColor(Color.GRAY);
                tv_down_classify.setTextColor(Color.GRAY);
                tv_down_user.setTextColor(Color.BLACK);
            }
        });
        ll_classify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ft = fragmentManager.beginTransaction();
                ft.replace(R.id.fl_home_content,new ClassifyFragment());
                ft.commit();
                iv_down_all.setBackgroundResource(R.mipmap.ic_down_all);
                iv_down_hot.setBackgroundResource(R.mipmap.ic_down_hot);
                iv_down_main.setBackgroundResource(R.mipmap.ic_down_main);
                iv_down_classify.setBackgroundResource(R.mipmap.ic_down_classify_black);
                iv_down_user.setBackgroundResource(R.mipmap.ic_down_user);
                tv_down_all.setTextColor(Color.GRAY);
                tv_down_hot.setTextColor(Color.GRAY);
                tv_down_main.setTextColor(Color.GRAY);
                tv_down_classify.setTextColor(Color.BLACK);
                tv_down_user.setTextColor(Color.GRAY);
            }
        });

    }
    /**
     * 初始化布局文件
     */
    private void initView() {
        ll_all = (LinearLayout) findViewById(R.id.ll_all);
        ll_hot = (LinearLayout) findViewById(R.id.ll_hot);
        ll_main = (LinearLayout) findViewById(R.id.ll_main);
        ll_classify = (LinearLayout) findViewById(R.id.ll_classify);
        ll_user = (LinearLayout) findViewById(R.id.ll_user);
        fl_home_content = (FrameLayout) findViewById(R.id.fl_home_content);
        fl_home_content = (FrameLayout) findViewById(R.id.fl_home_content);

        iv_down_all = (ImageView) findViewById(R.id.iv_down_all);
        iv_down_hot = (ImageView) findViewById(R.id.iv_down_hot);
        iv_down_main = (ImageView) findViewById(R.id.iv_down_main);
        iv_down_classify = (ImageView) findViewById(R.id.iv_down_classify);
        iv_down_user = (ImageView) findViewById(R.id.iv_down_user);

        tv_down_all = (TextView) findViewById(R.id.tv_down_all);
        tv_down_hot = (TextView) findViewById(R.id.tv_down_hot);
        tv_down_main = (TextView) findViewById(R.id.tv_down_main);
        tv_down_classify = (TextView) findViewById(R.id.tv_down_classify);
        tv_down_user = (TextView) findViewById(R.id.tv_down_user);

    }
}
