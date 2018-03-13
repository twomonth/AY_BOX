package com.aygames.twomonth.aybox.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ToxicBakery.viewpager.transforms.CubeOutTransformer;
import com.aygames.twomonth.aybox.R;
import com.aygames.twomonth.aybox.activity.GameActivity;
import com.aygames.twomonth.aybox.service.AyboxService;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;

import java.util.ArrayList;


/**
 * Created by Administrator on 2018/1/18.
 */

public class Bt_Find_Fragment extends Fragment {
    private View view ;
    private ConvenientBanner convenientBanner ;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bt_find, null);

        initView();
        initData();

        convenientBanner.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(getContext(),AyboxService.banner.get(position).app_name_cn,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), GameActivity.class);
                intent.putExtra("gid",AyboxService.banner.get(position).gid);
                intent.putExtra("type",1);
                startActivity(intent);
            }
        });

        return view;
    }

    private void initData() {

        ArrayList imageList = new ArrayList();
        for (int i = 0; i < AyboxService.banner.size(); i++) {
            imageList.add(AyboxService.banner.get(i).ico_url);
        }
        //轮播图
        convenientBanner.setPages(
                new CBViewHolderCreator<Bt_Home_Fragment.NetworkImageHolderView>() {
                    @Override
                    public Bt_Home_Fragment.NetworkImageHolderView createHolder() {
                        return new Bt_Home_Fragment.NetworkImageHolderView();
                    }
                }, imageList)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.mipmap.nb, R.mipmap.nc})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
        //设置翻页的效果，不需要翻页效果可用不设
//                        convenientBanner.setPageTransformer(Transformer.DefaultTransformer);   // 集成特效之后会有白屏现象，新版已经分离，如果要集成特效的例子可以看Demo的点击响应
        convenientBanner.getViewPager().setPageTransformer(true, new CubeOutTransformer());
        convenientBanner.startTurning(3000);
        convenientBanner.setManualPageable(true);//设置不能手动影响  默认是手指触摸 轮播图不能翻页
        convenientBanner.setPointViewVisible(true);
        convenientBanner.setScrollDuration(1000);

    }


    private void initView() {
        convenientBanner = view.findViewById(R.id.convenientBanner_find);

    }


}
