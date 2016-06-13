package com.fire.zhaowei.launchertest.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ImageView;

import com.fire.zhaowei.launchertest.R;
import com.fire.zhaowei.launchertest.bean.ImageViewBean;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by zhaowei on 16/5/19.
 * 设置图片
 */
public class ImageViewLoaderUtils {
    private static final String TAG = "image_loader_task";
    public static ArrayList<ImageView> sImageTask = new ArrayList<>();

    private static int mThreadNum = 3;
    private static int mRunningThreadNum = 0;
    public int getmThreadNum() {
        return mThreadNum;
    }

    public void setmThreadNum(int mThreadNum) {
        this.mThreadNum = mThreadNum;
    }

    private static Handler sHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case ImageViewLoaderRunnable.SET_IMAGE_VIEW:
                    ImageViewBean ivb = (ImageViewBean) msg.obj;
                    ivb.getmImageView().setImageBitmap(ivb.getmBitmap());
                    break;
            }
        }
    };
    public static void setImageView(Context context, ImageView imageView, String url){
        Bitmap btp = ImageLocalCacheUtils.getImageLocalCache(context, url);
        if (btp != null){
            imageView.setImageBitmap(btp);
            return;
        } else {
            imageView.setTag(url);
            imageView.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher));
            synchronized (sImageTask){
                sImageTask.add(imageView);
            }
            if (mRunningThreadNum < mThreadNum){
                mRunningThreadNum++;
                TaskUtils.executeTask(TAG, new ImageViewLoaderRunnable(context, sHandler));
//                new Thread(new ImageViewLoaderRunnable(context, sHandler)).start();
            }
        }

//        String fileName = getFileName(context, url);
//        File dirFile = new File(fileName);
//        if(dirFile.exists()){
//            imageView.setImageBitmap(BitmapFactory.decodeFile(fileName));
//            return;
//        } else {
//            imageView.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher));
//        }
//        new Thread(new ImageViewLoaderRunnable(sHandler, imageView, url, fileName)).start();
    }
}
