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

    public Client() {
        try {
            this.baseConnect = new MQConnect(MQFactory.getproducer(StaticVarible.baseQueueProducer)
                    );
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void Login(AAMessage aaMessage) throws JMSException {
        String userName = aaMessage.getUser().getUserName();

        baseConnect.sendMessage(aaMessage.getFinalMessage());
        privateConnect = new MQConnect(MQFactory.getproducer("CS_" + userName),
                MQFactory.getConsumer("SC_" +userName));
        privateConnect.addMessageHandler(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                System.out.println("add handler");
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
}
