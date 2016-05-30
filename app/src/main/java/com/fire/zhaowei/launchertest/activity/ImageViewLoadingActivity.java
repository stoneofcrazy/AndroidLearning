package com.fire.zhaowei.launchertest.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.fire.zhaowei.launchertest.R;
import com.fire.zhaowei.launchertest.adapter.ImageViewListAdapter;
import com.fire.zhaowei.launchertest.utils.ImageViewLoaderUtils;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by zhaowei on 16/5/19.
 */
public class ImageViewLoadingActivity extends BaseActivity {
    private RecyclerView mImage_content;
    private ImageViewListAdapter adapter;
    private ArrayList<String> data = new ArrayList<>();
    private static final String image_url = "http://pic36.nipic.com/20131128/11748057_141932278338_2.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imageview_loading);
        initView();
    }

    private void initView() {
        mImage_content = (RecyclerView) findViewById(R.id.image_content);
        mImage_content.setLayoutManager(new LinearLayoutManager(ImageViewLoadingActivity.this));
        for (int i = 0; i < 100; i++) {
            data.add(image_url);
        }

        adapter = new ImageViewListAdapter(this, data);
        mImage_content.setAdapter(adapter);
    }
}
