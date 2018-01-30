package com.aygames.twomonth.aybox.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aygames.twomonth.aybox.R;
import com.aygames.twomonth.aybox.bean.Game;
import com.aygames.twomonth.aybox.utils.Logger;
import com.aygames.twomonth.aybox.utils.Util;
import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Administrator on 2018/1/21.
 */

public class GameStartAdapter extends RecyclerView.Adapter<GameStartAdapter.MyViewHolder> {

    private LayoutInflater mInflater;
    private Context context;
    private ArrayList<Game> list;
    /**
     * 点击事件实现
     */
    public interface OnItemClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }
    private OnItemClickListener mOnItemClickListener;
    public void setOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener = listener;
    }
    public GameStartAdapter(Context context , ArrayList<Game> datas) {
        this.context = context;
        this.list =  datas;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 2){
            View view = mInflater.inflate(R.layout.item_game_start,parent,false);
            MyViewHolder viewHolder = new MyViewHolder(view);
            return viewHolder;
        }else {
            View view = mInflater.inflate(R.layout.item_game_start_time,parent,false);
            MyViewHolder viewHolder = new MyViewHolder(view);
            return viewHolder;
        }

    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).type;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        //内部时间
        String time = list.get(position).time;
        //当前时间 day
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
//        Logger.msg("天天："+day+"vvv"+Util.getStrDay(time));
        holder.tv_name.setText(list.get(position).app_name_cn);
        if (list.get(position).app_type.equals(null)){
            holder.tv_type.setText("**");
        }else {
            holder.tv_type.setText(list.get(position).app_type);
        }
        holder.tv_service.setText(list.get(position).service);


        if (day == Util.getStrDay(time)){
            holder.tv_time.setTextColor(Color.parseColor("#07ae76"));
            holder.tv_time.setText(Util.getStrTime(time));
        }else {
            holder.tv_time.setTextColor(Color.parseColor("#ffa10a"));
            holder.tv_time.setText(Util.getStrTimeString(time));
        }
//        holder.tv_time.setText(Util.getStrTime(time));

        Glide.with(context)
                .load(list.get(position).ico_url)
                .error(R.mipmap.logo)
                .into(holder.iv_icon);
        //绑定监听
        if (mOnItemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.itemView,position);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickListener.onItemLongClick(holder.itemView,position);
                    return false;
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_icon;
        TextView tv_name;
        TextView tv_type;
        TextView tv_service;
        TextView tv_time;
        public MyViewHolder(View itemView) {
            super(itemView);
            iv_icon = itemView.findViewById(R.id.iv_icon_start);
            tv_name = itemView.findViewById(R.id.tv_name_start);
            tv_type = itemView.findViewById(R.id.tv_type_start);
            tv_service = itemView.findViewById(R.id.tv_service_start);
            tv_time = itemView.findViewById(R.id.tv_time_start);
        }
    }
}
