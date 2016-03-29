package com.Application.Server;

import com.Communication.AAMessage;
import com.Communication.MQConnect;
import com.Communication.MQFactory;
import com.Config.ConfigData;
import com.Database.Controller.MainController;
import com.Util.TimeUtil;
import com.Util.WriteLog;
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

    /**
     * 合法输入次数/minutes
     */
    private static int validLoginCount = 0;
    /**
     * 不合法输入次数/minute
     */
    private static int inValidLoginCount = 0;
    private static int forwardedMessageCount = 0;
    /**
     * 登录日志的路径
     */
    private static String loginLog = ConfigData.getLoginLog();
    private static String ForwardedMessageLog = "ServerForwardedMessageLog.txt";

    private static int second = Integer.parseInt(ConfigData.getLoginLogSecond());

    /**
     * queue用来发送登录请求
     * TODO: 注册
     */
    public  MQConnect baseConnect;

    /**
     * 私用queue发送消息给server
     * (except login register request)
     */
    public MQConnect privateConnect;
    /**
     * 公用topic连接
     */
    public MQConnect topicConnect;

    /**
     * 用来存收发消息的MQConnect
     */
    protected ArrayList<MQConnect> mqConnects = new ArrayList<MQConnect>();
    /**
     * 用来记录每一个登录且发送过消息的client这次session的每次消息时间
     */
    private static Map<String, ArrayList<Date>> connectArrayListMap = new HashMap<String, ArrayList<Date>>();

    /**
     * 写日志的定时器
     */
    public static Timer timer;

    /**
     * 定时器记录每分钟合法和不合法的消息个数
     */
    static class WriteLoginTask extends TimerTask
    {
        public void run() {
            //TODO: 把validLogin和invalidLogin记录到文件中
            Date date = new Date();
            WriteLog.write(loginLog, date + "\tValid Login Count: " + validLoginCount + "\tInvalid Login Count: " + inValidLoginCount);
            WriteLog.write(ForwardedMessageLog, date + "\tForwarded Message Count: " + forwardedMessageCount);
            inValidLoginCount = 0;
            validLoginCount = 0;
            forwardedMessageCount = 0;

        }
    }


    public static void main(String[] args) throws JMSException {
        Server server = new Server();
        server.timer = new Timer();
        server.timer.schedule(new WriteLoginTask(), 60 * second, 60 * second);
    }

    public Server() {
        try {
            this.baseConnect = new MQConnect(MQFactory.getConsumer(ConfigData.getBaseQueueDestination()));
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

    /**
     * 从client接受到消息
     * @param message
     */
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

    /**
     * 发送回执消息给client
     * @param flag
     * @param connect
     * @throws JMSException
     */
    public void sendQueue(boolean flag, final MQConnect connect) throws JMSException {
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

                connectArrayListMap.put(aaMessage.getFinalMessage().getStringProperty("userName"), new ArrayList<Date>());
                validLoginCount += 1;

                connect.addMessageHandler(new MessageListener() {
                    @Override
                    public void onMessage(Message message) {
                        try {
                            int type = message.getIntProperty("type");
                            if (type == 777) {
                                System.out.println("aaa777777777");
                                //TODO: 请求重连，回复888，并且不再ignore
                                AAMessage reloginPermitMessage = new AAMessage(888, "Relogin Successfully");
                                connect.sendMessage(reloginPermitMessage.getFinalMessage());
                            }else {
                                //TODO: 做次数检测
                                String isMessageValidStr = isMessageValid(message, connect);
                                if (isMessageValidStr.equals("ok")) {
                                    sendTopic(message);
                                }else {
                                    //TODO: invalidMessage +1
                                }
                            }
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

    /**
     * 合法消息发送到topic中
     * @param message
     * @throws JMSException
     */
     public void sendTopic(Message message) throws JMSException {
        topicConnect.sendMessage(message);
        forwardedMessageCount += mqConnects.size();
     }


    /**
     * 检查某一个client是不是已经在已允许的列表中
     * @param connect
     * @return
     * @throws JMSException
     */
    public boolean isConnectExist (MQConnect connect) throws JMSException {
        for(int i = 0; i< mqConnects.size(); i++) {
            if (mqConnects.get(i).getMessageProducer().getDestination().equals(connect.getMessageProducer().getDestination())) {
                System.out.println("mqConnect i : " + mqConnects.get(i).getMessageProducer().getDestination());
                return true;
            }
        }
        return false;
    }

    /**
     * 判断消息是否合法
     * @param message
     * @param connect
     * @return
     * @throws JMSException
     */
    public String isMessageValid (Message message, MQConnect connect) throws JMSException {
        Date now = new Date();
        String userName = message.getStringProperty("userName");
        if (connectArrayListMap.containsKey(message.getStringProperty("userName")) ) {
            ArrayList<Date> dates = connectArrayListMap.get(message.getStringProperty("userName"));
//            if (dates.size() > )
            if (dates.size() > 5) {
                Date lastFive = dates.get(dates.size() - 5);
                long interval = TimeUtil.getTimeInterval(now, lastFive);
                if (interval < 1) {
                    //TODO:ignore
                    return "ignore";
                }else {
                    //TODO: 判断100
                    if (dates.size() >= 100) {
                        //TODO: 断掉这个链接
                        //TODO: 发一个消息给client
                        //TODO: ignore后面的消息
                        AAMessage aaMessage = new AAMessage(999, "Redo Login");
                        connect.sendMessage(aaMessage.getFinalMessage());
                        connectArrayListMap.get(userName).clear();
                        return "ignore";
                    } else {
                        connectArrayListMap.get(userName).add(now);
                        return "ok";
                    }
                }
            }else {
                connectArrayListMap.get(userName).add(now);
                return "ok";
            }
        }else {
            //TODO 没有这个用户名
            return "strange";
        }

    }

}
