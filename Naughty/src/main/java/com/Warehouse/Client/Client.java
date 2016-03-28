package com.Warehouse.Client;

import com.Warehouse.Event.TestEvent;
import com.Warehouse.MQUtil.MQConnect;
import com.Warehouse.MQUtil.MQFactory;
import com.Warehouse.entity.AAMessage;
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

    public MQConnect topicConenct;

    public Client() {
        try {
            this.baseConnect = new MQConnect(MQFactory.getproducer(StaticVarible.baseQueueProducer)
            );
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void Login(final AAMessage aaMessage) throws JMSException {
        String userName = aaMessage.getUser().getUserName();

        baseConnect.sendMessage(aaMessage.getFinalMessage());
        privateConnect = new MQConnect(MQFactory.getproducer("CS_" + userName),
                MQFactory.getConsumer("SC_" +userName));
        privateConnect.addMessageHandler(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                System.out.println("add handler");
                int type = 0;
                //TODO: 收到登录验证

                try {
                    type = message.getIntProperty("type");
                    if (type == 1) {
                        topicConenct = new MQConnect(MQFactory.getSubscriber("Topic"));
                        topicConenct.addMessageHandler(new MessageListener() {
                            @Override
                            public void onMessage(Message message) {
//                                aaMessage.setType(3);
                                try {
                                    System.out.println(message.getStringProperty("content"));
                                } catch (JMSException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                    }
                } catch (JMSException e) {
                    e.printStackTrace();
                }
                System.out.println(type);
                EventController.eventBus.post(new TestEvent("aaa"));
            }
        });
    }

    public void SendMessage(AAMessage aaMessage) throws JMSException {
        if (privateConnect != null) {
            System.out.println("Client send a message to server...");
            privateConnect.sendMessage(aaMessage.getFinalMessage());
            privateConnect.addMessageHandler(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    System.out.println("Listen to Server for reply...");
                }
            });
        }else {
            //TODO:断开连接？
        }
    }
}
