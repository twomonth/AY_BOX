package com.aygames.twomonth.aybox.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aygames.twomonth.aybox.R;
import com.aygames.twomonth.aybox.activity.LoginActivity;
import com.aygames.twomonth.aybox.activity.NewsActivity;
import com.aygames.twomonth.aybox.adapter.NewsBaseAdapter;
import com.aygames.twomonth.aybox.bean.News;
import com.aygames.twomonth.aybox.service.AyboxService;
import com.aygames.twomonth.aybox.utils.Logger;
import com.lzy.okgo.OkGo;

import org.json.JSONArray;
import org.json.JSONException;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;

import cn.sharesdk.onekeyshare.OnekeyShare;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/1/18.
 */

public class Bt_Mine_Fragment extends Fragment {

    private View view;
    private TextView tv_passport , tv_telephone;
    private ImageView iv_user_head;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user, null);

        initView();
        initDada();

        iv_user_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getActivity(), LoginActivity.class));

                showShare();
            }
        });

        return view;
    }

    private void initDada() {
        if (AyboxService.isLogin == true){
            tv_telephone.setText(AyboxService.telephone);
            tv_passport.setText(AyboxService.passport);
        }else {
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }

    }

    private void initView() {
        tv_passport = view.findViewById(R.id.tv_passport);
        tv_telephone = view.findViewById(R.id.tv_telephone);
        iv_user_head = view.findViewById(R.id.iv_user_head);
    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，微信、QQ和QQ空间等平台使用
        oks.setTitle("福利盒子");
        // titleUrl QQ和QQ空间跳转链接
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url在微信、微博，Facebook等平台中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网使用
        oks.setComment("我是测试评论文本");
        // 启动分享GUI
        oks.show(getActivity());
    }


}
