package com.fire.zhaowei.launchertest.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fire.zhaowei.launchertest.R;
import com.fire.zhaowei.launchertest.adapter.TestRecyclerViewAdapter;
import com.fire.zhaowei.launchertest.my_interface.MyItemOnClickListener;

import java.util.ArrayList;

/**
 * Created by zhaowei on 16/5/10.
 */
public class TestActivity extends Activity {
    RecyclerView mViewList;
    private TestRecyclerViewAdapter adapter;
    private TabLayout mTabLayout;
    private ViewPager mViewpager;
    ArrayList<String> data = new ArrayList<String>();
    ArrayList<String> mTestData = new ArrayList<>();
    private ArrayList<TextView> mTextViews = new ArrayList<>();
    private static final int BUTTER_KNIFE = 0;
    private static final int CUSTOM_TABS = 1;
    private static final int EVENT_BUS = 2;
    private static final int ENCRYPTION_DECIPHERING = 3;
    private static final int IMAGEVIEW_LOADING = 4;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);
        data.add("测试");
        data.add("书籍");
        data.add("零食");
        data.add("厨具");
        data.add("服装");
        data.add("书籍");
        data.add("零食");
        data.add("厨具");
        data.add("服装");
        data.add("书籍");
        data.add("零食");
        data.add("厨具");
        data.add("服装");
        data.add("书籍");
        data.add("零食");
        data.add("厨具");
        data.add("服装");
        initTestView();
        initView();
    }

    private void initTestView() {
        mTestData.add("ButterKnife注解");
        mTestData.add("CustomTabs");    //访问外部服务器Google推荐的控件，有预加载，速度更快
        mTestData.add("EventBus");      //发布和订阅事件到总线
        mTestData.add("KMP算法");//仿漫咖加解密
        mTestData.add("图片异步加载及4.4以后版本注意事项");//仿漫咖加解密
        mViewList = new RecyclerView(TestActivity.this);
        mViewList.setLayoutManager(new LinearLayoutManager(TestActivity.this));
        adapter = new TestRecyclerViewAdapter(TestActivity.this, mTestData, new MyItemOnClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                startActivityMethod(position);
            }
        });
        adapter.setmState(adapter.EMPTY);
        mViewList.setAdapter(adapter);
    }

    private void startActivityMethod(int position) {
        Intent intent = new Intent();
        switch (position){
            case BUTTER_KNIFE:
                intent.setClass(TestActivity.this, ButterKnifeActivity.class);
                break;
            case CUSTOM_TABS:
                intent.setClass(TestActivity.this, CustomTabsActivity.class);
                break;
            case EVENT_BUS:
                intent.setClass(TestActivity.this, EventBusActivity.class);
                break;
            case ENCRYPTION_DECIPHERING:
                intent.setClass(TestActivity.this, EncryptionDecipheringActivity.class);
                break;
            case IMAGEVIEW_LOADING:
                intent.setClass(TestActivity.this, ImageViewLoadingActivity.class);
                break;
        }
        startActivity(intent);
    }
    private void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewpager = (ViewPager) findViewById(R.id.viewpager);

        mTabLayout.setTabsFromPagerAdapter(mAdapter);
        for (int i = 1;i < data.size(); i++){
            TextView tv = new TextView(this);
            tv.setText(data.get(i));
            tv.setTextColor(Color.parseColor("#ff0000"));
            mTextViews.add(tv);
        }

        mViewpager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewpager);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }
    private PagerAdapter mAdapter = new PagerAdapter(){
        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if (position > 0) {
                container.addView(mTextViews.get(position - 1));
                return mTextViews.get(position - 1);
            } else {
                container.addView(mViewList);
                return mViewList;
            }
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            if (position > 0) {
                container.removeView(mTextViews.get(position - 1));
            } else {
                container.removeView(mViewList);
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return data.get(position);
        }
    };
}
