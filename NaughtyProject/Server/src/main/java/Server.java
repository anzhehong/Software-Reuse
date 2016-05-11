import com.API.Controller.DBAPI;
import reuse.cm.ReadJson;
import reuse.communication.MQ.MQConnect;
import reuse.communication.MQ.MQFactory;
import reuse.communication.entity.AAMessage;
import reuse.license.MultiFrequencyRestriction;
import reuse.license.MultiMaxNumOfMessage;
import reuse.pm.PMManager;
import reuse.utility.AAEncryption;
import reuse.utility.Zip;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.io.File;
import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by MSI on 2016/3/27.
 */
public class Server {

    static public String outPath = "../Resources/out/Log/Server/";
    static public String jsonPath = "../Resources/test.json";


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
     * 公用topic连接.
     */
//    public MQConnect topicConnect;
//    public ArrayList<Integer> topicGroups;
//    public ArrayList<MQConnect> topicConnects;
    public Map<Integer, MQConnect> topicConnects = new HashMap<Integer, MQConnect>();
    /**
     * 用来存收发消息的MQConnect.
     */
    protected ArrayList<MQConnect> mqConnects = new ArrayList<MQConnect>();
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
            PMManager.encipherWrite(loginLog, date + "\tValid Login Count: " + validLoginCount + "\tInvalid Login Count: " + inValidLoginCount, outPath);
            PMManager.encipherWrite(forwardedMessageLog, date + "\tForwarded Message Count: " + forwardedMessageCount, outPath);
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
            this.multiFrequencyRestriction = new MultiFrequencyRestriction(Integer.parseInt(new ReadJson(jsonPath).getStringConfig("CSMessage")));
            this.multiMaxNumOfMessage = new MultiMaxNumOfMessage((new ReadJson(jsonPath).getIntConfig("CSSession")));

            Date executeDate =  new Date();
            Server.TaskDaily taskDaily = new TaskDaily();
            Server.TaskWeekly taskWeekly = new TaskWeekly();
            Timer timerDaily  = new Timer();
            Timer timerWeekly  = new Timer();

            timerDaily.schedule(taskDaily,executeDate,new ReadJson(jsonPath).getIntConfig("FirsrZipInterval"));
            timerWeekly.schedule(taskWeekly,executeDate,new ReadJson(jsonPath).getIntConfig("SecondZipInterval"));
            start();
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }

    private  class TaskDaily extends  TimerTask{
        private  TaskDaily(){

        }

        public void run(){
            //执行打包功能
         Zip.zipWeekly();

        }

    }
    private  class TaskWeekly extends  TimerTask{
        private  TaskWeekly(){

        }

        public void run(){
            //解压再压缩
            Zip.zipWeekly();
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

            Map<String, Object> dbResult = DBAPI.checkPasswordAndGetGroup(userName, userPassword);
            boolean flag = ((Boolean)dbResult.get("error")).booleanValue();
            System.out.println("flag : " + flag);
            privateConnect = new MQConnect(MQFactory.getproducer("SC_" + userName), MQFactory.getConsumer("CS_" + userName));
            if (!flag) {
                sendQueue(!flag, privateConnect, dbResult.get("groupId"));
            }else {
                sendQueue(!flag, privateConnect, dbResult.get("errorMsg"));
            }
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
                mqConnects.add(connect);

                multiMaxNumOfMessage.addMap(aaMessage.getFinalMessage().getStringProperty("userName"));
                multiFrequencyRestriction.addMap(aaMessage.getFinalMessage().getStringProperty("userName"));
//                connectArrayListMap.put(aaMessage.getFinalMessage().getStringProperty("userName"), new ArrayList<Date>());
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
                            } else {
                                //TODO: 做次数检测
                                String isMessageValidStr = isMessageValid(message, connect);
                                if (isMessageValidStr.equals("ok")) {
                                    //TODO: hhhhhhhhhhhhhhhhailsdfjlasdfjaiosdfjoadsifjaeoirfjaeiorwf

                                    sendTopic(message, groupId);
                                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    String DateStr = df.format(new Date());
                                    //存储的消息
                                    String contentStoredUnencrypted  = groupId+"\t"+message.getStringProperty("userName")+"\t"+
                                            message.getStringProperty("content")+"\t"+
                                            message.getStringProperty("createdTime");
                                    ArrayList<Object> result = AAEncryption.DefaultEncryptString(contentStoredUnencrypted);

                                    String contentStored = (String)result.get(0);
                                    //消息文件的路径
                                    ReadJson readJson = new ReadJson(jsonPath);

                                    //zhunbei
                                    File overallfile = new File(readJson.getStringConfig("sourcePath"));
                                    File[] overallfiles = overallfile.listFiles();
                                    int overallfilesFlag = 0;
                                    for(int k = 0;k< overallfiles.length;k++){

                                        if(overallfiles[k].length() + contentStored.getBytes().length <= readJson.getLongConfig("OverallFileMaxSize") ){
                                            File[] singlefiles = overallfiles[k].listFiles();
                                            int singlefilesFlag = 0;
                                            for(int i = 0; i < singlefiles.length;i++){
                                                if(singlefiles[i].getName().substring(0,6).equals("server")&&singlefiles[i].length()+contentStored.getBytes().length <= readJson.getLongConfig("SingleFileMaxSize")){
                                                    PMManager.encipherWrite(singlefiles[i].getName(), contentStored, readJson.getStringConfig("sourcePath") + File.separator + overallfiles[k].getName() + File.separator);
                                                    singlefilesFlag = 1;
                                                    break;
                                                }
                                            }
                                            if(singlefilesFlag == 0){
                                                PMManager.encipherWrite("server" + DateStr, contentStored, readJson.getStringConfig("sourcePath") + File.separator + overallfiles[k].getName() + File.separator);
                                            }
                                        }
                                        overallfilesFlag = 1;
                                        break;
                                    }
                                    if(overallfilesFlag == 0){
                                        File tmp = new File(readJson.getStringConfig("sourcePath"),DateStr);
                                        tmp.mkdir();
                                        PMManager.encipherWrite("server" + DateStr, contentStored, tmp + File.separator);
                                    }

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
         MQConnect thisConnect;
         if (topicConnects.containsKey(groupId)) {
             thisConnect = topicConnects.get(groupId);
         }else {
             thisConnect = new MQConnect((MQFactory.getpublisher("Topic_" + groupId)));
             topicConnects.put(groupId, thisConnect);
         }
        thisConnect.sendMessage(message);
        forwardedMessageCount += mqConnects.size();
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

                return "ok";
            }
        }
    }

}
