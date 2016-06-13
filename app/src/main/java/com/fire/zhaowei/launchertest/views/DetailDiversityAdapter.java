package com.fire.zhaowei.launchertest.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by zhaowei on 16/5/31.
 */
public class DetailDiversityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private int mType;
    private ArrayList<Object> data;
    public DetailDiversityAdapter(Context context, int type) {
        this.context = context;
        this.mType = type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        if (mType == DetailDiversityView.BUTTON_TYPE){
            return new MyButtonHolder(new TextView(context));
//        }
//        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        if (mType == DetailDiversityView.BUTTON_TYPE){
            ((MyButtonHolder)holder).setTextContent((String)data.get(position));
//        }
    }

    public void setData(ArrayList<Object> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public ArrayList<Object> getData() {
        return data;
    }

    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        } else {
            return data.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mType;
    }

    class MyButtonHolder extends RecyclerView.ViewHolder {
        private TextView tv;
        public MyButtonHolder(final View itemView) {
            super(itemView);
            tv = (TextView) itemView;
        }
        public void setTextContent(String str){
            tv.setText(str);
        }
    }
}
