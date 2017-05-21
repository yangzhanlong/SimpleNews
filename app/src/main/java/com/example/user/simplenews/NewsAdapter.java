package com.example.user.simplenews;


import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import static com.example.user.simplenews.R.id.tv_title;

public class NewsAdapter extends BaseAdapter{
    private Context mContext;
    private ArrayList<News> mList;

    public NewsAdapter(Context context, ArrayList<News> list) {
        mContext = context;
        mList = list;
    }

    @Override

    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item, null);
            holder = new ViewHolder();
            holder.tv_title = (TextView) convertView.findViewById(tv_title);
            holder.tv_des = (TextView) convertView.findViewById(R.id.tv_desc);
            holder.draweeView = (SimpleDraweeView) convertView.findViewById(R.id.iv_icon);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        News news = (News) getItem(position);
        holder.tv_title.setText(news.getTitle());
        holder.tv_des.setText(news.getDescription());

        String imgurl = news.getImgurl();
        Uri uri = Uri.parse(imgurl);
        holder.draweeView.setImageURI(uri);

        return convertView;
    }

    static class ViewHolder {
        TextView tv_title;
        TextView tv_des;
        SimpleDraweeView draweeView;
    }
}
