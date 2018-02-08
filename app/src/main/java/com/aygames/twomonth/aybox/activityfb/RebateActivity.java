package com.aygames.twomonth.aybox.activityfb;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;

import com.aygames.twomonth.aybox.R;
import com.aygames.twomonth.aybox.fragment.Bt_Gift_Fragment;
import com.aygames.twomonth.aybox.fragment.Bt_News_Fragment;
import com.aygames.twomonth.aybox.utils.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RebateActivity extends AppCompatActivity {

    @Bind(R.id.iv_rebater_back) ImageView iv_rebater_back;
    @Bind(R.id.tab_rebate)
    TabLayout tabLayout;
    @Bind(R.id.viewpager_rebate)
    ViewPager viewPager;
    private List<Pair<String,Fragment>> items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rebate);

        if (getIntent().getData() == null){
            Logger.msg("盒子内部启动");
        }else {
            Logger.msg("SDK启动"+getIntent().getData());
        }
        ButterKnife.bind(this);

        initData();

    }

    private void initData() {
        items = new ArrayList<>();
        items.add(new Pair<String, Fragment>("任务", new Bt_Gift_Fragment()));
        items.add(new Pair<String, Fragment>("订单", new Bt_News_Fragment()));
        viewPager.setAdapter(new MainAdapter(this.getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
    }

    @OnClick(R.id.iv_rebater_back)
    public void rebater_backClick(View view){
        finish();
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
