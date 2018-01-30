package com.aygames.twomonth.aybox.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aygames.twomonth.aybox.R;
import com.aygames.twomonth.aybox.bean.Gift;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/29.
 */

public class GiftBaseAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Gift> arrayList;

    public GiftBaseAdapter(Context context, ArrayList<Gift> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_game_gift, null);
            holder = new ViewHolder();
            holder.tv_title_gift = convertView.findViewById(R.id.tv_title_gift);
            holder.tv_count_gift = convertView.findViewById(R.id.tv_count_gift);
            holder.iv_picture_gift = convertView.findViewById(R.id.iv_picture_gift);
            holder.tv_publicity_gift = convertView.findViewById(R.id.tv_publicity_gift);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_title_gift.setText(arrayList.get(position).app_name_cn);
        holder.tv_count_gift.setText(arrayList.get(position).lbcount+"个");
        holder.tv_publicity_gift.setText(arrayList.get(position).publicity);
        Glide.with(context)
                .load(arrayList.get(position).ico_url)
                .error(R.mipmap.logo)
                .into(holder.iv_picture_gift);
        return convertView;
    }

    class ViewHolder {
        private TextView tv_title_gift;
        private ImageView iv_picture_gift;
        private TextView tv_publicity_gift;
        private TextView tv_count_gift;
    }
}
