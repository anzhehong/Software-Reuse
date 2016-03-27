package com.Warehouse.MQUtil;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by MSI on 2016/3/27.
 */
public class MQFactory {
    private static Session session = null;
    public static Session getSession() throws JMSException {
        if (session != null) return session;
        else {
            ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            Connection connection = factory.createConnection();
            connection.start();
            Session connectionSession = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            session = connectionSession;
            return session;
        }
    }
    public static MessageConsumer getConsumer(String QueueID) throws JMSException {
        Session tmpSession = getSession();
        Destination dest = tmpSession.createQueue(QueueID);
        return tmpSession.createConsumer(dest);
    }

    public static MessageProducer getproducer(String QueueID) throws JMSException {
        Session tmpSession = getSession();
        Destination dest = tmpSession.createQueue(QueueID);
        return tmpSession.createProducer(dest);
    }

    public static Message getMessage() throws JMSException {
        Session temSession = getSession();
        Message message = temSession.createMessage();
//        message.setIntProperty("type",0);
//        message.setStringProperty("username", "123");
//        message.setStringProperty("password", "123");
        return message;

    }

}
