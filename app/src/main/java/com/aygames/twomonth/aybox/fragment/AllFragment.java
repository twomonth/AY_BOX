package com.aygames.twomonth.aybox.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.ViewPropertyAnimator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aygames.twomonth.aybox.R;
import com.aygames.twomonth.aybox.adapter.MainPagerAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/11/28.
 */

public class AllFragment extends Fragment {

    private ViewPager viewPager;
    private List<Fragment> fragments;
    private TextView tv_tab_bt;
    private TextView tv_tab_discount;
    private TextView tv_tab_h5;
    private MainPagerAdapter adapter;
    private LinearLayout ll_tab_bt;
    private LinearLayout ll_tab_discount;
    private LinearLayout ll_tab_h5;
    private View view , v_indicate_line;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_all,null,false);
        initView();
        intitData();
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            //切换完成后调用，传入的参数是切换后的界面的索引
            @Override
            public void onPageSelected(int position) {
                textLightAndScale();
            }

            //滑动过程不断调用
            //如果滑动过程中出现两个界面，position是前一个的索引
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
                System.out.println(positionOffsetPixels);
                //计算红线位移的距离
                int distance = positionOffsetPixels / 3;

                //持续时间为0，立刻生效，因为红线的移动需要与用户滑动同步
                v_indicate_line.animate().translationX(distance + position * v_indicate_line.getWidth()).setDuration(0);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // TODO Auto-generated method stub

            }
        });

        textLightAndScale();

        ll_tab_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
            }
        });
        ll_tab_discount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
            }
        });
        ll_tab_h5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(2);
            }
        });

        v_indicate_line = view.findViewById(R.id.v_indicate_line);


        //设置红线的宽度
        computeIndicateLineWidth();

        return view;
    }

    private void intitData() {
        fragments = new ArrayList<Fragment>();
        //创建Fragment对象，存入集合
        AllFragment_BT fragment1 = new AllFragment_BT();
        AllFragment_discount fragment2 = new AllFragment_discount();
        AllFragment_H5 fragment3 = new AllFragment_H5();
        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);
        adapter = new MainPagerAdapter(getChildFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
    }

    private void initView() {
        //拿到布局文件中的组件
        viewPager = (ViewPager) view.findViewById(R.id.viewpager_all);

        tv_tab_bt = (TextView)view. findViewById(R.id.tv_tab_bt);
        tv_tab_discount = (TextView) view.findViewById(R.id.tv_tab_discount);
        tv_tab_h5 = (TextView) view.findViewById(R.id.tv_tab_h5);

        ll_tab_bt = (LinearLayout) view.findViewById(R.id.ll_tab_bt);
        ll_tab_discount = (LinearLayout)view.findViewById(R.id.ll_tab_discount);
        ll_tab_h5 = (LinearLayout) view.findViewById(R.id.ll_tab_h5);

    }

    /**
     * 改变选项卡的文本的颜色和大小
     */
    private void textLightAndScale() {
        //获取viewPager当前显示界面的索引
        int item = viewPager.getCurrentItem();
        //根据viewPager的界面索引决定选项卡颜色
        tv_tab_bt.setTextColor(item == 0? Color.RED : 0xaa666666);
        tv_tab_discount.setTextColor(item == 1? Color.RED : 0xaa666666);
        tv_tab_h5.setTextColor(item == 2? Color.RED : 0xaa666666);

        // 要操作的对象改变宽度至指定比例
        tv_tab_bt.animate().scaleX(item == 0? 1.2f : 1).setDuration(200);
        tv_tab_discount.animate().scaleX(item == 1? 1.2f : 1).setDuration(200);
        tv_tab_h5.animate().scaleX(item == 2? 1.2f : 1).setDuration(200);
        tv_tab_bt.animate().scaleY(item == 0? 1.2f : 1).setDuration(200);
        tv_tab_discount.animate().scaleY(item == 1? 1.2f : 1).setDuration(200);
        tv_tab_h5.animate().scaleY(item == 2? 1.2f : 1).setDuration(200);
    }

    /**
     * 指定红线宽度为屏幕1/3
     */
    private void computeIndicateLineWidth() {
        int width = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        v_indicate_line.getLayoutParams().width = width / 3;
    }

}
