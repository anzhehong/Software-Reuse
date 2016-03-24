package com.Warehouse.Server;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by MSI on 2016/3/23.
 */
public class Server_base {
    public Server_base(String host, String port, String queneID) {
        this.host = host;
        this.port = port;
        QueneID = queneID;
    }

    private String host;
    private String port;
    private String QueneID;
    private Message message;
    private MessageProducer producer;
    private Session session;
    private Connection connection;

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public void setQueneID(String queneID) {
        QueneID = queneID;
    }

    public void start() throws JMSException {
        ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://" + host + ":" + port);
        Connection connection = factory.createConnection();
        connection.start();
        this.connection = connection;
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        this.session = session;
        Destination dest = session.createQueue(QueneID);
        MessageProducer producer = session.createProducer(dest);
        this.producer = producer;
        this.message = session.createMessage();
    }

    public void sendMessage()throws JMSException {
        this.message.setIntProperty("id",1);
        this.message.setStringProperty("name","test001");
        this.producer.send(this.message);
    }

    public void close()throws JMSException{
        this.producer.close();
        this.session.close();
        this.connection.close();
    }

}
