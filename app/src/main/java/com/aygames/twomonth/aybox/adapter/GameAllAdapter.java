package com.aygames.twomonth.aybox.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aygames.twomonth.aybox.R;
import com.aygames.twomonth.aybox.bean.Game;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/21.
 */

public class GameAllAdapter extends RecyclerView.Adapter<GameAllAdapter.MyViewHolder> {

    private LayoutInflater mInflater;
    private Context context;
    private ArrayList<Game> list;
    /**
     * 点击事件实现
     */
    public interface OnItemClickListener{
        void onItemClick(View view , int position);
        void onItemLongClick(View view , int position);
    }
    private OnItemClickListener mOnItemClickListener;
    public void setOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener = listener;
    }
    public GameAllAdapter(Context context , ArrayList<Game> datas) {
        this.context = context;
        this.list =  datas;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_game_all,parent,false);

        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tv_name.setText(list.get(position).app_name_cn);
        holder.tv_type.setText(list.get(position).app_type);
        holder.tv_size.setText(list.get(position).game_size);
        holder.tv_publicty.setText(list.get(position).publicty);
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
        TextView tv_size;
        TextView tv_publicty;
        public MyViewHolder(View itemView) {
            super(itemView);
            iv_icon = itemView.findViewById(R.id.iv_icon_all);
            tv_name = itemView.findViewById(R.id.tv_name_all);
            tv_type = itemView.findViewById(R.id.tv_type_all);
            tv_size = itemView.findViewById(R.id.tv_size_all);
            tv_publicty = itemView.findViewById(R.id.tv_publicty_all);
        }
    }
}
