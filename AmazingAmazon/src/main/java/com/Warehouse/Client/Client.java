package com.Warehouse.Client;

import com.Warehouse.factory.MQFactory;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import java.sql.Connection;

/**
 * Created by MSI on 2016/3/27.
 */
public class Client {

    private MQConnect baseConnect;
    private MQConnect privateConnect;

    public static void main(String[] args) throws JMSException {
        Client client = new Client();
        Message message = MQFactory.getSession().createMessage();
        message.setIntProperty("type",0);
        message.setStringProperty("username", "123");
        message.setStringProperty("password", "123");
        client.Login(message);
    }

    public Client() {
        try {
            this.baseConnect = new MQConnect(MQFactory.getproducer("testQueue"), MQFactory.getConsumer("testQueue"));
            this.privateConnect = new MQConnect(MQFactory.getproducer("protocol1"), MQFactory.getConsumer("protocol2"));
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void Login(Message message) throws JMSException {
        baseConnect.sendMessage(message);
        privateConnect.addMessageHandler(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                System.out.println(message);
            }
        });
    }




}
