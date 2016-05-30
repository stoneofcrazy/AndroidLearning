package com.fire.zhaowei.launchertest.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.fire.zhaowei.launchertest.my_interface.MyItemOnClickListener;

import java.util.ArrayList;

/**
 * Created by zhaowei on 16/5/10.
 */
public class TestRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    protected int mStatea;
    private ArrayList<String> data;
    public int ERROR = 0x01;
    public int BUTTON_TYPE = 0x02;
    public int EMPTY = 0x04;
    private MyItemOnClickListener listener;


    public TestRecyclerViewAdapter(Context context, ArrayList<String> data, MyItemOnClickListener listener){
        this.context = context;
        this.data = data;
        this.listener = listener;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ERROR){
            return new MyErrorHolder(new TextView(context), "错误信息！！");
        } else if (viewType == BUTTON_TYPE){
            return new MyButtonHolder(new Button(context));
        }  else {
            return new MyEmptyHolder(new TextView(context));
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        int state = getItemViewType(position);
        if (state == BUTTON_TYPE){
            ((MyButtonHolder)holder).setTextContent(data.get(position));
            ((MyButtonHolder) holder).tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClickListener(((MyButtonHolder)holder).tv, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data == null||data.size()==0?1:data.size();
    }

    public int getmState() {
        return mStatea;
    }

    public void setmState(int mState) {
        this.mStatea = mState;
    }

    @Override
    public int getItemViewType(int position) {
        if (data == null||data.size() == 0){
            return mStatea;
        } else {
            return BUTTON_TYPE;
        }
    }

    class MyButtonHolder extends RecyclerView.ViewHolder {
        private Button tv;
        public MyButtonHolder(final View itemView) {
            super(itemView);
            tv = (Button) itemView;
        }
        public void setTextContent(String str){
            tv.setText(str);
        }
    }
    class MyEmptyHolder extends RecyclerView.ViewHolder {
        public MyEmptyHolder(View itemView) {
            super(itemView);
            ((TextView)itemView).setText("空字符串");
        }
    }
    class MyErrorHolder extends RecyclerView.ViewHolder {
        public MyErrorHolder(View itemView, String str) {
            super(itemView);
            ((TextView)itemView).setText(str);
        }
    }
}
