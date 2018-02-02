package com.aygames.twomonth.aybox.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.aygames.twomonth.aybox.R;
import com.aygames.twomonth.aybox.activity.GameActivity;
import com.aygames.twomonth.aybox.adapter.GameAllAdapter;
import com.aygames.twomonth.aybox.bean.Game;
import com.lzy.okgo.OkGo;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Response;

/**
 * Created by Administrator on 2018/1/18.
 */

public class Bt_Center_Fragment extends Fragment {

    private View view ;

    private GridView gv_type,gv_ticai;
    private RelativeLayout rl_center;
    private RecyclerView recyclerView_center;
    private ArrayList list_type,list_ticai;
    private ToggleButton toggle;
    private JSONArray jsonArray_type,jsonArray_ticai;
    private ArrayList<Game> arrayList_center = new ArrayList<>();
    private ArrayList<Game> arrayList = new ArrayList<>();
    private PullLoadMoreRecyclerView iRecyclerView;
    private GameAllAdapter adapter;
    private int item = 0;
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bt_center, null);

        initView();
        initData();

        iRecyclerView.setPullRefreshEnable(false);
        iRecyclerView.setFooterViewText("加载更多...");
        iRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                item = item+20;
                getAllGame();
            }
        });



        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    rl_center.setVisibility(View.VISIBLE);
                }else {
                    rl_center.setVisibility(View.GONE);
                }
            }
        });

        gv_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    String str = jsonArray_type.getString(position);
                    str = URLEncoder.encode(str);
                    String type = "label";
                    Toast.makeText(getContext(),str,Toast.LENGTH_SHORT).show();
                    toggle.setChecked(false);
                    getGameFromStyle(str,type);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        gv_ticai.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    String str = jsonArray_ticai.getString(position);
                    str = URLEncoder.encode(str);
                    String type = "theme";
                    Toast.makeText(getContext(),str,Toast.LENGTH_SHORT).show();
                    toggle.setChecked(false);
                    getGameFromStyle(str,type);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        return view;
    }

    private void initData() {

        list_type = new ArrayList<HashMap<String, Object>>();
        list_ticai = new ArrayList<HashMap<String ,Object>>();

        final String [] from ={"text"};
        final int [] to = {R.id.tv_center_type_item};
        //加载
        new Thread(){
            @Override
            public void run() {
                try {
                    Response response = OkGo.get("http://sdk.aooyou.com/index.php/DataGames/getLabel").execute();
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    jsonArray_type = jsonObject.getJSONArray("3");
                    jsonArray_ticai = jsonObject.getJSONArray("4");
                    for (int i = 0; i < jsonArray_type.length(); i++) {
                        HashMap<String,Object> map = new HashMap<String,Object>();
                        map.put("text",jsonArray_type.getString(i));
                        list_type.add(map);
                    }
                    for (int i = 0; i < jsonArray_ticai.length(); i++) {
                        HashMap<String,Object> map = new HashMap<String,Object>();
                        map.put("text",jsonArray_ticai.getString(i));
                        list_ticai.add(map);
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            SimpleAdapter simpleAdapter_type = new SimpleAdapter(getContext(),list_type,R.layout.item_center_type,from,to);
                            gv_type.setAdapter(simpleAdapter_type);
                            SimpleAdapter simpleAdapter_ticai = new SimpleAdapter(getContext(),list_ticai,R.layout.item_center_type,new String[]{"text"},new int[]{R.id.tv_center_type_item});
                            gv_ticai.setAdapter(simpleAdapter_ticai);
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        getAllGame();
    }

    private void initView() {
        rl_center = view.findViewById(R.id.rl_center_type);
        recyclerView_center = view.findViewById(R.id.recycle_center);
        gv_type = view.findViewById(R.id.gv_type);
        gv_ticai = view.findViewById(R.id.gv_ticai);
        toggle = view.findViewById(R.id.toggle);
        iRecyclerView = view.findViewById(R.id.pullLoadMoreRecyclerView);

    }

    void getGameFromStyle(final String string ,final String type){
        new Thread(){
            @Override
            public void run() {
                try {
                    Response response = OkGo.get("http://sdk.aooyou.com/index.php/DataGames/getGameLabelInfo/"+type+"/"+string).execute();
//                    Logger.msg("+++++++"+response.body().string());
                    arrayList_center.clear();
                    JSONArray jsonArray = new JSONArray(response.body().string());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Game game = new Game(
                                jsonObject.getString("ico_url")
                                ,jsonObject.getString("app_name_cn")
                                ,jsonObject.getString("gid")
                                ,jsonObject.getString("game_size")
                                ,jsonObject.getString("app_type"),jsonObject.getString("publicity")
                        );
                        arrayList_center.add(game);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        final GameAllAdapter adapter = new GameAllAdapter(getContext(),arrayList_center);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
                        recyclerView_center.setLayoutManager(linearLayoutManager);
                        recyclerView_center.setAdapter(adapter);
                        iRecyclerView.setVisibility(View.GONE);
                        recyclerView_center.setVisibility(View.VISIBLE);
                        adapter.setOnItemClickListener(new GameAllAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent intent = new Intent(getContext(), GameActivity.class);
                                intent.putExtra("gid",arrayList_center.get(position).gid);
                                intent.putExtra("type",1);
                                startActivity(intent);
                            }

                            @Override
                            public void onItemLongClick(View view, int position) {

                            }
                        });
                    }
                });
            }
        }.start();
    }
    void getAllGame(){
        //默认加载所有游戏
        new Thread(){
            @Override
            public void run() {
                try {
                    Response response = OkGo.get("http://sdk.aooyou.com/index.php/DataGames/getGameall/start/"+item).execute();
                    JSONArray jsonArray = new JSONArray(response.body().string());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Game game = new Game(
                                jsonObject.getString("ico_url")
                                ,jsonObject.getString("app_name_cn")
                                ,jsonObject.getString("gid")
                                ,jsonObject.getString("game_size")
                                ,jsonObject.getString("app_type")
                                ,jsonObject.getString("publicity")
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
                        if (item == 0){
                            adapter = new GameAllAdapter(getContext(),arrayList);
                            iRecyclerView.setLinearLayout();
                            iRecyclerView.setAdapter(adapter);
                            adapter.setOnItemClickListener(new GameAllAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    Intent intent = new Intent(getContext(), GameActivity.class);
                                    intent.putExtra("gid",arrayList.get(position).gid);
                                    startActivity(intent);
                                }

                                @Override
                                public void onItemLongClick(View view, int position) {

                                }
                            });
                        }else {
                            adapter.notifyDataSetChanged();
                            iRecyclerView.setPullLoadMoreCompleted();
                        }




                    }
                });
            }
        }.start();
    }
}
