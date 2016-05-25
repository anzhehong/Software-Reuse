package reuse.ForwardServer;

import reuse.cm.ReadJson;
import reuse.communication.MQ.MQConnect;
import reuse.communication.MQ.MQFactory;
import reuse.pm.PMManager;
import reuse.utility.ClassUtil;

import javax.jms.JMSException;
import java.io.File;

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

    public void start() {

    }
}
