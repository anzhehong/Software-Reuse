package com.Communication;

import javax.jms.Message;

/**
 * Created by fowafolo
 * Date: 16/3/27
 * Time: 21:46
 */
public class TestEvent  {
    private String str;
    private Message message;
    public String getStr() {
        return str;
    }
    public Message getMessage(){return message;}

    public void setStr(String str) {
        this.str = str;
    }

    public TestEvent(String str) {

        this.str = str;
    }

    public TestEvent(String str, Message message) {
        this.str = str;
        this.message = message;
    }
}
