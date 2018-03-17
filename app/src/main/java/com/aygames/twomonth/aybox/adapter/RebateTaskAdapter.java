package com.aygames.twomonth.aybox.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.aygames.twomonth.aybox.R;
import com.aygames.twomonth.aybox.activity.RebateOrderActivity;
import com.aygames.twomonth.aybox.bean.MessageEvent;
import com.aygames.twomonth.aybox.bean.RebateTask;
import com.aygames.twomonth.aybox.fragment.Rebate_Fragment;
import com.aygames.twomonth.aybox.utils.Constans;

import com.aygames.twomonth.aybox.utils.Logger;
import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;


import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/3/14.
 */

public class RebateTaskAdapter extends RecyclerView.Adapter<RebateTaskAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<RebateTask> arrayList;
    private LayoutInflater mInflater;
    private TextView et_smallcode_name ,

    et_gamename ,
    et_service ,
    et_heroname ,
    et_money;
    private Dialog dialog;

    private Button button;

    public RebateTaskAdapter(Context context , ArrayList<RebateTask> datas) {
        this.context = context;
        this.arrayList = datas;
        mInflater = LayoutInflater.from(context);
    }

    @Override

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == 1){
            View view = mInflater.inflate(R.layout.item_rebate_nook,parent,false);
            MyViewHolder viewHolder = new MyViewHolder(view);
            return viewHolder;
        }else if (viewType == 0){
            View view = mInflater.inflate(R.layout.item_rebate_task,parent,false);
            MyViewHolder viewHolder = new MyViewHolder(view);
            return viewHolder;
        }else {
            return  null;
        }
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.tv_gamename.setText(arrayList.get(position).gamename);
        holder.tv_amount.setText("已充值："+arrayList.get(position).amount+" ¥");
        holder.tv_diff.setText("还差："+arrayList.get(position).diff_amount+" ¥");
        Glide.with(context)
                .load(arrayList.get(position).icon_url)
                .error(R.mipmap.logo)
                .into(holder.icon);
        holder.bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context,RebateOrderActivity.class);
//                intent.putExtra("nickname",arrayList.get(position).nickname);
//                intent.putExtra("gamename",arrayList.get(position).gamename);
//                intent.putExtra("server",arrayList.get(position).server);
//                intent.putExtra("heroname",arrayList.get(position).heroname);
//                intent.putExtra("amount",arrayList.get(position).amount);
//                intent.putExtra("florderid",arrayList.get(position).fl_orderid);
//                context.startActivity(intent);

                dialog = new Dialog(context);
                View dialogView = mInflater.inflate(R.layout.activity_rebate_order,null);
                dialog.addContentView(dialogView,
                        new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                dialog.show();
                et_smallcode_name = dialogView.findViewById(R.id.et_smallcode_name);
                et_gamename = dialogView.findViewById(R.id.et_gamename);
                et_service = dialogView.findViewById(R.id.et_service);
                et_heroname = dialogView.findViewById(R.id.et_heroname);
                et_money = dialogView.findViewById(R.id.et_money);
                button = dialogView.findViewById(R.id.bt_submit);

                et_smallcode_name.setText(arrayList.get(position).nickname);
                et_gamename.setText(arrayList.get(position).gamename);
                et_service.setText(arrayList.get(position).server);
                et_heroname.setText(arrayList.get(position).heroname);
                et_money.setText(arrayList.get(position).amount);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //发送返利订单
                        postRebateOrder(position);
                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return arrayList.get(position).code;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView icon;
        TextView tv_gamename;
        TextView tv_amount;
        TextView tv_diff;
        Button bt_ok;

        public MyViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.iv_icon_rebate);
            tv_gamename = itemView.findViewById(R.id.tv_name_rebate);
            tv_amount = itemView.findViewById(R.id.tv_rebate_amount);
            tv_diff = itemView.findViewById(R.id.tv_rebate_diff_amount);
            bt_ok = itemView.findViewById(R.id.bt_rebate_ok);
        }
    }

    private void postRebateOrder(final int position){

        new Thread(){
            @Override
            public void run() {

                try {
                    Response response = OkGo.get(Constans.URL_REBATEORDER + "server/"+et_service.getText().toString()
                            +"/heroname/"+et_heroname.getText().toString()
                            +"/florderid/"+arrayList.get(position).fl_orderid).execute();
                    String code = response.body().string();
                        dialog.dismiss();
                        EventBus.getDefault().post(new MessageEvent(code));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

}
