package com.Application.Client;

import com.Communication.InterfaceEvent;
import com.Communication.MQConnect;
import com.Communication.MQFactory;
import com.Communication.AAMessage;
import com.Config.StaticVarible;
import com.Util.EventController;

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
                System.out.println("test on message");
                int type = 0;
                //TODO: 收到登录验证
                try {
                    type = message.getIntProperty("type");
                    System.out.println("typeeeee: "+ type);
                    if (type == 1) {
                        //TODO:登录成功写入文件

                        topicConenct = new MQConnect(MQFactory.getSubscriber("Topic"));
                        topicConenct.addMessageHandler(new MessageListener() {
                            @Override
                            public void onMessage(Message message) {
                                try {
                                    EventController.eventBus.post(new InterfaceEvent("MessageReceived",message));
                                    System.out.println(message.getStringProperty("content"));
                                } catch (JMSException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        EventController.eventBus.post(new InterfaceEvent("loginSuccessfully"));
                    }else if (type == 999){
                        //TODO: 断开重连
                        //TODO: 发送给interface 不能再输入了
                        //TODO: 发送给server ：请求重连
                        //TODO: 关闭topic，保证不会继续接受后面其它client 发送的消息
                        //TODO: 继续监听，直到拿到server发给我的『你成功重连啦！』的消息->加入topic，告诉interface可以接受。
                        EventController.eventBus.post(new InterfaceEvent("inputForbidden"));
                        topicConenct.getMessageConsumer().close();

                        AAMessage reloginMessage = new AAMessage(777, "Relogin Request");
                        privateConnect.sendMessage(reloginMessage.getFinalMessage());

                    }else if (type == 888) {
                        //TODO: 『你成功重连啦！』的消息->加入topic，告诉interface可以接受。
                        topicConenct = new MQConnect(MQFactory.getSubscriber("Topic"));
                        topicConenct.addMessageHandler(new MessageListener() {
                            @Override
                            public void onMessage(Message message) {
                                try {
                                    EventController.eventBus.post(new InterfaceEvent("MessageReceived",message));
                                    System.out.println(message.getStringProperty("content"));
                                } catch (JMSException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        EventController.eventBus.post(new InterfaceEvent("LoggedAgain"));
                    }
                    else if (type == 2) {
                        //TODO:登录失败，写入文件

                        //TODO: 登录失败，两种情况
                        System.out.println(message.getStringProperty("content"));
                        EventController.eventBus.post(new InterfaceEvent(message.getStringProperty("content")));
                    }
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void SendMessage(AAMessage aaMessage) throws JMSException {
        if (privateConnect != null) {
            System.out.println("Client send a message to server...");
            privateConnect.sendMessage(aaMessage.getFinalMessage());
        }else {
            //TODO:断开连接？
        }
    }


}
