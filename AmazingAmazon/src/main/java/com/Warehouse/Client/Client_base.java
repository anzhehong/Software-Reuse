package com.Warehouse.Client;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by MSI on 2016/3/23.
 */
public class Client_base {
    public Client_base(String host, String port, String queueID) {
        this.host = host;
        this.port = port;
        QueueID = queueID;
    }
    private String host;
    private String port;
    private String QueueID;
    private Message message;
    private MessageConsumer consumer;
    private MessageProducer producer;
    private Session session;
    private Connection connection;

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setQueueID(String queueID) {
        QueueID = queueID;
    }

    public void start() throws JMSException{
        ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://" + host + ":" + port);
        Connection connection = factory.createConnection();
        connection.start();
        this.connection = connection;
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        this.session = session;
        Destination dest = session.createQueue(QueueID);
        MessageConsumer consumer = session.createConsumer(dest);
        this.consumer = consumer;
        this.producer = session.createProducer(dest);
    }

    public void createSession(String QueueId){

    }


    public MessageProducer getProducer() {
        return producer;
    }

    public MessageConsumer getConsumer() {
        return consumer;
    }

    public Session getSession() {
        return session;
    }

    public Message getMessage()throws JMSException {
        return consumer.receive();
    }

    public void sendMessage(String mes)throws JMSException {
        this.message.setIntProperty("id",1);
        this.message.setStringProperty("name",mes);
        this.producer.send(this.message);
    }

    public void closeConnection()throws JMSException{
        this.consumer.close();
        this.session.close();
        this.connection.close();
    }

}
