package com.Warehouse.Server;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by MSI on 2016/3/23.
 */
public class Server_base {
    public Server_base(String host, String port, String queueID) {
        this.host = host;
        this.port = port;
        QueueID = queueID;
    }

    private String host;
    private String port;
    private String QueueID;
    private Message message;
    private MessageProducer producer;
    private MessageConsumer consumer;
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

    public void setQueueID(String queueID) {
        QueueID = queueID;
    }

    public void start() throws JMSException {
        ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://" + host + ":" + port);
        Connection connection = factory.createConnection();
        connection.start();
        this.connection = connection;
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        this.session = session;
        this.message = session.createMessage();
    }

    public void sendMessage(int type,Boolean check)throws JMSException {
        System.out.println(QueueID +" server: "+check);
        this.message.setIntProperty("type",type);
        this.message.setBooleanProperty("confirm", check);
        Destination dest = session.createQueue(QueueID);
        MessageProducer producer = session.createProducer(dest);
        producer.send(this.message);
    }

    public String getQueueID() {
        return QueueID;
    }

    public Message getMessage()throws JMSException {
        Destination dest = session.createQueue(QueueID);
        return session.createConsumer(dest).receive();
    }

    public void close()throws JMSException{
        this.session.close();
        this.connection.close();
    }

}
