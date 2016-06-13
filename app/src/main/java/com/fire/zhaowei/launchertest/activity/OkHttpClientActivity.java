package com.fire.zhaowei.launchertest.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fire.zhaowei.launchertest.R;
import com.fire.zhaowei.launchertest.bean.OkHttpBean;
import com.fire.zhaowei.launchertest.okhttp_utils.HttpUtils;

/**
 * Created by zhaowei on 16/6/8.
 */
public class OkHttpClientActivity extends Activity {
    private String url = "http://apis.baidu.com/heweather/weather/free";
    private Button bt_test;
    private TextView tv_content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ok_http_layout);

        initView();
    }

    private void initView() {
        bt_test = (Button) findViewById(R.id.bt_test);
        tv_content = (TextView) findViewById(R.id.tv_content);
        bt_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                OkHttpBean bean = (OkHttpBean) HttpUtils.getConnection(url, OkHttpBean.class);
//                tv_content.setText(bean.getErrMsg());
            }
        });
    }
}
