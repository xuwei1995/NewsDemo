package com.example.xuwei.mynewsdemo.adapter;

import android.content.Context;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.example.xuwei.mynewsdemo.News;
import com.example.xuwei.mynewsdemo.R;


import java.util.List;


import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xuwei on 17/4/10.
 * Email : 759974029@qq.com.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<News> mListString;
    private static final int HIGHT_TYPE = 0;
    private static final int LOW_TYPE = 1;

    public RecyclerViewAdapter(Context context, List<News> listString) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mListString = listString;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

            view = mLayoutInflater.inflate(R.layout.itemh_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        News news = mListString.get(position);
        holder.title.setText(mListString.get(position).getTitle());
        holder.summary.setText(mListString.get(position).getSummary());
        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"点击了第"+position+"张图片",Toast.LENGTH_SHORT).show();
            }
        });
        Glide.with(mContext).load(mListString.get(position).getImageUrl())
                .asBitmap()
                .centerCrop()
                .error(R.drawable.error)
                .placeholder(R.drawable.timg)
             .into(holder.iv);

    }

    @Override
    public int getItemCount() {
        return mListString.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv)
        ImageView iv;
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.summary)
        TextView summary;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }

    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 ==0 ){
            return HIGHT_TYPE;
        }else {
            return LOW_TYPE;
        }
    }
}
