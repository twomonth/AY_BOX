package com.aygames.twomonth.aybox.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aygames.twomonth.aybox.R;

/**
 * Created by Administrator on 2018/1/18.
 */

public class Bt_Order_Fragment extends Fragment {

    private View view ;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bt_order, null);
        return view;
    }
}
