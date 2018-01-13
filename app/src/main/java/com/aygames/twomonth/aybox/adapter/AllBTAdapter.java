package com.aygames.twomonth.aybox.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aygames.twomonth.aybox.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/8.
 */

public class AllBTAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Map<String, Object>> arraylist = null;

    public AllBTAdapter(Context applicationContext, ArrayList<Map<String, Object>> arraylist) {
        this.context = applicationContext;
        this.arraylist = arraylist;
    }

    @Override
    public int getCount() {
        return arraylist.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_adapter,null);
            holder.img =  convertView.findViewById(R.id.iv_all_bt_icon);
            holder.title = convertView.findViewById(R.id.tv_all_bt_gamename);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
//        holder.img.setImageResource((Integer) arraylist.get(position).get("image"));
        Glide.with(context).load(arraylist.get(position).get("image")).error(R.mipmap.logo).into(holder.img);
        holder.title.setText(arraylist.get(position).get("title").toString());
        return convertView;
    }
    //在外面先定义，ViewHolder静态类
    static class ViewHolder
    {
        public ImageView img;
        public TextView title;
    }
}
