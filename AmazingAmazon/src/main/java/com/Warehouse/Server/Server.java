package com.Warehouse.Server;

import com.Warehouse.MQUtil.MQConnect;
import com.Warehouse.MQUtil.MQFactory;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * Created by MSI on 2016/3/27.
 */
public class Server {
    public  MQConnect baseConnect;
    public MQConnect privateConnect;

    public static void main(String[] args) throws JMSException {
        Server server = new Server();
        System.out.println("ok");
    }

    public Server() {
        try {
            this.baseConnect = new MQConnect(MQFactory.getproducer("testQueue"), MQFactory.getConsumer("testQueue"));
            privateConnect = new MQConnect(MQFactory.getproducer("protocol2"),MQFactory.getConsumer("protocol1"));
            start();
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }

    public void start(){

        try {
            baseConnect.addMessageHandler(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    try {
                        System.out.println(message.getIntProperty("type"));
                        Message hh = MQFactory.getSession().createMessage();
                        hh.setIntProperty("type",0);
                        hh.setStringProperty("username", "123");
                        hh.setStringProperty("password", "123");
                        privateConnect.sendMessage(hh);
                        System.out.print("send");
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }



}
