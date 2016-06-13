package com.fire.zhaowei.launchertest.utils;

/**
 * Created by zhaowei on 16/6/12.
 */
public abstract class AbstractTask implements Runnable {
    private boolean mIsCanceled;
    private boolean mIsRunning;

    public AbstractTask() {
        this.mIsCanceled = false;
        this.mIsRunning = false;
    }

    protected void onError(Exception e) { }
    protected void onEnd() { }

    protected abstract void work();

    public boolean isIsCanceled() {
        return mIsCanceled;
    }

    public void setIsCanceled(boolean mIsCanceled) {
        this.mIsCanceled = mIsCanceled;
    }

    public boolean isIsRunning() {
        return mIsRunning;
    }

    public void setIsRunning(boolean mIsRunning) {
        this.mIsRunning = mIsRunning;
    }

    @Override
    public void run() {
        if (mIsCanceled) return;
        try {
            mIsRunning = true;
            work();
            mIsRunning = false;
            onEnd();
        } catch (Exception e){
            onError(e);
        }
    }
}
