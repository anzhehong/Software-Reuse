import com.API.Controller.DBAPI;
import reuse.cm.ReadJson;
import reuse.communication.MQ.MQConnect;
import reuse.communication.MQ.MQFactory;
import reuse.communication.entity.AAMessage;
import reuse.license.MultiFrequencyRestriction;
import reuse.license.MultiMaxNumOfMessage;
import reuse.pm.PMManager;
import reuse.utility.ClassUtil;
import reuse.utility.Zip;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

import static reuse.utility.Zip.Zip;

/**
 * Created by MSI on 2016/3/27.
 */
public class Server {

    static public String outPath = "../Resources/out/Log/Server/";
    static public String jsonPath = "../Resources/test.json";

    private String errorOutPath = new ReadJson(jsonPath).getStringConfig("ErrorLogPath")+File.separator;
    private String errorOutName = "ErrorLog.txt";

    private String debugOutPath = new ReadJson(jsonPath).getStringConfig("DebugLogPath")+File.separator;
    private String debugOutName = "DebugLog.txt";

    /**
     * 合法输入次数/minutes.
     */
    private static int validLoginCount = 0;
    /**
     * 不合法输入次数/minute.
     */
    private static int inValidLoginCount = 0;
    /**
     * 转发消息数量.
     */
    private static int forwardedMessageCount = 0;
    /**
     * 登录日志的路径.
     */
    private static String loginLog = new ReadJson(jsonPath).getStringConfig("loginLog");

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
     * 私用,用来转发验证请求给Auth Server
     */
    public MQConnect authConnect;

    /**
     * 私用,用来转发验证请求给Auth Server
     */
    public MQConnect persistConnect;

    /**
     * 私用,用来转发验证请求给Auth Server
     */
    public MQConnect forwardConnect;

    /**
     * 公用topic连接.
     */
//    public MQConnect topicConnect;
//    public ArrayList<Integer> topicGroups;
//    public ArrayList<MQConnect> topicConnects;

    /**
     * 用来存收发消息的MQConnect.
     */

    protected ArrayList<MQConnect> mqConnects = new ArrayList<MQConnect>();
    /**
     * 用来存登录信息
     */
    public static Map<String,Integer> logInUser = new HashMap<>();
    /**
     * 用来记录每一个登录且发送过消息的client这次session的每次消息时间.
     */
//    private static Map<String, ArrayList<Date>> connectArrayListMap = new HashMap<String, ArrayList<Date>>();

    private MultiFrequencyRestriction multiFrequencyRestriction;
    private MultiMaxNumOfMessage multiMaxNumOfMessage;

    /**
     * 写日志的定时器.
     */
    public static Timer timer;

    /**
     * 定时器记录每分钟合法和不合法的消息个数.
     */
    static class WriteLoginTask extends TimerTask
    {
        public void run() {
            //TODO: 把validLogin和invalidLogin记录到文件中
            Date date = new Date();
            PMManager.Write(loginLog, date + "\tValid Login Count: " + validLoginCount + "\tInvalid Login Count: " + inValidLoginCount, outPath);
            PMManager.Write(forwardedMessageLog, date + "\tForwarded Message Count: " + forwardedMessageCount, outPath);
            inValidLoginCount = 0;
            validLoginCount = 0;
            forwardedMessageCount = 0;

        }
    }


    public static void main(String[] args) throws JMSException {
        Server server = new Server();
        server.timer = new Timer();
        server.timer.schedule(new WriteLoginTask(), 5 * second, 5 * second);
    }

    public Server() {
        try {
            this.baseConnect = new MQConnect(MQFactory.getConsumer(new ReadJson(jsonPath).getStringConfig("baseQueueDestination")));
//            this.topicConnect = new MQConnect(MQFactory.getpublisher("Topic"));

            this.authConnect = new MQConnect(MQFactory.getproducer("Center_Auth"+new ReadJson(jsonPath).getStringConfig("authServerDestination")),
                    MQFactory.getConsumer("Auth_Center"+new ReadJson(jsonPath).getStringConfig("authServerDestination")));

            this.persistConnect = new MQConnect(MQFactory.getproducer("Center_Persist"+new ReadJson(jsonPath).getStringConfig("persistServerDestination")),
                    MQFactory.getConsumer("Persist_Center"+new ReadJson(jsonPath).getStringConfig("persistServerDestination")));

            this.forwardConnect = new MQConnect(MQFactory.getproducer("Center_Forward"+new ReadJson(jsonPath).getStringConfig("forwardServerDestination")),
                    MQFactory.getConsumer("Forward_Center"+new ReadJson(jsonPath).getStringConfig("forwardServerDestination")));

            this.multiFrequencyRestriction = new MultiFrequencyRestriction(Integer.parseInt(new ReadJson(jsonPath).getStringConfig("CSMessage")));
            this.multiMaxNumOfMessage = new MultiMaxNumOfMessage((new ReadJson(jsonPath).getIntConfig("CSSession")));

            Date executeDate =  new Date();



            start();
        } catch (JMSException e) {
            e.printStackTrace();
            PMManager.ErrorLog(errorOutName, e.toString(), this.getClass().getName(), ClassUtil.getLineNumber()
                    , errorOutPath);
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
//            e.printStackTrace();
            PMManager.ErrorLog(errorOutName, e.toString(), this.getClass().getName(), ClassUtil.getLineNumber()
                    , errorOutPath);
        }
    }

    /**
     * 从client接受到消息
     * @param message
     */
    public void receiveQueue(Message message) {
        try {
            PMManager.DebugLog(debugOutName,"receive login message",this.getClass().getName(),ClassUtil.getLineNumber(),debugOutPath);
            System.out.println("receiveQueue");
            String userName = null;
            userName = message.getStringProperty("userName");
            String userPassword = message.getStringProperty("userPassword");
            Map<String, Object> dbResult = DBAPI.checkPasswordAndGetGroup(userName, userPassword);

            authConnect.sendMessage(message);

            String finalUserName = userName;
            authConnect.addMessageHandler(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    System.out.println("received a reply from auth server");
                    try {
//                        boolean flag = ((Boolean)dbResult.get("error")).booleanValue();
                        boolean flag = message.getBooleanProperty("loginPermit");
                        System.out.println("flag : " + flag);

                        privateConnect = new MQConnect(MQFactory.getproducer("SC_" + finalUserName), MQFactory.getConsumer("CS_" + finalUserName));
                        if (!flag) {
                            PMManager.DebugLog(debugOutName,"send login success message",this.getClass().getName(),ClassUtil.getLineNumber(),debugOutPath);
                            logInUser.put(finalUserName,(int)dbResult.get("groupId"));
                            sendQueue(!flag, privateConnect, dbResult.get("groupId"));
                        }else {
                            PMManager.DebugLog(debugOutName,"send login failed message",this.getClass().getName(),ClassUtil.getLineNumber(),debugOutPath);
                            sendQueue(!flag, privateConnect, dbResult.get("errorMsg"));
                        }
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JMSException e) {
            e.printStackTrace();
            PMManager.ErrorLog(errorOutName, e.toString(), this.getClass().getName(), ClassUtil.getLineNumber()
                    , errorOutPath);
        }
    }

    /**
     * 发送回执消息给client
     * @param flag
     * @param connect
     * @throws JMSException
     */
    public void sendQueue(boolean flag, final MQConnect connect, Object thisObject) throws JMSException {
        if (flag) {
            if (isConnectExist(connect)) {
                System.out.println("already");
                AAMessage aaMessage = new AAMessage(2, "This Account has Already Logged in.");
                connect.sendMessage(aaMessage.getFinalMessage());
                inValidLoginCount += 1;
                //TODO: 测试 删除此privateConnect
//                boolean removeFlag = removeConnect(connect);
            } else {
                System.out.println("sendQueue");
                AAMessage aaMessage = new AAMessage(1, "Login Successfully");
                Message messageToSend = aaMessage.getFinalMessage();
                int groupId = (int) thisObject;
                messageToSend.setStringProperty("groupId","Topic_"+ groupId);
                connect.sendMessage(messageToSend);
                List<String> nameList = new ArrayList<>();
                for(Map.Entry<String,Integer> entry: logInUser.entrySet()){
                    String username = entry.getKey();
                    int groupId_map = entry.getValue();

                    if(groupId_map == groupId){
                        nameList.add(username);
                    }
                }
                messageToSend.setObjectProperty("stateList",nameList);
                messageToSend.setStringProperty("content","");
                sendTopic(messageToSend, groupId);
                mqConnects.add(connect);

                multiMaxNumOfMessage.addMap(aaMessage.getFinalMessage().getStringProperty("userName"));
                multiFrequencyRestriction.addMap(aaMessage.getFinalMessage().getStringProperty("userName"));
//                connectArrayListMap.put(aaMessage.getFinalMessage().getStringProperty("userName"), new ArrayList<Date>());
                validLoginCount += 1;

                connect.addMessageHandler(new MessageListener(){
                    @Override
                    public void onMessage(Message message) {
                        try {
                            int type = message.getIntProperty("type");
                            if (type == 777) {
                                PMManager.DebugLog(debugOutName,"relogin message received",this.getClass().getName(),ClassUtil.getLineNumber(),debugOutPath);
                                System.out.println("aaa777777777");
                                //TODO: 请求重连，回复888，并且不再ignore
                                AAMessage reloginPermitMessage = new AAMessage(888, "Relogin Successfully");
                                connect.sendMessage(reloginPermitMessage.getFinalMessage());
                            } else {
                                //TODO: 做次数检测
                                String isMessageValidStr = isMessageValid(message, connect);
                                if (isMessageValidStr.equals("ok")) {
                                    //TODO:
                                    System.out.println("send_topic and groupid:" + groupId);
                                    send_Topic(message, groupId);
                                } else {
                                    //TODO: invalidMessage +1
                                }
                            }
                        } catch (JMSException e) {
                            e.printStackTrace();
                            PMManager.ErrorLog(errorOutName, e.toString(), this.getClass().getName(), ClassUtil.getLineNumber()
                                    , errorOutPath);
                        }
                    }
                });
            }
        } else {
            String errorMsg = String.valueOf(thisObject);
            AAMessage aaMessage = new AAMessage(2, errorMsg);
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
     public void sendTopic(Message message, int groupId) throws JMSException {
         Message message1 = message;
         message1.setIntProperty("group_Id", groupId);
         forwardConnect.sendMessage(message1);
         forwardedMessageCount += mqConnects.size();
//         persistConnect.sendMessage(message1);
         System.out.println("written a msg...");


     }


    public void send_Topic(Message message, int groupId) throws JMSException{
        String content = message.getStringProperty("content");
        System.out.println("content:" + content);
        AAMessage aaMessage = new AAMessage(123,content);
        Message message1 = aaMessage.getFinalMessage();
        message1.setIntProperty("group_Id", groupId);
        forwardConnect.sendMessage(message1);
        forwardedMessageCount += mqConnects.size();
        persistConnect.sendMessage(message1);

    }


    /**
     * 检查某一个client是不是已经在已允许的列表中
     * @param connect
     * @return
     * @throws JMSException
     */
    public boolean isConnectExist(MQConnect connect) throws JMSException {
//        for (int i = 0; i< mqConnects.size(); i++) {
//            if (mqConnects.get(i).getMessageProducer().getDestination().equals(connect.getMessageProducer().getDestination())) {
//                System.out.println("mqConnect i : " + mqConnects.get(i).getMessageProducer().getDestination());
//                return true;
//            }
//        }
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

        boolean sessionFlag =  multiMaxNumOfMessage.CheckByKey(message.getStringProperty("userName"));
        boolean secondFlag = multiFrequencyRestriction.CheckByKey(message.getStringProperty("userName"));

        if (!secondFlag) {
            return "ignore";
        }else  {
            if (!sessionFlag) {
                AAMessage aaMessage = new AAMessage(999, "Redo Login");
                connect.sendMessage(aaMessage.getFinalMessage());
                return "ignore";
            }else {
                PMManager.DebugLog(debugOutName,"message is valid",this.getClass().getName(),ClassUtil.getLineNumber(),debugOutPath);
                return "ok";
            }
        }
    }

}
