package com.Warehouse.Client;

/**
 * Created by fowafolo
 * Date: 16/3/23
 * Time: 16:01
 */

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class ClientRoom {

    public static void main(String[] args) throws JMSException {

        ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");

        Connection connection = factory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination dest = session.createQueue("testQueue");
        MessageConsumer consumer = session.createConsumer(dest);

        Message msg = consumer.receive();
        TextMessage txtmsg = (TextMessage) msg;
        String txt = txtmsg.getText();
        System.out.println("Received message from ActiveMQ: " + txt);

        consumer.close();
        session.close();
        connection.close();

        System.out.println("Completed.");
    }
}
