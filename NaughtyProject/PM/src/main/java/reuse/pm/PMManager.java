package reuse.pm;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by MSI on 2016/3/28.
 */
public class PMManager {
    public  Timer timer;
    private static int inValidLoginCount;
    private static int validLoginCount;
    private static String filepath;
    private long delaySecond;

    public PMManager(String _filePath,int _delayMinute) {
            filepath = _filePath;
        delaySecond = _delayMinute * 60000;
    }

    public static void setFilepath(String filepath) {
        PMManager.filepath = filepath;
    }

    public void LogSuccess(){
        validLoginCount++;
    }

    public void LogFail(){
        inValidLoginCount++;
    }


    public void startRecord( ){
        timer = new Timer();
        timer.schedule(new WriteLoginTask(), delaySecond, delaySecond);

    }

    static class WriteLoginTask extends TimerTask
    {
        public void run() {
            //TODO: 把validLogin和invalidLogin记录到文件中
            String currentMins = new SimpleDateFormat("yyyy_MM_dd HH mm ss").format(Calendar.getInstance().getTime());
            String fileName = "LogInRecordAt" +currentMins;

//            System.out.println(filepath+fileName+ ".txt");
            PMManager.Write(fileName + ".txt", currentMins + "\tValid Login Count: " + validLoginCount + "\tInvalid Login Count: " + inValidLoginCount, filepath);
            inValidLoginCount = 0;
            validLoginCount = 0;
        }
    }

    /**
     * 写文件
     * @param fileName 文件路径
     * @param content  内容
     */
    public static void Write(String fileName, String content, String outPath) {
        try {
            // 打开一个随机访问文件流，按读写方式
            RandomAccessFile randomFile = new RandomAccessFile(outPath + fileName, "rw");
            // 文件长度，字节数
            long fileLength = randomFile.length();
            // 将写文件指针移到文件尾。
            randomFile.seek(fileLength);
            randomFile.writeBytes(content+"\r\n");
            randomFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    /**
//     * 记录登录记录，包括时间、client使用的username、是否成功
//     * @param ClientName
//     * @param result
//     */
//    public static void WriteLoginForClient(String ClientName , boolean result, String outPath){
//        Date date = new Date();
//        Write("Log.txt","Client: " + ClientName + " login " + result + " at " + date, outPath);
//    }

    public static void main(String[] args) {
//        PMManager pmManager = new PMManager("/Users/fowafolo/Desktop/1111/",1);
//        pmManager.startRecord();
//        pmManager.LogSuccess();
//        pmManager.LogSuccess();
//        pmManager.LogFail();
    }


}

