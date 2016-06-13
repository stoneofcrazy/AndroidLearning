package com.fire.zhaowei.launchertest.views;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;


import java.util.ArrayList;

/**
 * Created by zhaowei on 16/5/31.
 */
public class DetailDiversityView extends AbsViewGroup {
    private int mDiversityType;
    private RecyclerView mRecycView;
    private DetailDiversityAdapter mAdapter;
    private ArrayList<Object> data;
    private Rect mRecycRect = new Rect();
    private int mRecycWidth;
    private int mRecycHeight;

    public static final int BUTTON_TYPE = 0;
    public DetailDiversityView(Context context){
        this(context, BUTTON_TYPE);
    }

    public DetailDiversityView(Context context, AttributeSet attr) {
        this(context, attr, BUTTON_TYPE);
    }

    public DetailDiversityView(Context context, int type) {
        super(context, null);
        this.mDiversityType = type;
    }

    public DetailDiversityView(Context context, AttributeSet attr, int type) {
        super(context, attr);
        this.mDiversityType = type;
    }

    @Override
    public void initView(Context context) {
        mRecycView = new RecyclerView(context);
        if (mDiversityType == BUTTON_TYPE){
            mRecycView.setLayoutManager(new GridLayoutManager(context, 5));
        }
        mAdapter = new DetailDiversityAdapter(context, BUTTON_TYPE);
        mRecycView.setAdapter(mAdapter);
        addView(mRecycView);
    }

    @Override
    public void initSize(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        mViewWidth = metrics.widthPixels;
    }

    @Override
    public void initPadding(Context context) {

    }

    @Override
    public void initRect(Context context) {
        mRecycRect = new Rect();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mViewHeight==0||mViewWidth==0){
            computeRecycSize();
            computeRecycRect();
            mViewWidth = mRecycWidth;
            mViewHeight = mRecycHeight;
            Log.e("zhaowei", "onM");
        }
        measureExactly(mRecycView, mRecycWidth, mRecycHeight);
        setMeasuredDimension(mViewWidth, mViewHeight);
    }

    private void computeRecycRect() {
        mRecycRect.top = 0;
        mRecycRect.left = 0;
        mRecycRect.bottom = mRecycRect.top + mRecycHeight;
        mRecycRect.right = mRecycRect.left + mRecycWidth;
    }

    private void computeRecycSize() {
        mRecycView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        mRecycWidth = mRecycView.getMeasuredWidth();
        mRecycHeight = mRecycView.getMeasuredHeight();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mRecycView.layout(mRecycRect.left, mRecycRect.top, mRecycRect.right, mRecycRect.bottom);
    }

    public void setData(ArrayList<Object> data) {
        this.data = data;
        mAdapter.setData(data);
        Log.e("zhaowei", "setD");
        reMeasure();
    }

    public int getmDiversityType() {
        return mDiversityType;
    }

    public void setmDiversityType(int mDiversityType) {
        this.mDiversityType = mDiversityType;
    }

}
