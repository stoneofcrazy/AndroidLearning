package com.fire.zhaowei.launchertest.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Looper;
import android.widget.TextView;

import com.fire.zhaowei.launchertest.R;

import java.io.ByteArrayInputStream;

import butterknife.BindView;

/**
 * Created by zhaowei on 16/5/13.
 */
public class EncryptionDecipheringActivity extends BaseActivity {
    private static final int MAX_LENGTH = 128;
    private static final String CHARS = "abcdefghijklmnopqrst wvuxyz";
    @BindView(R.id.tv_content)
    TextView mContent;
    @BindView(R.id.display)
    TextView mDisplay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.encryption_deciphering);
        initView();
    }

    private void initView() {
        while (true){
            int length = (int) (Math.random() * MAX_LENGTH);
            if (length == 0) length = 1;
            int[] next = new int[length];
            int[] next2 = new int[length];
            StringBuffer P = new StringBuffer();
            for (int i = 0; i < length; i++) {
                int index = (int) (Math.random()*CHARS.length());
                if (index == CHARS.length()) index = CHARS.length() - 1;
                P.append(CHARS.charAt(index));
            }

            makeNext(P.toString(), next);
            makeNext2(P.toString(), next2);

            String method1 = "方法1:";
            String method2 = "方法2:";
            boolean flag = false;
            for (int i = 0; i < length; ++i) {
                method1 += next[i];
                method2 += next2[i];
                if (next[i] != next2[i])flag = true;
            }
            System.out.println(method1+"\n");
            System.out.println(method2+"\n");
            if (flag) {
                System.out.println(P.toString()+"\n");//特例：mmtmmmc
                System.out.println("恭喜你想错了！"+"\n");//   0101220
                break;
            };
        }
    }

    public void makeNext(String P,int next[]) {
        int q,k;
        int m = P.length();
        next[0] = 0;
        for (q = 1, k = 0; q < m; q++){
            while (k > 0 && P.charAt(q) != P.charAt(k))
                k = next[k-1];
            if (P.charAt(q) == P.charAt(k)) {
                k++;
            }
            next[q] = k;
        }
    }
    public void makeNext2(String P,int next[]) {
        int q,k;
        int m = P.length();
        next[0] = 0;
        for (q = 1,k = 0; q < m; ++q)
        {
            while(k > 0 && P.charAt(q) != P.charAt(k))
                k = 0;
            if (P.charAt(q) == P.charAt(k))
            {
                k++;
            }
            next[q] = k;
        }
    }
}
