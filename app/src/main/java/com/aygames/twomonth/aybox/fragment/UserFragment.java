package com.aygames.twomonth.aybox.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aygames.twomonth.aybox.R;
import com.aygames.twomonth.aybox.activity.LoginActivity;


/**
 * Created by Administrator on 2017/11/28.
 */

public class UserFragment extends Fragment {

    private View view;
    private ImageView iv_user_head;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user,null,false);

        initView();

        iv_user_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
    public void initView(){
        iv_user_head = view.findViewById(R.id.iv_user_head);
    }
}
