package com.fire.zhaowei.launchertest.utils;

import android.content.Context;
import android.graphics.Bitmap;

import java.lang.ref.SoftReference;
import java.util.HashMap;

/**
 * Created by zhaowei on 16/5/27.
 */
public class ImageLocalCacheUtils {
    public static HashMap<String, SoftReference<Bitmap>> mImageMemoryCache = new HashMap<>();

    public static Bitmap getImageLocalCache(Context context, String url) {
        SoftReference<Bitmap> result_cache;
        result_cache = mImageMemoryCache.get(url);
        if (result_cache == null){
            Bitmap result;
            result = ImageViewLoaderRunnable.getBitmapFromUrl(context, url);
            if (result != null) {
                mImageMemoryCache.put(url, new SoftReference<Bitmap>(result));
                return result;
            }
        } else {
            return result_cache.get();
        }
        return null;
    }
}
