package com.aygames.twomonth.aybox.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aygames.twomonth.aybox.R;
import com.aygames.twomonth.aybox.adapter.RebateTaskAdapter;
import com.aygames.twomonth.aybox.bean.MessageEvent;
import com.aygames.twomonth.aybox.bean.RebateTask;
import com.aygames.twomonth.aybox.service.AyboxService;
import com.aygames.twomonth.aybox.utils.Constans;
import com.aygames.twomonth.aybox.utils.Logger;
import com.lzy.okgo.OkGo;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


import okhttp3.Response;

/**
 * Created by Administrator on 2018/2/8.
 */

public class Rebate_Fragment extends Fragment {

    private View view;
    private RebateTaskAdapter rebateTaskAdapter;
    RecyclerView recyclerView;
    private ArrayList<RebateTask> arrayList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_rebate,null);
        recyclerView = view.findViewById(R.id.recycle_rebate_task);

        initData();

        return view;
    }

    private void initData() {
        arrayList = new ArrayList<>();
        new Thread(){
            @Override
            public void run() {
                try {
                    Response response = OkGo.get(Constans.URL_FANLITASK + AyboxService.passport).execute();
                    JSONArray jsonArray = new JSONArray(response.body().string());
                    Logger.msg("返利任务"+jsonArray);
                    arrayList = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        RebateTask rebateTask = new RebateTask();
                        rebateTask.amount = jsonObject.getString("amount");//充值金额
                        rebateTask.code = jsonObject.getInt("code");//标识
                        rebateTask.diff_amount = jsonObject.getString("diff_amount");//还差多少钱
                        rebateTask.gameid = jsonObject.getString("gameid"); // 游戏gid
                        rebateTask.icon_url = jsonObject.getString("ico_url"); // 游戏icon
                        rebateTask.server = jsonObject.getString("server"); //区服
                        rebateTask.pay_id = jsonObject.getString("pay_id"); // 订单号
                        rebateTask.roleid = jsonObject.getString("roleid");
                        rebateTask.heroname = jsonObject.getString("heroname");//角色名称
                        rebateTask.nickname = jsonObject.getString("nickname"); //小号昵称
                        rebateTask.username = jsonObject.getString("username"); //小号哦id
                        rebateTask.gamename = jsonObject.getString("gamename"); //游戏名称
                        rebateTask.fl_orderid = jsonObject.getString("fl_orderid");//返利订单好
                        arrayList.add(rebateTask);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        rebateTaskAdapter = new RebateTaskAdapter(getActivity(),arrayList);
                        recyclerView.setAdapter(rebateTaskAdapter);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
                        recyclerView.setLayoutManager(linearLayoutManager);
                    }
                });
            }
        }.start();

    }

}
