package reuse.communication.MQ;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by MSI on 2016/3/27.
 */
public class MQFactory {
    private static Session session = null;

    public static String address = "tcp://localhost:61616";

    public static void setAddress(String address) {
        MQFactory.address = address;
    }

    public static Session getSession() throws JMSException {

        if (session != null) return session;
        else {

            ConnectionFactory factory = new ActiveMQConnectionFactory(address);
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

    public static MessageProducer getpublisher(String TopicID) throws JMSException{
        Session tmpSession = getSession();
        Topic topic = tmpSession.createTopic(TopicID);
        MessageProducer messageProducer = tmpSession.createProducer(topic);
        messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);
        return messageProducer;
    }

    public static MessageConsumer getSubscriber(String TopicID) throws JMSException{
        Session tmpSession = getSession();
        Topic topic = tmpSession.createTopic(TopicID);
        MessageConsumer messageConsumer = tmpSession.createConsumer(topic);
        return messageConsumer;
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
