package com.Warehouse.entity;

import java.util.Date;

/**
 * Created by fowafolo
 * Date: 16/3/27
 * Time: 18:12
 */
public class AAMessage  {

    /**
     * queue消息类型
     */
    private int type;

    /**
     * client的发送者
     */
    private User user;

    /**
     * 消息具体内容
     */
    private String content;

    /**
     * 创建时间
     */
    private Date createdTime;

    public AAMessage(User newUser, String newContent, int newType) {
        this.createdTime = getCurrentTime();
        this.user = newUser;
        this.content = newContent;
        this.type = newType;
    }

    /**
     * TODO:进行一些时间格式的处理
     * @return
     */
    public Date getCurrentTime() {
        return new Date();
    }


    /**
     * Getter and Setter
     * @return
     */
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

}
