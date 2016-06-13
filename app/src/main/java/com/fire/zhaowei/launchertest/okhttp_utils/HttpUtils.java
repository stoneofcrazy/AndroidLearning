package com.fire.zhaowei.launchertest.okhttp_utils;

import com.alibaba.fastjson.JSON;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by zhaowei on 16/6/8.
 */
public class HttpUtils {
    public void getInstance(){

    }
    public Object getConnection(String url, Class mClass){
        OkHttpClient mOkHttpClient = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        try {
            Response mResponse = mOkHttpClient.newCall(request).execute();
            if (!mResponse.isSuccessful()) return null;
            Object user = JSON.parseObject(mResponse.body().toString(), mClass);
            return user;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
