package reuse.ForwardServer;

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
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fowafolo
 * Date: 16/5/26
 * Time: 01:11
 */
public class Server {

    static public String jsonPath = "../Resources/test.json";
    private String errorOutPath = new ReadJson(jsonPath).getStringConfig("ErrorLogPath")+ File.separator;
    private String errorOutName = "ErrorLog.txt";

    private String debugOutPath = new ReadJson(jsonPath).getStringConfig("DebugLogPath")+File.separator;
    private String debugOutName = "DebugLog.txt";

    public Map<Integer, MQConnect> topicConnects = new HashMap<Integer, MQConnect>();

    /**
     * 私用,用来转发验证请求给Center Server
     */
    public MQConnect queueConnect;

    public static void main(String[] args) {
        Server server = new Server();
    }

    public Server() {
        try {
            this.queueConnect = new MQConnect(MQFactory.getproducer("Forward_Center"+new ReadJson(jsonPath).getStringConfig("forwardServerDestination")),
                    MQFactory.getConsumer("Center_Forward"+new ReadJson(jsonPath).getStringConfig("forwardServerDestination")));
            start();
        } catch (JMSException e) {
            e.printStackTrace();
            PMManager.ErrorLog(errorOutName, e.toString(), this.getClass().getName(), ClassUtil.getLineNumber()
                    , errorOutPath);
        }
    }

    public void start()  {
        try {
            queueConnect.addMessageHandler(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    try {
                        PMManager.DebugLog(debugOutName,"send message via topic",this.getClass().getName(),ClassUtil.getLineNumber(),debugOutPath);
                        MQConnect thisConnect;

                        int groupId = message.getIntProperty("group_Id");
                        System.out.println("groupId:" + groupId);
                        if (topicConnects.containsKey(groupId)) {
                            thisConnect = topicConnects.get(groupId);
                        }else {
                            thisConnect = new MQConnect((MQFactory.getpublisher("Topic_" + groupId)));
                            topicConnects.put(groupId, thisConnect);
                        }
                        thisConnect.sendMessage(message);
                        System.out.println("send message");
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
