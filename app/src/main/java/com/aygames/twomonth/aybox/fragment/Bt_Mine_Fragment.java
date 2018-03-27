package com.aygames.twomonth.aybox.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.aygames.twomonth.aybox.R;
import com.aygames.twomonth.aybox.activity.LoginActivity;
import com.aygames.twomonth.aybox.activity.ShareActivity;
import com.aygames.twomonth.aybox.service.AyboxService;



/**
 * Created by Administrator on 2018/1/18.
 */

public class Bt_Mine_Fragment extends Fragment {

    private View view;
    private TextView tv_passport , tv_telephone;
    private ImageView iv_user_head;
    private LinearLayout ll_share;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user, null);

        initView();
        initDada();

        iv_user_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getActivity(), LoginActivity.class));

            }
        });

        ll_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),ShareActivity.class));
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

        ll_share = view.findViewById(R.id.share);
    }
}
