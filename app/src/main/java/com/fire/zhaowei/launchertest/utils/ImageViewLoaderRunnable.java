package com.fire.zhaowei.launchertest.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import com.fire.zhaowei.launchertest.bean.ImageViewBean;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by zhaowei on 16/5/19.
 * 下载图片并缓存到某个文件夹
 */
public class ImageViewLoaderRunnable implements Runnable {
    private Context context;
    public static final int SET_IMAGE_VIEW = 0x01;
    private static final int TIMEOUT = 5 * 1000;//超时时间
    private ImageView mImageView;
    private String mUrl;
    private String mFilePath;
    private Handler mHandler;

    public ImageViewLoaderRunnable(Context context, Handler sHandler) {
        this.context = context;
        this.mHandler = sHandler;
        synchronized (ImageViewLoaderUtils.sImageTask) {
            mImageView = ImageViewLoaderUtils.sImageTask.get(0);
            ImageViewLoaderUtils.sImageTask.remove(0);
        }
        this.mUrl = (String) mImageView.getTag();
        mFilePath = getFileName(context, mUrl);
    }

    @Override
    public void run() {
        try {
            int length;
            do {
                BitmapFactory.Options opt = new BitmapFactory.Options();
                opt.inSampleSize = 1;
                opt.inPurgeable = true;
                InputStream ins = getImageStream(mUrl);
                ImageViewBean ivb = new ImageViewBean();
                ivb.setmImageView(mImageView);
                Bitmap bm = BitmapFactory.decodeStream(ins, null, opt);
                ivb.setmBitmap(bm);
                Message msg = new Message();
                msg.what = SET_IMAGE_VIEW;
                msg.obj = ivb;
                mHandler.sendMessage(msg);
                SoftReference<Bitmap> bitmap_cache = new SoftReference<>(bm);
                if (ImageLocalCacheUtils.mImageMemoryCache.get(mUrl)==null) {
                    ImageLocalCacheUtils.mImageMemoryCache.put(mUrl, bitmap_cache);
                }
                saveFile(ivb.getmBitmap());

                ins.close();
                synchronized (ImageViewLoaderUtils.sImageTask) {
                    length = ImageViewLoaderUtils.sImageTask.size();
                    if (length > 0) {
                        mImageView = ImageViewLoaderUtils.sImageTask.get(0);
                        ImageViewLoaderUtils.sImageTask.remove(0);
                    }
                }
                if (length > 0){
                    this.mUrl = (String) mImageView.getTag();
                    mFilePath = getFileName(context, mUrl);
                }
            } while (length > 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveFile(Bitmap bitmap) throws Exception{
        File theFile = new File(mFilePath);
        if(!theFile.exists()){
            String dir = mFilePath.substring(0, mFilePath.lastIndexOf("/")+1);
            File dirFile = new File(dir);
            dirFile.mkdir();
        } else {
            return;
        }
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(theFile));
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
    }

    public InputStream getImageStream(String path) throws Exception{
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(TIMEOUT);
        conn.setRequestMethod("GET");
        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK)return conn.getInputStream();
        return null;
    }

    public static String getFileName(Context context, String url) {
        String result;
        String format = url.substring(url.lastIndexOf("."));
        String img_name = getPathEnCodesMD5(url) + format;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT){
                result = Environment.getExternalStorageDirectory() + "/Android/data/" + context.getPackageName() + "/cache/download_test/" + img_name;
            } else {
                File[] files = context.getExternalCacheDirs();
                if (files.length > 1) {
                    result = context.getExternalCacheDirs()[1] + "/download_test/" + img_name;
                } else {
                    result = context.getExternalCacheDirs()[0] + "/download_test/" + img_name;
                }
            }
        } else {
            result = context.getCacheDir().getPath() + "/Android/data/" + context.getPackageName() + "/cache/download_test/" + img_name;
        }
        return result;
    }

    public static String getPathEnCodesMD5(String str) {
        try {
            if (str == null) return "null";
            byte[] btInput = str.getBytes();
            MessageDigest mdInst = MessageDigest.getInstance("MD5");

            byte[] md = mdInst.digest(btInput);
            return md.toString();
        } catch (Exception e){
            Log.e("EnCodesMD5", e.getMessage().toString());
        }

        return "null";
    }

    public static Bitmap getBitmapFromUrl(Context context, String url){
        return BitmapFactory.decodeFile(getFileName(context, url));
    }
}
