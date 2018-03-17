package com.aygames.twomonth.aybox.fragment;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.aygames.twomonth.aybox.R;
import com.aygames.twomonth.aybox.adapter.RebateTaskAdapter;
import com.aygames.twomonth.aybox.bean.RebateTask;
import com.aygames.twomonth.aybox.service.AyboxService;
import com.aygames.twomonth.aybox.utils.Constans;
import com.aygames.twomonth.aybox.utils.Logger;
import com.aygames.twomonth.aybox.utils.Util;
import com.lzy.okgo.OkGo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.Response;


/**
 * Created by Administrator on 2018/2/8.
 */

public class Rebate_Fragment_order extends Fragment {

    private View view;

    ListView recyclerView;
    private ArrayList<RebateTask> arrayList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_rebate_order,null);
        recyclerView = view.findViewById(R.id.recycle_rebate_order);
        initData();

        return view;
    }

    private void initData() {
        new Thread(){
            @Override
            public void run() {
                try {
                    Response response = OkGo.get(Constans.URL_FANLIORDER + AyboxService.passport).execute();
                    JSONArray jsonArray = new JSONArray(response.body().string());
                    Logger.msg("返利订单："+jsonArray.toString());
                    arrayList = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        RebateTask rebateTask = new RebateTask();
                        rebateTask.amount = jsonObject.getString("amount");//充值金额
                        rebateTask.code = jsonObject.getInt("status");//标识
                        rebateTask.gamename = jsonObject.getString("app_name_cn"); //游戏名称
                        rebateTask.diff_amount = jsonObject.getString("create_time");//时间戳
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
                        MyAdapter adapter = new MyAdapter(getActivity());
                        recyclerView.setAdapter(adapter);
                    }
                });
            }
        }.start();
    }

    class MyAdapter extends BaseAdapter{
        Context context;
        MyAdapter(Context context){
            this.context = context;
        }

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(context);
            MyHolder holder = null;
            if (convertView == null){
                convertView = inflater.inflate(R.layout.item_fanli_order, null);
                holder = new MyHolder();
                holder.tv_gamename_order =  convertView.findViewById(R.id.tv_gamename_order);
                holder.tv_time_order =  convertView.findViewById(R.id.tv_time_order);
                holder.tv_money_order =  convertView.findViewById(R.id.tv_money_order);
                holder.tv_code = convertView.findViewById(R.id.tv_code);
                convertView.setTag(holder);
            }    else {
                holder = (MyHolder) convertView.getTag();
            }
            holder.tv_gamename_order.setText(arrayList.get(position).gamename);
//            holder.tv_time_order.setText(sdf.format(new Date(Long.parseLong(String.valueOf(arrayList.get(position).diff_amount)))));
            holder.tv_time_order.setText(Util.getStrTimeString(arrayList.get(position).diff_amount));
            holder.tv_money_order.setText("返利金额："+arrayList.get(position).amount);
            if (arrayList.get(position).code == 0){
                holder.tv_code.setText("等待处理");
                holder.tv_code.setTextColor(Color.parseColor("#41ff07"));
            }else {
                holder.tv_code.setText("已处理");
            }
            return convertView;
        }
    }
    class MyHolder{
        TextView tv_gamename_order;
        TextView tv_time_order;
        TextView tv_money_order;
        TextView tv_code;

    }
}
