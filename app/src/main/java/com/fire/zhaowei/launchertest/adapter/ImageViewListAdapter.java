package com.fire.zhaowei.launchertest.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fire.zhaowei.launchertest.utils.ImageViewLoaderUtils;

import java.util.ArrayList;

/**
 * Created by zhaowei on 16/5/20.
 */
public class ImageViewListAdapter extends RecyclerView.Adapter<ImageViewListAdapter.MyViewHolder> {
    private ArrayList<String> data;
    private Context context;

    public ImageViewListAdapter(Context context, ArrayList<String> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(context, new ImageView(context));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ((MyViewHolder)holder).setImageBitmap(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        private Context context;
        private ImageView imageView;
        public MyViewHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            this.imageView = (ImageView) itemView;
        }
        public void setImageBitmap(String url){
            ImageViewLoaderUtils.setImageView(context, imageView, url);
        }
    }
}
