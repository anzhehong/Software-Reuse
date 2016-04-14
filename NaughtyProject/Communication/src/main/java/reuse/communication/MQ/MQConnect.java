package reuse.communication.MQ;

import javax.jms.*;

/**
 * Created by MSI on 2016/3/27.
 */
public class MQConnect {
    private MessageProducer messageProducer;
    private MessageConsumer messageConsumer;

    public MQConnect(MessageProducer messageProducer, MessageConsumer messageConsumer) {
        this.messageProducer = messageProducer;
        this.messageConsumer = messageConsumer;
    }

    public MQConnect(MessageConsumer messageConsumer) {
        this.messageConsumer = messageConsumer;
    }

    public MQConnect() {
    }

    public MQConnect(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    public void sendMessage(Message message) throws JMSException {
        messageProducer.send(message);
    }

    public Message receiveMessage() throws JMSException {

        Message message = messageConsumer.receive();
        return message;
    }

    public void addMessageHandler(MessageListener messageListener) throws JMSException {
        messageConsumer.setMessageListener(messageListener);
    }

    public MessageProducer getMessageProducer() {
        return messageProducer;
    }

    public void setMessageProducer(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    public MessageConsumer getMessageConsumer() {
        return messageConsumer;
    }

    public void setMessageConsumer(MessageConsumer messageConsumer) {
        this.messageConsumer = messageConsumer;
    }


}
