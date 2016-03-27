package com.Warehouse.Server;

import com.Warehouse.MQUtil.MQConnect;
import com.Warehouse.MQUtil.MQFactory;
import com.Warehouse.controller.MainController;
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
            start();
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }

    public void start(){

        try {
            baseConnect.addMessageHandler(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    try {
                        String userName = message.getStringProperty("userName");
                        String userPassword = message.getStringProperty("userPassword");

                        ApplicationContext beanFactory;
                        beanFactory = new ClassPathXmlApplicationContext("/WEB-INF/applicationContext.xml");
                        MainController mainController = (MainController) beanFactory.getBean("main");
                        boolean flag = mainController.checkPassword(userName, userPassword);
                        Message resultMessage = MQFactory.getSession().createMessage();
                        privateConnect = new MQConnect(MQFactory.getproducer("SC_" + userName),MQFactory.getConsumer("CS_"+userName));

                        if (flag) {
                            resultMessage.setIntProperty("type", 1);
                            //TODO: list中是否已经存在此userName的connect
                            privateConnect.sendMessage(resultMessage);
                            mqConnects.add(privateConnect);

                        }else {
                            resultMessage.setIntProperty("type",2);
                            resultMessage.setStringProperty("errorMsg", "Login Failed");
                            privateConnect.sendMessage(resultMessage);
                            //TODO: 删掉
                        }
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }

                }
            });
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
