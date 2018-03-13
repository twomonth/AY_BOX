package com.aygames.twomonth.aybox.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ToxicBakery.viewpager.transforms.CubeOutTransformer;
import com.aygames.twomonth.aybox.R;
import com.aygames.twomonth.aybox.activity.BTgameActivity;
import com.aygames.twomonth.aybox.activity.GameActivity;
import com.aygames.twomonth.aybox.activity.ManVActivity;
import com.aygames.twomonth.aybox.activityfb.RebateActivity;
import com.aygames.twomonth.aybox.adapter.GameAllAdapter;
import com.aygames.twomonth.aybox.adapter.SmallAdapter;
import com.aygames.twomonth.aybox.bean.Game;
import com.aygames.twomonth.aybox.service.AyboxService;
import com.aygames.twomonth.aybox.utils.DialogUtil;
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
import okhttp3.Response;

/**
 * Created by Administrator on 2018/1/18.
 */

public class Bt_Home_Fragment extends Fragment {

    private RecyclerView recyclerView ;
//    private RecyclerView recycle_gameall;
    private ArrayList<String> imageList;
    private ConvenientBanner convenientBanner;
    private ArrayList<Game> arrayListBanner;
    private ArrayList<Game> arrayListTuijian;
    private ArrayList<Game> arrayListNewGames;
//    private ArrayList<Game> arrayListGameAll;
    private ArrayList<Game> arrayList1,arrayList2,arrayList3,arrayList4;
    private RecyclerView recycle_game1,recycle_game2,recycle_game3,recycle_game4,recycle_newgames;
    private ImageView iv_hltuijian1,iv_hltuijian2,iv_hltuijian3,iv_hltuijian4;
    private GameAllAdapter adapter1,adapter2,adapter3,adapter4;
    private SmallAdapter smipleAdapter,smipleAdapter_newgames;
    private JSONArray jsonArray_hltuijian;
    private View view ;
    private TextView tv_click;
    private RelativeLayout rl_bt,rl_manv,rl_fuli,rl_fanli;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bt_home, null);
        initView();
        initData();

        rl_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BTgameActivity.class);
                startActivity(intent);
            }
        });

        rl_manv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ManVActivity.class);
                startActivity(intent);
            }
        });
        rl_fuli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RebateActivity.class);
                startActivity(intent);
            }
        });
        rl_fanli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RebateActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }


    private void initView() {

        tv_click = view.findViewById(R.id.tv_click);
        recyclerView = view.findViewById(R.id.recycle);
//        recycle_gameall = view.findViewById(R.id.recycle_gameall);
        convenientBanner = view.findViewById(R.id.convenientBanner);

        recycle_game1 = view.findViewById(R.id.recycle_game1);
        recycle_game2 = view.findViewById(R.id.recycle_game2);
        recycle_game3 = view.findViewById(R.id.recycle_game3);
        recycle_game4 = view.findViewById(R.id.recycle_game4);

        iv_hltuijian1 = view.findViewById(R.id.iv_hltuijian1);
        iv_hltuijian2 = view.findViewById(R.id.iv_hltuijian2);
        iv_hltuijian3 = view.findViewById(R.id.iv_hltuijian3);
        iv_hltuijian4 = view.findViewById(R.id.iv_hltuijian4);

        rl_bt = view.findViewById(R.id.rl_bt);
        rl_manv = view.findViewById(R.id.rl_manv);
        rl_fuli = view.findViewById(R.id.rl_fuli);
        rl_fanli = view.findViewById(R.id.rl_fanli);

        recycle_newgames = view.findViewById(R.id.recycle_newgames);

        DialogUtil.showDialog(getContext(),"正在请求数据");
    }
    private void initData() {
        //读取网络数据游戏列表
        new Thread(){
            @Override
            public void run() {
                try {
                    Response response = OkGo.get("http://sysdk.syyouxi.com/index.php/DataGames/getGames").execute();
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONArray jsonArray_banner = jsonObject.getJSONArray("banner");
                    JSONArray jsonArary_listtj = jsonObject.getJSONArray("listtj");
                    JSONArray jsonArary_gameall = jsonObject.getJSONArray("gameall");
                    JSONArray jsonArray_newgames = jsonObject.getJSONArray("newgames");
                    jsonArray_hltuijian = jsonObject.getJSONArray("hltj");

                    Logger.msg("轮播："+jsonArray_banner.toString());
                    Logger.msg("列表："+jsonArary_listtj.toString());
                    Logger.msg("所有："+jsonArary_gameall.toString());

                    arrayListBanner = new ArrayList<>();
                    arrayListTuijian = new ArrayList<>();
                    arrayListNewGames = new ArrayList<>();
                    arrayList1 = new ArrayList<>();
                    arrayList2 = new ArrayList<>();
                    arrayList3 = new ArrayList<>();
                    arrayList4 = new ArrayList<>();
//                    arrayListGameAll = new ArrayList<>();
//                    arrayListHLtuijian = new ArrayList<>();
                    //新游
                    for (int i = 0; i < jsonArray_newgames.length(); i++) {
                        JSONObject jsonObject_newgames = jsonArray_newgames.getJSONObject(i);
                        Game game = new Game(jsonObject_newgames.getString("ico_url"),
                                jsonObject_newgames.getString("app_name_cn"),
                                jsonObject_newgames.getString("gid")
                        );
                        arrayListNewGames.add(game);

                    }
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
                    AyboxService.banner = arrayListBanner;
                    //推荐列表
                    for (int i = 0; i < jsonArary_listtj.length(); i++) {
                        JSONObject jsonObject_tj = jsonArary_listtj.getJSONObject(i);
                        Game game = new Game(jsonObject_tj.getString("ico_url"),
                                jsonObject_tj.getString("app_name_cn"),
                                jsonObject_tj.getString("gid")
                        );
                        arrayListTuijian.add(game);
                        Logger.msg(game.ico_url+"=="+game.app_name_cn);
                    }
//                    //所有游戏列表
                    for (int i = 0; i < jsonArary_gameall.length(); i++) {
                        JSONObject jsonObject_gameall = jsonArary_gameall.getJSONObject(i);
                        Game game = new Game(
                                jsonObject_gameall.getString("ico_url")
                                ,jsonObject_gameall.getString("app_name_cn")
                                ,jsonObject_gameall.getString("gid")
                                ,jsonObject_gameall.getString("game_size")
                                ,jsonObject_gameall.getString("app_type")
                                ,jsonObject_gameall.getString("publicity")
                                );
//                        arrayListGameAll.add(game);
                        if (i<5){
                            arrayList1.add(game);
                        }else if (4<i & i<10){
                            arrayList2.add(game);
                        }else if (9<i & i<15){
                            arrayList3.add(game);
                        }else {
                            arrayList4.add(game);
                        }
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
                        //新游
                        smipleAdapter_newgames = new SmallAdapter(getContext(),arrayListNewGames);
                        recycle_newgames.setAdapter(smipleAdapter_newgames);
                        LinearLayoutManager linearLayoutManager_newgames = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
                        recycle_newgames.setLayoutManager(linearLayoutManager_newgames);
                        //推荐
                        smipleAdapter = new SmallAdapter(getContext(),  arrayListTuijian);
                        recyclerView.setAdapter(smipleAdapter);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
                        recyclerView.setLayoutManager(linearLayoutManager);
//                        gameAllAdapter = new GameAllAdapter(getContext(),arrayListGameAll);
//                        recycle_gameall.setAdapter(gameAllAdapter);
//                        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
//                        recycle_gameall.setLayoutManager(linearLayoutManager1);
//                        FullyLinearLayoutManager linearLayoutManager3 = new FullyLinearLayoutManager(getContext());
//                        recycle_gameall.setNestedScrollingEnabled(false);
//                        recycle_gameall.setLayoutManager(linearLayoutManager3);
//                        recycle_gameall.setItemAnimator(new DefaultItemAnimator());
                        adapter1 = new GameAllAdapter(getContext(),arrayList1);
                        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
                        recycle_game1.setLayoutManager(linearLayoutManager1);
                        recycle_game1.setAdapter(adapter1);

                        adapter2 = new GameAllAdapter(getContext(),arrayList2);
                        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
                        recycle_game2.setLayoutManager(linearLayoutManager2);
                        recycle_game2.setAdapter(adapter2);

                        adapter3 = new GameAllAdapter(getContext(),arrayList3);
                        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
                        recycle_game3.setLayoutManager(linearLayoutManager3);
                        recycle_game3.setAdapter(adapter3);

                        adapter4 = new GameAllAdapter(getContext(),arrayList4);
                        LinearLayoutManager linearLayoutManager4 = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
                        recycle_game4.setLayoutManager(linearLayoutManager4);
                        recycle_game4.setAdapter(adapter4);

                        try {
                            Glide.with(getContext()).load(jsonArray_hltuijian.getJSONObject(0).getString("img") ).error(R.mipmap.ic_launcher).into(iv_hltuijian1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        iv_hltuijian1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getContext(), GameActivity.class);
                                try {
                                    intent.putExtra("gid",jsonArray_hltuijian.getJSONObject(0).getString("gid"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                startActivity(intent);
                            }
                        });

                        try {
                            Glide.with(getContext()).load(jsonArray_hltuijian.getJSONObject(1).getString("img") ).error(R.mipmap.ic_launcher).into(iv_hltuijian2);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        iv_hltuijian2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getContext(), GameActivity.class);
                                try {
                                    intent.putExtra("gid",jsonArray_hltuijian.getJSONObject(1).getString("gid"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                startActivity(intent);
                            }
                        });

                        try {
                            Glide.with(getContext()).load(jsonArray_hltuijian.getJSONObject(2).getString("img") ).error(R.mipmap.ic_launcher).into(iv_hltuijian3);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        iv_hltuijian3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getContext(), GameActivity.class);
                                try {
                                    intent.putExtra("gid",jsonArray_hltuijian.getJSONObject(2).getString("gid"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                startActivity(intent);
                            }
                        });

                        try {
                            Glide.with(getContext()).load(jsonArray_hltuijian.getJSONObject(3).getString("img") ).error(R.mipmap.ic_launcher).into(iv_hltuijian4);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        iv_hltuijian4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getContext(), GameActivity.class);
                                try {
                                    intent.putExtra("gid",jsonArray_hltuijian.getJSONObject(3).getString("gid"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                startActivity(intent);
                            }
                        });


                        //轮播图
                        convenientBanner.setPages(
                                new CBViewHolderCreator<NetworkImageHolderView>() {
                                    @Override
                                    public NetworkImageHolderView createHolder() {
                                        return new NetworkImageHolderView();
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

                        smipleAdapter_newgames.setOnItemClickListener(new SmallAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Toast.makeText(getContext(),arrayListNewGames.get(position).app_name_cn,Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getContext(), GameActivity.class);
                                intent.putExtra("gid",arrayListNewGames.get(position).gid);
                                intent.putExtra("type",1);
                                startActivity(intent);
                            }

                            @Override
                            public void onItemLongClick(View view, int position) {

                            }
                        });
                        adapter1.setOnItemClickListener(new GameAllAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Toast.makeText(getContext(),arrayList1.get(position).app_name_cn,Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getContext(), GameActivity.class);
                                intent.putExtra("gid",arrayList1.get(position).gid);
                                intent.putExtra("type",1);
                                startActivity(intent);
                            }

                            @Override
                            public void onItemLongClick(View view, int position) {

                            }
                        });
                        adapter4.setOnItemClickListener(new GameAllAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Toast.makeText(getContext(),arrayList1.get(position).app_name_cn,Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getContext(), GameActivity.class);
                                intent.putExtra("gid",arrayList1.get(position).gid);
                                intent.putExtra("type",1);
                                startActivity(intent);
                            }

                            @Override
                            public void onItemLongClick(View view, int position) {

                            }
                        });
                        adapter3.setOnItemClickListener(new GameAllAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Toast.makeText(getContext(),arrayList1.get(position).app_name_cn,Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getContext(), GameActivity.class);
                                intent.putExtra("gid",arrayList1.get(position).gid);
                                intent.putExtra("type",1);
                                startActivity(intent);
                            }

                            @Override
                            public void onItemLongClick(View view, int position) {

                            }
                        });
                        adapter2.setOnItemClickListener(new GameAllAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Toast.makeText(getContext(),arrayList1.get(position).app_name_cn,Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getContext(), GameActivity.class);
                                intent.putExtra("gid",arrayList1.get(position).gid);
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
    public static class NetworkImageHolderView implements Holder<String> {
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



