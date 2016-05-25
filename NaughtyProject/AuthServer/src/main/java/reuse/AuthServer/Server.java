package reuse.AuthServer;

import com.API.Controller.DBAPI;
import reuse.cm.ReadJson;
import reuse.communication.MQ.MQConnect;
import reuse.communication.MQ.MQFactory;
import reuse.communication.entity.AAMessage;
import reuse.pm.PMManager;
import reuse.utility.ClassUtil;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.io.File;
import java.util.Map;

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
     * 私用,用来转发验证请求给Center Server
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
        try {
            queueConnect.addMessageHandler(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    try {
                        String userName = null;
                        userName = message.getStringProperty("userName");
                        String userPassword = message.getStringProperty("userPassword");
                        Map<String, Object> dbResult = DBAPI.checkPasswordAndGetGroup(userName, userPassword);
                        boolean flag = ((Boolean)dbResult.get("error")).booleanValue();
                        AAMessage aaMessage = new AAMessage(33, "loginresult");
                        Message messageToSend = aaMessage.getFinalMessage();
                        messageToSend.setBooleanProperty("loginPermit", flag);
                        queueConnect.sendMessage(messageToSend);
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
}
