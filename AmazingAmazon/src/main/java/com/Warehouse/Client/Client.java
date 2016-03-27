package com.Warehouse.Client;

import com.Warehouse.Event.TestEvent;
import com.Warehouse.MQUtil.MQConnect;
import com.Warehouse.MQUtil.MQFactory;
import com.Warehouse.entity.StaticVarible;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * Created by MSI on 2016/3/27.
 */
public class Client {

    /**
     * 登录等请求
     */
    private MQConnect baseConnect;

    /**
     * 消息传送通道
     */
    public MQConnect privateConnect;

    public Client() {
        try {
            this.baseConnect = new MQConnect(MQFactory.getproducer(StaticVarible.baseQueueProducer)
                    );
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void Login(Message message) throws JMSException {
        System.out.println("Login");
        String userName = message.getStringProperty("userName");
        System.out.println(userName);

        baseConnect.sendMessage(message);
        privateConnect = new MQConnect(MQFactory.getproducer("CS_" + userName),
                MQFactory.getConsumer("SC_" +userName));
        privateConnect.addMessageHandler(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                int type = 0;
                try {
                    type = message.getIntProperty("type");
                } catch (JMSException e) {
                    e.printStackTrace();
                }
                System.out.println(type);
                EventController.eventBus.post(new TestEvent("aaa"));
            }
        });

    }

    public static void main(String[] args) throws JMSException {
        Client client = new Client();
        Message message = MQFactory.getMessage();
        message.setIntProperty("type", 0);
        message.setStringProperty("userName", "abc");
        message.setStringProperty("userPassword","abc");
        client.Login(message);
    }



}
