package reuse.AuthServer;

import reuse.cm.ReadJson;
import reuse.communication.MQ.MQConnect;
import reuse.communication.MQ.MQFactory;
import reuse.communication.entity.AAMessage;
import reuse.pm.PMManager;
import reuse.utility.ClassUtil;

import javax.jms.JMSException;
import javax.jms.Message;
import java.io.File;

/**
 * Created by fowafolo
 * Date: 16/5/25
 * Time: 23:47
 */
public class Server {

    static public String jsonPath = "../Resources/test.json";
    private String errorOutPath = new ReadJson(jsonPath).getStringConfig("ErrorLogPath")+ File.separator;
    private String errorOutName = "ErrorLog.txt";

    private String debugOutPath = new ReadJson(jsonPath).getStringConfig("DebugLogPath")+File.separator;
    private String debugOutName = "DebugLog.txt";

    /**
     * 私用,用来转发验证请求给Auth Server
     */
    public MQConnect queueConnect;

    public static void main(String[] args) {
        Server server = new Server();
    }

    public Server() {
        try {
            this.queueConnect = new MQConnect(MQFactory.getproducer("Auth_Center"+new ReadJson(jsonPath).getStringConfig("authServerDestination")),
                    MQFactory.getConsumer("Center_Auth"+new ReadJson(jsonPath).getStringConfig("authServerDestination")));
            start();
        } catch (JMSException e) {
            e.printStackTrace();
            PMManager.ErrorLog(errorOutName, e.toString(), this.getClass().getName(), ClassUtil.getLineNumber()
                    , errorOutPath);
        }
    }

    public void start() {
        AAMessage message = new AAMessage(123,"ooooo");
        try {
            Message message1 = message.getFinalMessage();
            queueConnect.sendMessage(message1);
        } catch (JMSException e) {
            e.printStackTrace();
        }

//        try {
//            queueConnect.addMessageHandler(new MessageListener() {
//                @Override
//                public void onMessage(Message message) {
//                    System.out.println("start");
//                    receiveQueue(message);
//                    System.out.println("from center server");
//                }
//            });
//        } catch (JMSException e) {
////            e.printStackTrace();
//            PMManager.ErrorLog(errorOutName, e.toString(), this.getClass().getName(), ClassUtil.getLineNumber()
//                    , errorOutPath);
//        }
    }

    public void receiveQueue(Message message) {

//        try {
//            PMManager.DebugLog(debugOutName,"receive login message",this.getClass().getName(),ClassUtil.getLineNumber(),debugOutPath);
//            System.out.println("receiveQueue");
//            String userName = message.getStringProperty("userName");
//            String userPassword = message.getStringProperty("userPassword");
//            Map<String, Object> dbResult = DBAPI.checkPasswordAndGetGroup(userName, userPassword);
//            boolean flag = ((Boolean)dbResult.get("error")).booleanValue();
//            System.out.println("flag : " + flag);
//            privateConnect = new MQConnect(MQFactory.getproducer("SC_" + userName), MQFactory.getConsumer("CS_" + userName));
//            System.out.println();
//            if (!flag) {
//                PMManager.DebugLog(debugOutName,"send login success message",this.getClass().getName(),ClassUtil.getLineNumber(),debugOutPath);
//                logInUser.put(userName,(int)dbResult.get("groupId"));
//                sendQueue(!flag, privateConnect, dbResult.get("groupId"));
//            }else {
//                PMManager.DebugLog(debugOutName,"send login failed message",this.getClass().getName(),ClassUtil.getLineNumber(),debugOutPath);
//                sendQueue(!flag, privateConnect, dbResult.get("errorMsg"));
//            }
//        } catch (JMSException e) {
//            e.printStackTrace();
//            PMManager.ErrorLog(errorOutName, e.toString(), this.getClass().getName(), ClassUtil.getLineNumber()
//                    , errorOutPath);
//        }
    }
}
