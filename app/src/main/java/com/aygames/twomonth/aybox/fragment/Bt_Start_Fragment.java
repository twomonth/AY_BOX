package com.aygames.twomonth.aybox.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aygames.twomonth.aybox.R;
import com.aygames.twomonth.aybox.activity.GameActivity;
import com.aygames.twomonth.aybox.adapter.GameStartAdapter;
import com.aygames.twomonth.aybox.bean.Game;
import com.aygames.twomonth.aybox.utils.Logger;
import com.lzy.okgo.OkGo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Response;

/**
 * Created by Administrator on 2018/1/18.
 */

public class Bt_Start_Fragment extends Fragment {

    private View view ;
    private JSONObject jsonObject_game;
    private ArrayList<Game> gameList;

    private RecyclerView recyclerView ;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bt_start, null);
        initView();
        initData();
        return view;
    }

    private void initView() {
        recyclerView = view.findViewById(R.id.recycle_start);
    }

    private void initData() {

        new Thread(){
            @Override
            public void run() {
                try {
                    Response response = OkGo.get("http://sdk.aooyou.com/index.php/DataGames/getKaifu").execute();
                    JSONArray jsonArray = new JSONArray(response.body().string());
                    Logger.msg(jsonArray.toString());
                    gameList = new ArrayList();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject_game = jsonArray.getJSONObject(i);
                        Game game = new Game(jsonObject_game.getString("ico_url")
                                ,jsonObject_game.getString("app_name")
                                ,jsonObject_game.getString("gid")
                                ,jsonObject_game.getString("time")
                                ,jsonObject_game.getString("server")
                                ,jsonObject_game.getString("app_type")
                                ,false);
                        gameList.add(game);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        GameStartAdapter adapter = new GameStartAdapter(getContext(),gameList);
                        recyclerView.setAdapter(adapter);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
                        recyclerView.setLayoutManager(gridLayoutManager);
                        adapter.setOnItemClickListener(new GameStartAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent intent = new Intent(getContext(), GameActivity.class);
                                intent.putExtra("gid",gameList.get(position).gid);
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
}
