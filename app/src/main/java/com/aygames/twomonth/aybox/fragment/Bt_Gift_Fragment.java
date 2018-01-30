package com.aygames.twomonth.aybox.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.aygames.twomonth.aybox.R;
import com.aygames.twomonth.aybox.adapter.GiftBaseAdapter;
import com.aygames.twomonth.aybox.bean.Gift;
import com.aygames.twomonth.aybox.utils.Logger;
import com.lzy.okgo.OkGo;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Response;

/**
 * Created by Administrator on 2018/1/18.
 */

public class Bt_Gift_Fragment extends Fragment {
    private View view ;
    private Button bt_gift_center,bt_gift_new;
    private ListView lv_gift;
    private ArrayList<Gift> giftArrayList;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bt_gift, null);

        initView();
        initData();

        bt_gift_center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bt_gift_center.setBackgroundResource(R.drawable.bt_back_gift_left);
                bt_gift_new.setBackgroundResource(R.drawable.bt_back_gift_right_no);
            }
        });

        bt_gift_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bt_gift_center.setBackgroundResource(R.drawable.bt_back_gift_left_no);
                bt_gift_new.setBackgroundResource(R.drawable.bt_back_gift_right);
            }
        });




        return view;
    }

    private void initData() {
        new Thread(){
            @Override
            public void run() {
                giftArrayList = new ArrayList();
                try {
                    Response response = OkGo.get("http://sdk.aooyou.com/index.php/DataGames/getLibaolist").execute();
                    JSONArray jsonArray = new JSONArray(response.body().string());
                    Logger.msg("礼包——————"+jsonArray.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Gift gift = new Gift();
                        gift.app_name_cn = jsonArray.getJSONObject(i).getString("app_name_cn");
                        gift.ico_url = jsonArray.getJSONObject(i).getString("ico_url");
                        gift.gid = jsonArray.getJSONObject(i).getString("gid");
                        gift.lbcount = jsonArray.getJSONObject(i).getString("lbcount");
                        gift.publicity = jsonArray.getJSONObject(i).getString("publicity");
                        giftArrayList.add(gift);
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            GiftBaseAdapter giftBaseAdapter = new GiftBaseAdapter(getContext(),giftArrayList);
                            lv_gift.setAdapter(giftBaseAdapter);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void initView() {
        bt_gift_center = view.findViewById(R.id.bt_gift_center);
        bt_gift_new = view.findViewById(R.id.bt_gift_new);
        lv_gift = view.findViewById(R.id.lv_gift);

    }
}
