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
import android.widget.Toast;

import com.aygames.twomonth.aybox.R;

import com.aygames.twomonth.aybox.bean.MessageEvent;
import com.aygames.twomonth.aybox.fragment.Rebate_Fragment;
import com.aygames.twomonth.aybox.fragment.Rebate_Fragment_order;
import com.aygames.twomonth.aybox.utils.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    Fragment rebateFragment,rebateFragmentOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rebate);

        if (getIntent().getStringArrayListExtra("msg") == null){
            Logger.msg("盒子内部启动");
        }else {
            ArrayList list = getIntent().getStringArrayListExtra("msg");

            for (int i = 0; i < list.size(); i++) {
                Logger.msg("SDK启动"+list.get(i));
            }
        }
        ButterKnife.bind(this);
        initData();
        EventBus.getDefault().register(this);

    }

    private void initData() {
        rebateFragment = new Rebate_Fragment();
        rebateFragmentOrder = new Rebate_Fragment_order();
        items = new ArrayList<>();
        items.add(new Pair<String, Fragment>("任务", rebateFragment));
        items.add(new Pair<String, Fragment>("订单", rebateFragmentOrder));
        viewPager.setAdapter(new MainAdapter(getSupportFragmentManager()));
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        if (messageEvent.getMessage().equals("success")){
            Toast.makeText(this,"提交成功等待人工审核",Toast.LENGTH_SHORT).show();
//            Toast.makeText(this,messageEvent.getMessage(),Toast.LENGTH_SHORT).show();
            initData();

        }else {
            Toast.makeText(this,"提交失败，请联系客服",Toast.LENGTH_SHORT).show();
//            Toast.makeText(this,messageEvent.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }


}
