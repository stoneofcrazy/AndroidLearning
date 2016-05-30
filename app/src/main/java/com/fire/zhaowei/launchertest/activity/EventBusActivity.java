package com.fire.zhaowei.launchertest.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fire.zhaowei.launchertest.R;
import com.fire.zhaowei.launchertest.event.MyEventBusMessage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhaowei on 16/5/12.
 */
public class EventBusActivity extends BaseActivity {
    @BindView(R.id.send_event)
    Button mEvent;
    @BindView(R.id.tv_display)
    TextView mDisplay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eventbus_activity);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @OnClick(R.id.send_event)
    public void onClickEnvent(View v){
        EventBus.getDefault().post(new MyEventBusMessage("测试数据"));
        ((Button)v).setText("测试数据Button");
    }
    @Subscribe
    public void MyEvent(MyEventBusMessage event){
        mDisplay.setText(event.getMessage());
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
