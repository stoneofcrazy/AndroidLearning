package com.fire.zhaowei.launchertest.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fire.zhaowei.launchertest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhaowei on 16/5/11.
 */
public class ButterKnifeActivity extends BaseActivity {
    @BindView(R.id.bt_test)
    Button bt;
    @BindView(R.id.tv_test)
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buffer_knife);
        bt.setText("测试按钮");
        tv.setText("测试TextView");
    }
    @OnClick(R.id.bt_test)
    public void TestClick(){
        if (bt.getText().equals("点击了")){
            bt.setText("测试按钮");
            tv.setText("测试TextView");
        } else {
            bt.setText("点击了");
            tv.setText("点击了");
        }
        Toast.makeText(this, "你点击了按钮", Toast.LENGTH_LONG).show();
    }
}
