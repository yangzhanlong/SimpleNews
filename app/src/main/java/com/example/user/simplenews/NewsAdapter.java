package com.example.user.simplenews;


import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

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
        View view = convertView;

        if (view == null) {
            view = View.inflate(mContext, R.layout.item, null);
        }

        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        TextView tv_des = (TextView) view.findViewById(R.id.tv_desc);

        News news = (News) getItem(position);
        tv_title.setText(news.getTitle());
        tv_des.setText(news.getDescription());

        String imgurl = news.getImgurl();
        Uri uri = Uri.parse(imgurl);
        SimpleDraweeView draweeView = (SimpleDraweeView) view.findViewById(R.id.iv_icon);
        draweeView.setImageURI(uri);

        return view;
    }
}
