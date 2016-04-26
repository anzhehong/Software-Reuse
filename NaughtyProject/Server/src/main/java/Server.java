import com.API.Controller.DBAPI;
import com.HaroldLIU.PerformanceManager;
import license.PerSecondCountLicense;
import license.SumCountLicense;
import license.TZLicense;
import reuse.communication.entity.AAMessage;
import reuse.communication.MQ.MQConnect;
import reuse.communication.MQ.MQFactory;
import reuse.cm.ReadJson;
import reuse.license.MultiFrequencyRestriction;
import reuse.license.MultiMaxNumOfMessage;
import reuse.pm.PMManager;
//import reuse.pm.PMManager;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by MSI on 2016/3/27.
 */
public class Server {

    static public String outPath = "/Users/fowafolo/Desktop/Log/Server/";
    static public String jsonPath = "/Users/fowafolo/Desktop/test.json";
    static private int Sum;
    static private int Fre;
//    /**
//     * 合法输入次数/minutes.
//     */
//    private static int validLoginCount = 0;
//    /**
//     * 不合法输入次数/minute.
//     */
//    private static int inValidLoginCount = 0;
//    /**
//     * 转发消息数量.
//     */
//    private static int forwardedMessageCount = 0;
    /**
     * 登录日志的路径.
     */

    private static String loginLog =new ReadJson("/Users/Sophie/Software-Reuse/NaughtyProject/test.json").getStringConfig("loginLog");

    /**
     * 转发消息路径
     */
    private static String forwardedMessageLog = "ServerForwardedMessageLog.txt";

    /**
     * 秒钟
     */
    private static int second = 1000;

    /**
     * queue用来发送登录请求.
     * TODO: 注册
     */
    public  MQConnect baseConnect;

    /**
     * 私用queue发送消息给server.
     * (except login register request)
     */
    public MQConnect privateConnect;
    /**
     * 公用topic连接.
     */
    public MQConnect topicConnect;

    /**
     * 用来存收发消息的MQConnect.
     */
    protected ArrayList<MQConnect> mqConnects = new ArrayList<MQConnect>();
    /**
     * 用来记录每一个登录且发送过消息的client这次session的每次消息时间.
     */
//    private static Map<String, ArrayList<Date>> connectArrayListMap = new HashMap<String, ArrayList<Date>>();

//    private MultiFrequencyRestriction multiFrequencyRestriction;
//    private MultiMaxNumOfMessage multiMaxNumOfMessage;
    private PerformanceManager performanceManager;
    private Map<String,TZLicense> licenseSumMap;
    private Map<String,TZLicense> licenseFreMap;
//    /**
//     * 写日志的定时器.
//     */
//    public static Timer timer;
//
//    /**
//     * 定时器记录每分钟合法和不合法的消息个数.
//     */
//    static class WriteLoginTask extends TimerTask
//    {
//        public void run() {
//            //TODO: 把validLogin和invalidLogin记录到文件中
//            Date date = new Date();
////            PMManager.Write(loginLog, date + "\tValid Login Count: " + validLoginCount + "\tInvalid Login Count: " + inValidLoginCount, outPath);
////            PMManager.Write(forwardedMessageLog, date + "\tForwarded Message Count: " + forwardedMessageCount, outPath);
//            inValidLoginCount = 0;
//            validLoginCount = 0;
//            forwardedMessageCount = 0;
//
//        }
//    }


    public static void main(String[] args) throws JMSException {
        Server server = new Server();

//        server.timer = new Timer();
//        server.timer.schedule(new WriteLoginTask(), 5 * second, 5 * second);
    }

    public Server() {
        try {

            this.baseConnect = new MQConnect(MQFactory.getConsumer(new ReadJson("/Users/Sophie/Software-Reuse/NaughtyProject/test.json").getStringConfig("baseQueueDestination")));
            this.topicConnect = new MQConnect(MQFactory.getpublisher("Topic"));
//            this.multiFrequencyRestriction = new MultiFrequencyRestriction(Integer.parseInt(ReadJson.getStringConfig("CSMessage")));
//            this.multiMaxNumOfMessage = new MultiMaxNumOfMessage(Integer.parseInt(ReadJson.getStringConfig("CSSession")));
            this.performanceManager = new PerformanceManager("D:\\Server\\",1000);
            performanceManager.start();
            licenseSumMap = new HashMap<String,TZLicense>();
            licenseFreMap = new HashMap<String,TZLicense>();
            start();
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }

    public void start() {
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
    public void receiveQueue(Message message) {
        try {
            System.out.println("receiveQueue");
            String userName = message.getStringProperty("userName");
            String userPassword = message.getStringProperty("userPassword");
            boolean flag = DBAPI.CheckPassword(userName, userPassword);
            System.out.println("flag : " + flag);
            privateConnect = new MQConnect(MQFactory.getproducer("SC_" + userName), MQFactory.getConsumer("CS_" + userName));
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
                performanceManager.failTime++;
                //TODO: 测试 删除此privateConnect
//                boolean removeFlag = removeConnect(connect);
            } else {
                System.out.println("sendQueue");
                AAMessage aaMessage = new AAMessage(1, "Login Successfully");
                connect.sendMessage(aaMessage.getFinalMessage());
                mqConnects.add(connect);

                licenseSumMap.put(aaMessage.getFinalMessage().getStringProperty("userName"), new SumCountLicense(Sum));
                licenseFreMap.put(aaMessage.getFinalMessage().getStringProperty("userName"), new PerSecondCountLicense(Fre));
//                multiMaxNumOfMessage.addMap(aaMessage.getFinalMessage().getStringProperty("userName"));
//                multiFrequencyRestriction.addMap(aaMessage.getFinalMessage().getStringProperty("userName"));
//                connectArrayListMap.put(aaMessage.getFinalMessage().getStringProperty("userName"), new ArrayList<Date>());
                performanceManager.successTime++;

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
                            } else {
                                //TODO: 做次数检测
                                String isMessageValidStr = isMessageValid(message, connect);
                                if (isMessageValidStr.equals("ok")) {
                                    sendTopic(message);

                                    //将信息输入到文件
                                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    String str = df.format(new Date()).split(" ")[0];
                                    String contentStored = message.getStringProperty("userName")+"\t"+
                                            message.getStringProperty("content")+"\t"+
                                            message.getStringProperty("createdTime");
                                    ReadJson readJson = new ReadJson("/Users/Sophie/Software-Reuse/NaughtyProject/test.json");
                                    File file = new File(readJson.getStringConfig("sourcePath"));
                                    File[] files = file.listFiles();
                                    int flag = 0;
                                    for(int i = 0;i < files.length;i++){
                                       // System.out.println(files[i].getName());
                                        if(files[i].getName().equals("yserver"+str))
                                        {
                                           // System.out.println("xiangdeng");
                                            PMManager.Write("client"+str+"-v2",contentStored,readJson.getStringConfig("sourcePath")+"/");
                                            flag = 1;
                                            break;
                                        }
                                    }
                                    if(flag == 0) {
                                        PMManager.Write("client" + str, contentStored, readJson.getStringConfig("sourcePath") + "/");
                                    }
                                    PMManager.Write("server"+str,contentStored,readJson.getStringConfig("sourcePath"));

                                } else {
                                    //TODO: invalidMessage +1
                                }
                            }
                        } catch (JMSException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        } else {
            AAMessage aaMessage = new AAMessage(2, "Validation Failed.");
            connect.sendMessage(aaMessage.getFinalMessage());
            performanceManager.failTime++;
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
//        forwardedMessageCount += mqConnects.size();
     }


    /**
     * 检查某一个client是不是已经在已允许的列表中
     * @param connect
     * @return
     * @throws JMSException
     */
    public boolean isConnectExist(MQConnect connect) throws JMSException {
        for (int i = 0; i< mqConnects.size(); i++) {
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
    public String isMessageValid(Message message, MQConnect connect) throws JMSException {

        boolean sessionFlag =  licenseSumMap.get(message.getStringProperty("userName")).tryAcquire();
        boolean secondFlag = licenseFreMap.get(message.getStringProperty("userName")).tryAcquire();

        if (!secondFlag) {
            return "ignore";
        }else  {
            if (!sessionFlag) {
                AAMessage aaMessage = new AAMessage(999, "Redo Login");
                connect.sendMessage(aaMessage.getFinalMessage());
                licenseSumMap.get(message.getStringProperty("userName")).reset();
                licenseFreMap.get(message.getStringProperty("userName")).reset();
                return "ignore";
            }else {

                return "ok";
            }
        }
    }

}
