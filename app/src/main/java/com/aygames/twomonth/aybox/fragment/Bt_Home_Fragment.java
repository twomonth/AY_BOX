package com.aygames.twomonth.aybox.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.Toast;

import com.aygames.twomonth.aybox.R;
import com.aygames.twomonth.aybox.activity.GameActivity;
import com.aygames.twomonth.aybox.adapter.GameAllAdapter;
import com.aygames.twomonth.aybox.adapter.SmallAdapter;
import com.aygames.twomonth.aybox.bean.Game;
import com.aygames.twomonth.aybox.utils.DialogUtil;
import com.aygames.twomonth.aybox.utils.FullyLinearLayoutManager;
import com.aygames.twomonth.aybox.utils.Logger;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * Created by Administrator on 2018/1/18.
 */

public class Bt_Home_Fragment extends Fragment {

    private RecyclerView recyclerView ;
    private RecyclerView recycle_gameall;
    private ArrayList<String> imageList;
    private ConvenientBanner convenientBanner;
    private ArrayList<Game> arrayListBanner;
    private ArrayList<Game> arrayListTuijian;
    private ArrayList<Game> arrayListGameAll;
    private GameAllAdapter gameAllAdapter;
    private SmallAdapter smipleAdapter;
    private View view ;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bt_home, null);
        initView();
        initData();

        return view;
    }


    private void initView() {
        recyclerView = view.findViewById(R.id.recycle);
        recycle_gameall = view.findViewById(R.id.recycle_gameall);
        convenientBanner = view.findViewById(R.id.convenientBanner);
        DialogUtil.showDialog(getContext(),"正在请求数据");
    }
    private void initData() {
        //读取网络数据游戏列表
        new Thread(){
            @Override
            public void run() {
                try {
                    Response response = OkGo.get("http://sdk.aooyou.com/index.php/DataGames/getGames").execute();
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONArray jsonArray_banner = jsonObject.getJSONArray("banner");
                    JSONArray jsojArary_listtj = jsonObject.getJSONArray("listtj");
                    JSONArray jsojArary_gameall = jsonObject.getJSONArray("gameall");

                    Logger.msg("轮播："+jsonArray_banner.toString());
                    Logger.msg("列表："+jsojArary_listtj.toString());
                    Logger.msg("所有："+jsojArary_gameall.toString());

                    arrayListBanner = new ArrayList<>();
                    arrayListTuijian = new ArrayList<>();
                    arrayListGameAll = new ArrayList<>();
                    //banner 列表
                    for (int i = 0 ;i < jsonArray_banner.length();i++){
                        JSONObject jsonObject_banner = jsonArray_banner.getJSONObject(i);
                        Game game = new Game(jsonObject_banner.getString("carousel_url"),
                                jsonObject_banner.getString("app_name_cn"),
                                jsonObject_banner.getString("gid")
                                );
                        arrayListBanner.add(game);
                        Logger.msg(game.ico_url+"=="+game.app_name_cn);
                    }
                    //推荐列表
                    for (int i = 0; i < jsojArary_listtj.length(); i++) {
                        JSONObject jsonObject_tj = jsojArary_listtj.getJSONObject(i);
                        Game game = new Game(jsonObject_tj.getString("ico_url"),
                                jsonObject_tj.getString("app_name_cn"),
                                jsonObject_tj.getString("gid")
                        );
                        arrayListTuijian.add(game);
                        Logger.msg(game.ico_url+"=="+game.app_name_cn);
                    }
//                    //所有游戏列表
                    for (int i = 0; i < jsojArary_gameall.length(); i++) {
                        JSONObject jsonObject_gameall = jsojArary_gameall.getJSONObject(i);
                        Game game = new Game(
                                jsonObject_gameall.getString("ico_url")
                                ,jsonObject_gameall.getString("app_name_cn")
                                ,jsonObject_gameall.getString("gid")
                                ,jsonObject_gameall.getString("game_size")
                                ,jsonObject_gameall.getString("app_type"),jsonObject_gameall.getString("publicity")
                                );
                        arrayListGameAll.add(game);
                        Logger.msg(game.ico_url+"=="+game.app_name_cn);
                    }
                    imageList = new ArrayList();
                    for (int i = 0; i < arrayListBanner.size(); i++) {
                        imageList.add(arrayListBanner.get(i).ico_url);

                    }

                } catch (IOException e) {

                } catch (JSONException e) {

                }
                //更新ui
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        smipleAdapter = new SmallAdapter(getContext(),  arrayListTuijian);
                        recyclerView.setAdapter(smipleAdapter);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
                        recyclerView.setLayoutManager(linearLayoutManager);

                        gameAllAdapter = new GameAllAdapter(getContext(),arrayListGameAll);
                        recycle_gameall.setAdapter(gameAllAdapter);
//                        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
//                        recycle_gameall.setLayoutManager(linearLayoutManager1);
                        FullyLinearLayoutManager linearLayoutManager3 = new FullyLinearLayoutManager(getContext());
                        recycle_gameall.setNestedScrollingEnabled(false);
                        recycle_gameall.setLayoutManager(linearLayoutManager3);
                        recycle_gameall.setItemAnimator(new DefaultItemAnimator());

                        //轮播图
                        convenientBanner.setPages(
                                new CBViewHolderCreator<NetworkImageHolderView>() {
                                    @Override
                                    public NetworkImageHolderView createHolder() {
                                        return new NetworkImageHolderView();
                                    }
                                }, imageList)
                                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                                .setPageIndicator(new int[]{R.mipmap.ic_down_hot, R.mipmap.ic_down_hot_black})
                                //设置指示器的方向
                                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
                                //设置翻页的效果，不需要翻页效果可用不设
                                //.setPageTransformer(Transformer.DefaultTransformer);    集成特效之后会有白屏现象，新版已经分离，如果要集成特效的例子可以看Demo的点击响应。
                                //convenientBanner.setManualPageable(false);//设置不能手动影响
                        convenientBanner.startTurning(2000);
                        convenientBanner.setManualPageable(true);//设置不能手动影响  默认是手指触摸 轮播图不能翻页
                        convenientBanner.setPointViewVisible(true);
                        smipleAdapter.setOnItemClickListener(new SmallAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Toast.makeText(getContext(),arrayListTuijian.get(position).app_name_cn,Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getContext(), GameActivity.class);
                                intent.putExtra("gid",arrayListTuijian.get(position).gid);
                                intent.putExtra("type",1);
                                startActivity(intent);
                            }

                            @Override
                            public void onItemLongClick(View view, int position) {

                            }
                        });
                        gameAllAdapter.setOnItemClickListener(new GameAllAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Toast.makeText(getContext(),arrayListGameAll.get(position).app_name_cn,Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getContext(), GameActivity.class);
                                intent.putExtra("gid",arrayListGameAll.get(position).gid);
                                intent.putExtra("type",1);
                                startActivity(intent);
                            }

                            @Override
                            public void onItemLongClick(View view, int position) {

                            }
                        });
                        convenientBanner.setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                Toast.makeText(getContext(),arrayListBanner.get(position).app_name_cn,Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getContext(), GameActivity.class);
                                intent.putExtra("gid",arrayListBanner.get(position).gid);
                                intent.putExtra("type",1);
                                startActivity(intent);
                            }
                        });
                        try {
                            DialogUtil.dismissDialog();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }.start();
    }

    //A、网络图片
    public class NetworkImageHolderView implements Holder<String> {
        private ImageView imageView;
        @Override
        public View createView(Context context) {
            //你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
//            imageView.setImageResource(R.mipmap.background);
//            Logger.msg("图片网址：：：：：：："+ data);
            Glide.with(context).load(data).error(R.mipmap.background).into(imageView);
        }
    }

}



