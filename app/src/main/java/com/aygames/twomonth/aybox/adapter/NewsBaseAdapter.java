package com.aygames.twomonth.aybox.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aygames.twomonth.aybox.R;
import com.aygames.twomonth.aybox.bean.News;
import com.aygames.twomonth.aybox.utils.Util;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/29.
 */

public class NewsBaseAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<News> arrayList;

    public NewsBaseAdapter(Context context, ArrayList<News> arrayList) {
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
            convertView = inflater.inflate(R.layout.item_game_news, null);
            holder = new ViewHolder();
            holder.tv_title_news = convertView.findViewById(R.id.tv_title_news);
            holder.tv_time_news = convertView.findViewById(R.id.tv_time_news);
            holder.iv_picture_news = convertView.findViewById(R.id.iv_picture_news);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_title_news.setText(arrayList.get(position).newsTitle);
        holder.tv_time_news.setText(Util.getStrTimeString(arrayList.get(position).newsTime));
        Glide.with(context)
                .load(arrayList.get(position).newsPicture)
                .error(R.mipmap.logo)
                .into(holder.iv_picture_news);
        return convertView;
    }

    class ViewHolder {
        private TextView tv_title_news;
        private ImageView iv_picture_news;
        private TextView tv_time_news;
    }
}
