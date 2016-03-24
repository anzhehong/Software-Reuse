package com.Warehouse.Client;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.transport.stomp.Stomp;

import javax.jms.*;
import javax.jms.Connection;
import java.sql.*;

/**
 * Created by MSI on 2016/3/23.
 */
public class Client_base {
    public Client_base(String host, String port, String queneID) {
        this.host = host;
        this.port = port;
        QueneID = queneID;
    }

    private String host;
    private String port;
    private String QueneID;
    private Message message;
    private MessageConsumer consumer;
    private Session session;
    private Connection connection;

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setQueneID(String queneID) {
        QueneID = queneID;
    }

    public void start() throws JMSException{
        ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://" + host + ":" + port);
        Connection connection = factory.createConnection();
        connection.start();
        this.connection = connection;
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        this.session = session;
        Destination dest = session.createQueue(QueneID);
        MessageConsumer consumer = session.createConsumer(dest);
        this.consumer = consumer;
    }

    public Message getMessage()throws JMSException {
        return consumer.receive();
    }

    public void closeConnection()throws JMSException{
        this.consumer.close();
        this.session.close();
        this.connection.close();
    }

}
