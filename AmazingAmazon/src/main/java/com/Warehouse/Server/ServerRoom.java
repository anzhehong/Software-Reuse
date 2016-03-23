package com.Warehouse.Server;

/**
 * Created by fowafolo
 * Date: 16/3/23
 * Time: 16:05
 */
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.Date;


public class ServerRoom {

    @SuppressWarnings("deprecation")
    public static void main(String[] args) throws JMSException {

        ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");

        Connection connection = factory.createConnection();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination dest = session.createQueue("testQueue");
        MessageProducer producer = session.createProducer(dest);

        Message msg = session.createTextMessage("MSG_" + (new Date(System.currentTimeMillis())).toLocaleString());
        producer.send(msg);

        producer.close();
        session.close();
        connection.close();

        System.out.println("Message sent.");
    }
}
