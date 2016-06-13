package com.fire.zhaowei.launchertest.bean;

/**
 * Created by zhaowei on 16/6/12.
 * {"errNum":300202,"errMsg":"Missing apikey"}
 */
public class OkHttpBean {
    private int errNum;
    private String errMsg;

    public int getErrNum() {
        return errNum;
    }

    public void setErrNum(int errNum) {
        this.errNum = errNum;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
