package com.aygames.twomonth.aybox.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ToxicBakery.viewpager.transforms.CubeOutTransformer;
import com.aygames.twomonth.aybox.R;
import com.aygames.twomonth.aybox.activity.GameActivity;
import com.aygames.twomonth.aybox.adapter.FindAdapter;
import com.aygames.twomonth.aybox.adapter.GameAllAdapter;
import com.aygames.twomonth.aybox.bean.Game;
import com.aygames.twomonth.aybox.service.AyboxService;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.lzy.okgo.OkGo;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import okhttp3.Response;


/**
 * Created by Administrator on 2018/1/18.
 */

public class Bt_Find_Fragment extends Fragment {
    private View view ;
    private ConvenientBanner convenientBanner ;
    private PullLoadMoreRecyclerView recyclerView_find;
    private int item = 0;
    private ArrayList<Game> arrayList = new ArrayList<>();
    private FindAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bt_find, null);

        initView();
        initData();

        recyclerView_find.setPullRefreshEnable(false);
        recyclerView_find.setFooterViewText("加载更多...");
        recyclerView_find.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                item = item+20;
                getAllGame();
            }
        });

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
        getAllGame();

    }

    private void initView() {
        convenientBanner = view.findViewById(R.id.convenientBanner_find);
        recyclerView_find = view.findViewById(R.id.recycle_find);
    }





    void getAllGame() {
        //默认加载所有游戏
        new Thread() {
            @Override
            public void run() {
                try {
                    Response response = OkGo.get("http://sdk.aooyou.com/index.php/DataGames/getGameall/start/" + item).execute();
                    JSONArray jsonArray = new JSONArray(response.body().string());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Game game = new Game(
                                jsonObject.getString("ico_url")
                                , jsonObject.getString("app_name_cn")
                                , jsonObject.getString("gid")
                                , getRandomColorStr()
                                , getRandomHeight()+""
                                , jsonObject.getString("publicity")
                        );
                        arrayList.add(game);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (item == 0) {
                            adapter = new FindAdapter(getContext(), arrayList);
                            recyclerView_find.setLinearLayout();
                            recyclerView_find.setGridLayout(3);
                            recyclerView_find.setAdapter(adapter);
                            adapter.setOnItemClickListener(new FindAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    Intent intent = new Intent(getContext(), GameActivity.class);
                                    intent.putExtra("gid", arrayList.get(position).gid);
                                    startActivity(intent);
                                }

                                @Override
                                public void onItemLongClick(View view, int position) {

                                }
                            });
                        } else {
                            adapter.notifyDataSetChanged();
                            recyclerView_find.setPullLoadMoreCompleted();
                        }


                    }
                });
            }
        }.start();
    }

    /*
* 生成随机的颜色值
* */
    protected String getRandomColorStr(){
        String colorStr="";
        StringBuilder tempStr=new StringBuilder();
        String[] c= {"a","b","c","d","e","f","0","1","3","4","5","6","7","8","9"};

        Random random=new Random();
        for (int i=1;i<=6;i++){
            tempStr.append(c[random.nextInt(c.length)]);
        }

        colorStr="#"+tempStr;
//            Log.d("TAG",colorStr);

        return colorStr;
    }

    /*
    * 生成随机的高度,为了实现不规则高度
    * */
    protected int getRandomHeight(){
        int height=384;
        height= (int) (200+Math.random()*200);
        return height;
    }
}
