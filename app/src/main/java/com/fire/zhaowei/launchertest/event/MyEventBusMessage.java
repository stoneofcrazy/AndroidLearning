package com.fire.zhaowei.launchertest.event;

/**
 * Created by zhaowei on 16/5/12.
 */
public class MyEventBusMessage {
    private String message;

    public MyEventBusMessage(String message){
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
