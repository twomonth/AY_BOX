package com.aygames.twomonth.aybox.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.aygames.twomonth.aybox.R;
import com.aygames.twomonth.aybox.adapter.GameAllAdapter;
import com.aygames.twomonth.aybox.bean.Game;
import com.aygames.twomonth.aybox.utils.Constans;
import com.aygames.twomonth.aybox.utils.Logger;
import com.lzy.okgo.OkGo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Response;

public class ManVActivity extends AppCompatActivity {

    private ImageView iv_manv_back;
    private ArrayList<Game> arrayList_manv;
    private RecyclerView recyclerView_manv;
    private GameAllAdapter adapter_manv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_v);

        initView();
        initData();

        iv_manv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initData() {
        new Thread(){
            @Override
            public void run() {
                try {
                    Response response = OkGo.get(Constans.URL_MANV).execute();
                    JSONArray jsonArray = new JSONArray(response.body().string());
                    Logger.msg("满v游戏列表"+jsonArray.toString());
                    arrayList_manv = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject_manv = jsonArray.getJSONObject(i);
                        Game game = new Game(
                                jsonObject_manv.getString("ico_url")
                                ,jsonObject_manv.getString("app_name_cn")
                                ,jsonObject_manv.getString("gid")
                                ,jsonObject_manv.getString("game_size")
                                ,jsonObject_manv.getString("app_type")
                                ,jsonObject_manv.getString("publicity")
                        );
                        arrayList_manv.add(game);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter_manv = new GameAllAdapter(getApplicationContext(),arrayList_manv);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ManVActivity.this,LinearLayoutManager.VERTICAL,false);
                        recyclerView_manv.setLayoutManager(linearLayoutManager);
                        recyclerView_manv.setAdapter(adapter_manv);

                        adapter_manv.setOnItemClickListener(new GameAllAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Toast.makeText(ManVActivity.this,arrayList_manv.get(position).app_name_cn,Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ManVActivity.this, GameActivity.class);
                                intent.putExtra("gid",arrayList_manv.get(position).gid);
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

    private void initView() {
        iv_manv_back =  findViewById(R.id.iv_manv_back);
        recyclerView_manv = findViewById(R.id.recycle_manv);
    }
}
