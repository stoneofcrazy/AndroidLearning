package com.fire.zhaowei.launchertest.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TextView;

import com.fire.zhaowei.launchertest.R;

import java.util.ArrayList;

/**
 * Created by zhaowei on 16/5/31.
 */
public class DetailButtonItemViews extends AbsViewGroup {
    private Context context;
    private TextView[] mDiversityBtns;
    private int mColumn;
    private int mSize;

    private int mDiversityWidth;
    private int mDiversityHeight;
    private int mPaddingLeft;
    private int mPaddingTop;
    private int mIntervalWidth;

    private Rect[] mDiversityRects;
    private static final int HAS_LOOKING = 0;
    private static final int LOCK = 1;
    private static final int NOT_LOOKING = 2;
    /**
     * 一行View, size < column
     * @param context
     * @param column
     * @param size
     */
    public DetailButtonItemViews(Context context, int column, int size) {
        super(context);
        this.context = context;
        this.mColumn = column;
        this.mSize = size;
        initThisView();
    }

    private void initThisView() {
        if(mDiversityBtns == null){
            mDiversityBtns = new TextView[mSize];
        }
        for (int i = 0; i < mSize; i++) {
            mDiversityBtns[i] = new TextView(context);
            mDiversityBtns[i].setGravity(Gravity.CENTER);
            addView(mDiversityBtns[i]);
            setViewBackground(i, NOT_LOOKING);
        }

        Resources res = context.getResources();
        mPaddingLeft = (int) res.getDimension(R.dimen.mission_item_cover_padding_top);
        mPaddingTop = (int) res.getDimension(R.dimen.mission_item_cover_padding_top);
        mIntervalWidth = (int) res.getDimension(R.dimen.mission_item_cover_padding_top);

        if(mColumn > 0){
            mDiversityWidth = (mViewWidth - 2 * mPaddingLeft - (mColumn-1) * mIntervalWidth) / mColumn;
        } else {
            mDiversityWidth = mViewWidth - 2 * mPaddingLeft;
        }
        mDiversityHeight = (int) res.getDimension(R.dimen.mission_item_cover_padding_top) * 3;

        mDiversityRects = new Rect[mSize];
        for (int i = 0; i < mSize; i++) {
            mDiversityRects[i] = new Rect();
        }
    }

    @Override
    public void initView(Context context) {
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
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mViewHeight==0 || mViewWidth==0){
            for (int i = 0; i < mSize; i++) {
                computeDiversitySize(i);
                computeDiversityRect(i);
                mViewHeight = mPaddingTop * 2 + mDiversityHeight;
            }
        }
        setMeasuredDimension(mViewWidth, mViewHeight);
    }

    private void computeDiversityRect(int index) {
        if (index == 0){
            mDiversityRects[index].left = mPaddingLeft;
        } else {
            mDiversityRects[index].left = mDiversityRects[index-1].right + mIntervalWidth;
        }
        mDiversityRects[index].right = mDiversityRects[index].left + mDiversityWidth;
        mDiversityRects[index].top = mPaddingTop;
        mDiversityRects[index].bottom = mDiversityRects[index].top + mDiversityHeight;
    }

    private void computeDiversitySize(int index) {
        mDiversityBtns[index].measure(MeasureSpec.makeMeasureSpec(mDiversityWidth, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(mDiversityHeight, MeasureSpec.EXACTLY));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < mSize; i++) {
            mDiversityBtns[i].layout(mDiversityRects[i].left, mDiversityRects[i].top, mDiversityRects[i].right, mDiversityRects[i].bottom);
        }
    }

    public void setViewBackground(int index, int bgType) {
        switch (bgType){
            case LOCK:
                mDiversityBtns[index].setText("");
                mDiversityBtns[index].setBackgroundDrawable(context.getResources().getDrawable(R.drawable.locklooking));
                break;
            case HAS_LOOKING:
                mDiversityBtns[index].setBackgroundDrawable(context.getResources().getDrawable(R.drawable.haslooking));
                break;
            case NOT_LOOKING:
                mDiversityBtns[index].setBackgroundDrawable(context.getResources().getDrawable(R.drawable.notlooking));
                break;
        }
    }
    public void setRowsAllDiversityData(ArrayList<String> data){
        for (int i = 0; i < mDiversityBtns.length; i++) {
            mDiversityBtns[i].setText(data.get(i));
        }
    }
}
