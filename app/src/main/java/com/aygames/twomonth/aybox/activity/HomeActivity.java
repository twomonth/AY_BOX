package com.aygames.twomonth.aybox.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.aygames.twomonth.aybox.R;
import com.aygames.twomonth.aybox.serviece.LoginService;

/**
 * Created by Administrator on 2017/12/4.
 */

public class HomeActivity extends AppCompatActivity {

    private LinearLayout ll_all,ll_hot,ll_main,ll_search,ll_classify;
    private FrameLayout fl_home_content;
    private ImageView iv_home,iv_user;
    private PopupWindow popupWindow;
    View contentView;
    FragmentManager fragmentManager = getSupportFragmentManager();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        Intent intent = new Intent(this, LoginService.class);
        startService(intent);
//        ll_all.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FragmentTransaction ft = fragmentManager.beginTransaction();
//                ft.replace(R.id.fl_home_content,new AllFragment());
//                ft.commit();
//            }
//        });
//        ll_hot.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FragmentTransaction ft = fragmentManager.beginTransaction();
//                ft.replace(R.id.fl_home_content,new HotFragment());
//                ft.commit();
//            }
//        });
//        ll_main.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FragmentTransaction ft = fragmentManager.beginTransaction();
//                ft.replace(R.id.fl_home_content,new MainFragment());
//                ft.commit();
//            }
//        });
//        ll_search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FragmentTransaction ft = fragmentManager.beginTransaction();
//                ft.replace(R.id.fl_home_content,new SearchFragment());
//                ft.commit();
//            }
//        });
//        ll_classify.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FragmentTransaction ft = fragmentManager.beginTransaction();
//                ft.replace(R.id.fl_home_content,new ClassifyFragment());
//                ft.commit();
//            }
//        });
        iv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.WRAP_CONTENT,  LinearLayout.LayoutParams.WRAP_CONTENT, true);
                popupWindow.showAsDropDown(v);
            }
        });
        iv_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
    /**
     * 初始化布局文件
     */
    private void initView() {
//        ll_all = (LinearLayout) findViewById(R.id.ll_all);
//        ll_hot = (LinearLayout) findViewById(R.id.ll_hot);
//        ll_main = (LinearLayout) findViewById(R.id.ll_main);
//        ll_classify = (LinearLayout) findViewById(R.id.ll_classify);
//        ll_search = (LinearLayout) findViewById(R.id.ll_search);
//        fl_home_content = (FrameLayout) findViewById(R.id.fl_home_content);
        iv_home = (ImageView) findViewById(R.id.iv_home);
        contentView = View.inflate(HomeActivity.this,R.layout.popupwindow,null);
        iv_user = (ImageView) contentView.findViewById(R.id.iv_user);
        fl_home_content = (FrameLayout) findViewById(R.id.fl_home_content);
    }
}
