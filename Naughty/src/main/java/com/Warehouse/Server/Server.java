package com.Warehouse.Server;

import com.Warehouse.Client.WriteLog;
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
import java.util.*;

/**
 * Created by MSI on 2016/3/27.
 */
public class Server {

    private static int validLoginCount = 0;
    private static int inValidLoginCount = 0;
    private static String loginLog = "ServerLoginLog.txt";

    private static int second = 1000;

    public  MQConnect baseConnect;
    public MQConnect topicConnect;

    /**
     * 用来存收发消息的MQConnect
     */
    private static ArrayList<MQConnect> mqConnects = new ArrayList<MQConnect>();
    private static Map<MQConnect, ArrayList<Date>> connectArrayListMap = new HashMap<MQConnect, ArrayList<Date>>();
    private static Map<MQConnect, Date> connectDateMap = new HashMap<MQConnect, Date>();

    public MQConnect privateConnect;

    public static void main(String[] args) throws JMSException {
        Server server = new Server();
        server.timer = new Timer();
        server.timer.schedule(new WriteLoginTask(), 60 * second, 60 * second);
    }

    public static Timer timer;
    static class WriteLoginTask extends TimerTask
    {
        public void run() {
            //TODO: 把validLogin和invalidLogin记录到文件中
            Date date = new Date();
            WriteLog.write(loginLog, date + "\tValid Login Count: " + validLoginCount + "\tInvalid Login Count: " + inValidLoginCount);
            inValidLoginCount = 0;
            validLoginCount = 0;

        }
    }


    public Server() {
        try {
            this.baseConnect = new MQConnect(MQFactory.getConsumer(StaticVarible.baseQueueConsumer));
            this.topicConnect = new MQConnect(MQFactory.getpublisher("Topic"));
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
                    System.out.println("start");
                    receiveQueue(message);
                }
            });
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void receiveQueue(Message message){
        try {
            System.out.println("receiveQueue");
            String userName = message.getStringProperty("userName");
            String userPassword = message.getStringProperty("userPassword");
            ApplicationContext beanFactory;
            beanFactory = new ClassPathXmlApplicationContext("/WEB-INF/applicationContext.xml");
            MainController mainController = (MainController) beanFactory.getBean("main");
            boolean flag = mainController.checkPassword(userName, userPassword);
            System.out.println("flag : " + flag);
            privateConnect = new MQConnect(MQFactory.getproducer("SC_" + userName),MQFactory.getConsumer("CS_"+userName));
            sendQueue(flag, privateConnect);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void sendQueue(boolean flag, MQConnect connect) throws JMSException {
        if (flag) {
            if (isConnectExist(connect)) {
                System.out.println("already");
                AAMessage aaMessage = new AAMessage(2, "This Account has Already Logged in.");
                connect.sendMessage(aaMessage.getFinalMessage());
                inValidLoginCount += 1;
                //TODO: 测试 删除此privateConnect
//                boolean removeFlag = removeConnect(connect);
            }else {
                System.out.println("sendQueue");
                AAMessage aaMessage = new AAMessage(1, "Login Successfully");
                connect.sendMessage(aaMessage.getFinalMessage());
                mqConnects.add(connect);
                validLoginCount += 1;

                connect.addMessageHandler(new MessageListener() {
                    @Override
                    public void onMessage(Message message) {
                        try {
                            sendTopic(message);
                        } catch (JMSException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }else {
            AAMessage aaMessage = new AAMessage(2, "Validation Failed.");
            connect.sendMessage(aaMessage.getFinalMessage());
            inValidLoginCount += 1;
            //TODO: 测试 删除此privateConnect
//            boolean removeFlag = removeConnect(privateConnect);
        }
     }

     public void sendTopic(Message message) throws JMSException {
        topicConnect.sendMessage(message);
     }


    public boolean isConnectExist (MQConnect connect) throws JMSException {
        for(int i = 0; i< mqConnects.size(); i++) {
            if (mqConnects.get(i).getMessageProducer().getDestination().equals(connect.getMessageProducer().getDestination())) {
                return true;
            }
        }
        return false;
    }

}
