package com.Warehouse.entity;

import javax.jms.JMSException;
import javax.jms.Message;
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

    /**
     * TODO:进行一些时间格式的处理
     * @return
     */
    public Date getCurrentTime() {
        return new Date();
    }

    /**
     * 实例化后调用此方法，返回JMS 的 Message类型
     * @return
     * @throws JMSException
     */
    public Message getFinalMessage() throws JMSException {
        Message message = null;
        if (this!=null)
        {
            message.setIntProperty("userId", this.getUser().getId());
            message.setIntProperty("type", this.getType());
            message.setStringProperty("userName",this.getUser().getUserName());
            message.setStringProperty("userPassword", this.getUser().getUserPassword());
            message.setStringProperty("content",this.getContent());
            /**
             * FIXME: 不能传Date类型
             */
//            message.setStringProperty("createdTime",this.getCreatedTime());
        }
        return message;
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
