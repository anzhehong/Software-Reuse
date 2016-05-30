package reuse.PersistServer;

import reuse.cm.ReadJson;
import reuse.communication.MQ.MQConnect;
import reuse.communication.MQ.MQFactory;
import reuse.pm.PMManager;
import reuse.utility.ClassUtil;
import reuse.utility.Zip;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import static reuse.utility.Zip.Zip;

/**
 * Created by fowafolo
 * Date: 16/5/26
 * Time: 01:12
 */
public class Server {

    static public String jsonPath = "../Resources/test.json";
    private String errorOutPath = new ReadJson(jsonPath).getStringConfig("ErrorLogPath")+ File.separator;
    private String errorOutName = "ErrorLog.txt";

    private String debugOutPath = new ReadJson(jsonPath).getStringConfig("DebugLogPath")+File.separator;
    private String debugOutName = "DebugLog.txt";

    static ReadJson readJson = new ReadJson(jsonPath);
    static  String filename = readJson.getStringConfig("sourcePath");

    /**
     * 私用,用来转发验证请求给Center Server
     */
    public MQConnect queueConnect;

    public static void main(String[] args) {
        Server server = new Server();
    }

    public Server() {
        try {
            this.queueConnect = new MQConnect(MQFactory.getproducer("Persist_Center"+new ReadJson(jsonPath).getStringConfig("persistServerDestination")),
                    MQFactory.getConsumer("Center_Persist"+new ReadJson(jsonPath).getStringConfig("persistServerDestination")));

            Date executeDate =  new Date();
            Server.TaskDaily taskDaily = new TaskDaily();
            Server.TaskWeekly taskWeekly = new TaskWeekly();
            Server.LogDaily logDaily = new LogDaily();
            Server.LogWeekly logWeekly = new LogWeekly();

            Timer timerDaily  = new Timer();
            Timer timerWeekly  = new Timer();
            Timer FirsrLogZipInterval = new Timer();
            Timer SecondLogZipInterval = new Timer();

            timerDaily.schedule(taskDaily,executeDate,readJson.getIntConfig("FirsrZipInterval"));
            timerWeekly.schedule(taskWeekly,executeDate,readJson.getIntConfig("SecondZipInterval"));
            FirsrLogZipInterval.schedule(logDaily,executeDate,readJson.getIntConfig("FirsrLogZipInterval"));
            SecondLogZipInterval.schedule(logWeekly,executeDate,readJson.getIntConfig("SecondLogZipInterval"));

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

                    //写入消息
                    String contentStored  = null;

                    try {

                        System.out.println("written...");
                        contentStored = message.getStringProperty("userName")+"\t"+
                                message.getStringProperty("content")+"\t"+
                                message.getStringProperty("createdTime");
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }

                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH mm ss");
                        String DateStr = df.format(new Date());

                    PMManager.WriteMsg(filename,contentStored,DateStr,"Server");

                }
            });
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }

    private  class TaskDaily extends TimerTask {
        private  TaskDaily(){

        }

        public void run(){
            Zip.zipDaily();
        }

    }


    private  class TaskWeekly extends  TimerTask{
        private  TaskWeekly(){

        }

        public void run(){
            Zip.zipWeekly();
        }

    }
    private  class LogDaily extends  TimerTask{
        private  LogDaily(){

        }

        public void run(){
            Zip.LogDaily();
        }

    }

    private  class LogWeekly extends  TimerTask{
        private  LogWeekly(){

        }

        public void run(){
            Zip.LogWeekly();
        }

    }
}
