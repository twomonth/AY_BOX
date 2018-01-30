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
import com.aygames.twomonth.aybox.bean.GiftItem;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/29.
 */

public class GiftOneBaseAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<GiftItem> arrayList;

    public GiftOneBaseAdapter(Context context, ArrayList<GiftItem> arrayList) {
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
            convertView = inflater.inflate(R.layout.item_gift_one, null);
            holder = new ViewHolder();
            holder.tv_giftname = convertView.findViewById(R.id.tv_giftname);
            holder.tv_giftinfo = convertView.findViewById(R.id.tv_giftinfo);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_giftname.setText(arrayList.get(position).giftname);
        holder.tv_giftinfo.setText(arrayList.get(position).giftinfo);
        return convertView;
    }

    class ViewHolder {
        private TextView tv_giftname;
        private TextView tv_giftinfo;
    }
}
