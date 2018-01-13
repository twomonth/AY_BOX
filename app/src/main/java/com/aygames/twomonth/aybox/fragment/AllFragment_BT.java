package com.aygames.twomonth.aybox.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.view.menu.MenuAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.aygames.twomonth.aybox.R;
import com.aygames.twomonth.aybox.adapter.AllBTAdapter;
import com.lzy.okhttputils.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import okhttp3.Response;

/**
 * Created by Administrator on 2017/12/8.
 */

public class AllFragment_BT extends Fragment {
    private View view;
    private ListView lv_all_bt;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            lv_all_bt.setAdapter(new AllBTAdapter(getActivity().getApplicationContext(),arraylist));
        }
    };
    private ArrayList<Map<String , Object>> arraylist = null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_all_bt,null,false);

        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    Response response = OkHttpUtils.get("http://192.168.1.125:8080/game.json").execute();

                    JSONArray jsonArray = new JSONArray(response.body().string());
                    Log.i("jsonarray",jsonArray.toString());
                    arraylist = new ArrayList<Map<String, Object>>();
                    for (int i=0;i<jsonArray.length();i++){
                        Map map = new HashMap();
                        map.put("image",jsonArray.getJSONObject(i).getString("icom"));
                        map.put("title",jsonArray.getJSONObject(i).getString("gamename"));
                        arraylist.add(map);
                    }
                    Message message = new Message();
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        lv_all_bt = view.findViewById(R.id.lv_all_bt);
//        lv_all_bt.setAdapter(new AllBTAdapter());
        return view;
    }
}
