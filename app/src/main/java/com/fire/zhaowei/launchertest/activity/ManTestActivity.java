package com.fire.zhaowei.launchertest.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fire.zhaowei.launchertest.R;
import com.fire.zhaowei.launchertest.views.DetailButtonItemViews;
import com.fire.zhaowei.launchertest.views.DetailDiversityView;

import java.util.ArrayList;

/**
 * Created by zhaowei on 16/5/31.
 */
public class ManTestActivity extends Activity {
    private LinearLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.man_test_layout);

        layout = (LinearLayout) findViewById(R.id.layout);
        initView();
    }

    private void initView() {
        for (int i = 0; i < 23;) {
            int size = 5;
            if ((i+5) >= 23) size = 23 - i;
            DetailButtonItemViews dbiv = new DetailButtonItemViews(this, 5, size);
            ArrayList<String> data = new ArrayList<>();
            for (int i1 = 0; i1 < size; i1++) {
                data.add((i+1+i1)+"");
            }
            dbiv.setRowsAllDiversityData(data);
            layout.addView(dbiv);
            i += size;
        }
    }
}
