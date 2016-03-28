package com.Warehouse.entity;

import com.Warehouse.MQUtil.MQFactory;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.Date;

/**
 * Created by fowafolo
 * Date: 16/3/28
 * Time: 01:48
 */
public class AAMessage {



    /**
     * TODO  0: 发送连接请求;  1: 返回成功连接； 2：返回失败连接
     * TODO  3:
     * TODO  4：client表示收到成功连接通知，订阅成功，server可以发消息。
     */
    private int type;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 发送消息的用户
     * TODO: 如果是server发送，则为null?
     */
    private User user;

    /**
     * 消息创建时间
     */
    private Date createdTime;

    /**
     * Constructor
     * @param type
     * @param content
     * @param user
     */
    public AAMessage(int type, String content, User user) {
        this.type = type;
        this.content = content;
        this.user = user;
        this.createdTime = getCurrentTime();
    }

    public AAMessage(int type, User user) {
        this.type = type;
        this.user = user;
        this.createdTime = getCurrentTime();
        this.content = "Nothing Important";
    }

    public AAMessage(int type, String content) {
        this.type = type;
        this.content = content;
        this.createdTime = getCurrentTime();
        //TODO: User?null?
    }

    /**
     * 转换成JMS Message格式
     * @return
     * @throws JMSException
     */
    public Message getFinalMessage() throws JMSException {
        Message message = MQFactory.getMessage();
        message.setIntProperty("type", this.type);
        if (this.user != null) {
            message.setStringProperty("userName", this.user.getUserName());
            message.setStringProperty("userPassword", this.user.getUserPassword());
        }
        message.setStringProperty("content", this.content);
        return message;
    }

    public Date getCurrentTime() {
        return new Date();
    }


    /**
     * Getter and Setter
     * @return
     */
    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
