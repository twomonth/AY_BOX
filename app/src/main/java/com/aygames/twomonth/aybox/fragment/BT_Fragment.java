package com.aygames.twomonth.aybox.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import com.aygames.twomonth.aybox.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/18.
 */

public class BT_Fragment extends Fragment {
    private View view;
    private ViewPager vp_bt;
    private TabLayout tab_bt;
    private List<Pair<String,Fragment>> items;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bt,null);
        initView();
        initData();
        return view;
    }

    private void initView() {
        vp_bt = view.findViewById(R.id.vp_bt);
        tab_bt = view.findViewById(R.id.tab_bt);
    }

    private void initData() {
        items = new ArrayList<>();
        items.add(new Pair<String, Fragment>("首页", new Bt_Home_Fragment()));
        items.add(new Pair<String, Fragment>("开服", new Bt_Start_Fragment()));
        items.add(new Pair<String, Fragment>("新闻", new Bt_News_Fragment()));
        items.add(new Pair<String, Fragment>("预约", new Bt_Order_Fragment()));
        items.add(new Pair<String, Fragment>("礼包", new Bt_Gift_Fragment()));
        vp_bt.setAdapter(new MainAdapter(getChildFragmentManager()));
        tab_bt.setupWithViewPager(vp_bt);
    }

    private class MainAdapter extends FragmentPagerAdapter {

        MainAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return items.get(position).second;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return items.get(position).first;
        }
    }

}
