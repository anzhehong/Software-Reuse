package com.Warehouse.Server;

import com.Warehouse.MQUtil.MQConnect;
import com.Warehouse.MQUtil.MQFactory;
import com.Warehouse.controller.MainController;
import com.Warehouse.entity.AAMessage;
import com.Warehouse.entity.StaticVarible;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.util.ArrayList;

/**
 * Created by MSI on 2016/3/27.
 */
public class Server {
    public  MQConnect baseConnect;
    public MQConnect topicConnect;

    /**
     * 用来存收发消息的MQConnect
     */
    private ArrayList<MQConnect> mqConnects = new ArrayList<MQConnect>();

    public MQConnect privateConnect;

    public static void main(String[] args) throws JMSException {
        Server server = new Server();
    }

    public Server() {
        try {
            this.baseConnect = new MQConnect(MQFactory.getConsumer(StaticVarible.baseQueueConsumer));
            this.topicConnect = new MQConnect(MQFactory.getpublisher("Topic"));
            start();
//            receive();
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }

    public void start(){
        try {
            baseConnect.addMessageHandler(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    receiveQueue(message);
                }
            });
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void receiveQueue(Message message){
        try {
                        String userName = message.getStringProperty("userName");
                        String userPassword = message.getStringProperty("userPassword");

                        ApplicationContext beanFactory;
                        beanFactory = new ClassPathXmlApplicationContext("/WEB-INF/applicationContext.xml");
                        MainController mainController = (MainController) beanFactory.getBean("main");
                        boolean flag = mainController.checkPassword(userName, userPassword);
                        privateConnect = new MQConnect(MQFactory.getproducer("SC_" + userName),MQFactory.getConsumer("CS_"+userName));

                        
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
    }

    public void sendQueue(boolean flag) throws JMSException {
        if (flag) {
            AAMessage aaMessage = new AAMessage(1, "Login Successfully");
            //TODO: list中是否已经存在此userName的connect
            privateConnect.sendMessage(aaMessage.getFinalMessage());
            mqConnects.add(privateConnect);

            privateConnect.addMessageHandler(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    try {
                        System.out.print("asasas");
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });
        }else {
            AAMessage aaMessage = new AAMessage(2, "Login Failed");
            privateConnect.sendMessage(aaMessage.getFinalMessage());
            //TODO: 删掉已有
        }
     }

     public void sendTopic(Message message) throws JMSException {
        topicConnect.sendMessage(message);
     }

    public void receive() throws JMSException {
        if (privateConnect != null) {
            privateConnect.addMessageHandler(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    System.out.println(message);
                }
            });
        }
    }
}
