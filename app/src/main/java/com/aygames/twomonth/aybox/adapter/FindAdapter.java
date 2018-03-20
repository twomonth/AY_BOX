package com.aygames.twomonth.aybox.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aygames.twomonth.aybox.R;
import com.aygames.twomonth.aybox.bean.Game;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/21.
 */

public class FindAdapter extends RecyclerView.Adapter<FindAdapter.MyViewHolder> {

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

    /**
     * 构造方法
     * @param context
     * @param datas
     */
    public FindAdapter(Context context , ArrayList<Game> datas) {
        this.context = context;
        this.list =  datas;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_findadapter,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        ViewGroup.LayoutParams params=holder.relativeLayout_find.getLayoutParams();
        params.height= Integer.valueOf(list.get(position).app_type);
        holder.relativeLayout_find.setLayoutParams(params);

        holder.tv_find.setText(list.get(position).app_name_cn);
        holder.tv_message.setText(list.get(position).publicty);
        Glide.with(context)
                .load(list.get(position).ico_url)
                .error(R.mipmap.logo)
                .into(holder.iv_find);
        holder.relativeLayout_find.setBackgroundColor(Color.parseColor(list.get(position).game_size));
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
        RelativeLayout relativeLayout_find;
        ImageView iv_find;
        TextView tv_find;
        TextView tv_message;
        public MyViewHolder(View itemView) {
            super(itemView);
            relativeLayout_find = itemView.findViewById(R.id.relativeLayout_find);
            iv_find = itemView.findViewById(R.id.iv_find);
            tv_find = itemView.findViewById(R.id.tv_find);
            tv_message = itemView.findViewById(R.id.tv_message);
        }
    }



}
