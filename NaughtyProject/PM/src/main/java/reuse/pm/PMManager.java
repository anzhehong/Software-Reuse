package reuse.pm;

import reuse.cm.ReadJson;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Date;
/**
 * Created by MSI on 2016/3/28.
 */
public class PMManager {
    /**
     * 写文件
     * @param fileName 文件路径
     * @param content  内容
     */
    private static int maxSize = 2;

    private static double maxSumSize = 10;

    private static String jsonPath =  "../Resources/test.json";

    private static ReadJson readJson = new ReadJson(jsonPath);

    public static void setMaxSize(int maxSize) {
        PMManager.maxSize = maxSize;
    }

    /**
     * 检查文件夹大小
     * @param path 文件路径
     */
    private static double getDirSize(String path){
        return _getDirSize(new File(path));
    }

    public static void setMaxSumSize(double maxSumSize) {
        PMManager.maxSumSize = maxSumSize;
    }

    private static double _getDirSize(File file) {
        //判断文件是否存在
        if (file.exists()) {
            //如果是目录则递归计算其内容的总大小
            if (file.isDirectory()) {
                File[] children = file.listFiles();
                double size = 0;
                for (File f : children)
                    size += _getDirSize(f);
                return size;
            } else {//如果是文件则直接返回其大小,以“兆”为单位
                double size = (double) file.length() / 1024 / 1024;
                return size;
            }
        } else {
            System.out.println("文件或者文件夹不存在，请检查路径是否正确！");
            return 0.0;
        }
    }
    public static void Write(String fileName, String content, String outPath) {
        PMManager.writeLog(fileName, content, outPath);
    }

    //普通的写消息
    public static void WriteMsg(String fileName, String content, String outPath) {
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

    //加入控制文件和文件夹大小的写消息,分辨是server还是client端
    public static void WriteMsg(String filename,String contentStored,String DateStr,String type){

        File overallfile = new File(filename);
        File[] overallfiles = overallfile.listFiles();
        int overallfilesFlag = 0;

        for(int k = 0;k< overallfiles.length;k++){

            if(overallfiles[k].length() + contentStored.getBytes().length <= readJson.getLongConfig("OverallFileMaxSize") ){
                File[] singlefiles = overallfiles[k].listFiles();
                int singlefilesFlag = 0;
                for(int i = 0; i < singlefiles.length;i++){
                    if(singlefiles[i].getName().substring(0,6).equals(type)&&singlefiles[i].length()+contentStored.getBytes().length <= readJson.getLongConfig("SingleFileMaxSize")){
                        PMManager.WriteMsg(singlefiles[i].getName(),contentStored,readJson.getStringConfig("sourcePath")+File.separator+overallfiles[k].getName()+File.separator);
                        singlefilesFlag = 1;
                        break;
                    }
                }
                if(singlefilesFlag == 0){
                    PMManager.WriteMsg(type+DateStr,contentStored,readJson.getStringConfig("sourcePath")+File.separator+overallfiles[k].getName()+File.separator);
                }
            }
            overallfilesFlag = 1;
            break;
        }

        if(overallfilesFlag == 0){
            File tmp = new File(readJson.getStringConfig("sourcePath"),DateStr);
            tmp.mkdir();
            PMManager.Write(type+DateStr+".txt",contentStored,tmp+File.separator);
        }
    }

    public static void ErrorLog(String fileName, String contentMsg, String className, int lineNumber, String outPath) {
        boolean flag = ReadJson.getBooleanConfig("ErrorLogSwitch");
        contentMsg = className + ": " + lineNumber + "  " + contentMsg;
        if (flag) {
            PMManager.writeLog(fileName, contentMsg, outPath);
        }else {

        }
    }

    public static void DebugLog(String fileName, String contentMsg, String className, int lineNumber, String outPath) {
        boolean flag = ReadJson.getBooleanConfig("DebugLogSwitch");
        contentMsg = className + ": " + lineNumber + "  " + contentMsg;
        if (flag) {
            PMManager.writeLog(fileName, contentMsg, outPath);
        }else {

        }
    }

    private static void writeLog (String fileName, String content, String outPath) {
        try {
            //判断文件夹大小是否超出
            if(getDirSize(outPath) < maxSumSize){
                // 打开一个随机访问文件流，按 读写方式
                RandomAccessFile randomFile = new RandomAccessFile(outPath + fileName, "rw");
                // 文件长度，字节数
                long fileLength = randomFile.length();
                //判断文件是否超过规定大小，以kb为单位，向上取整
                if((int)(fileLength/1024)+1 < maxSize) {
                    // 将写文件指针移到文件尾。
                    randomFile.seek(fileLength);
                    randomFile.writeBytes(content + "\r\n");
                    randomFile.close();
                }else{
                    Write("#" + fileName ,content,outPath);
                }}else{
                JOptionPane.showMessageDialog(null, "文件夹大小超过限制！", null, JOptionPane.ERROR_MESSAGE);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 写加密文件
     * @param fileName 文件路径
     * @param content  内容
     */
    public static void encipherWrite(String fileName, String content, String outPath) {
        try {
            // 打开一个随机访问文件流，按读写方式
            if(getDirSize(outPath) < maxSumSize){
            RandomAccessFile randomFile = new RandomAccessFile(outPath + fileName, "rw");
            // 文件长度，字节数
            long fileLength = randomFile.length();
            if((int)(fileLength/1024)+1 < maxSize) {
                // 将写文件指针移到文件尾。
                randomFile.seek(fileLength);
                content = encipher(content);
                randomFile.writeBytes(content + "\r\n");
                randomFile.close();
            }else{
                encipherWrite("#" + fileName,content,outPath);
            }}else{
                JOptionPane.showMessageDialog(null, "文件夹大小超过限制！", null, JOptionPane.ERROR_MESSAGE);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * String 加密
     * @param content 内容
     * @return 加密后的 String
     */
    private static String encipher(String content){
        return Hex.encodeHexStr(content.getBytes());
    }

    /**
     * String 解密
     * @param content 加密内容
     * @return 解密后的 String
     */
    private static String decodeHex(String content){
        return new String(Hex.decodeHex(content.toCharArray()));
    }
    /**
     * 记录登录记录，包括时间、client使用的username、是否成功
     * @param ClientName
     * @param result
     */
    public static void WriteLoginForClient(String ClientName , boolean result, String outPath){
        Date date = new Date();
        Write("Log.txt","Client: " + ClientName + " login " + result + " at " + date, outPath);
    }

    public static void main(String[] args) {
        String path = "../Resources/out/Log/Client";
        double size = PMManager.getDirSize(path);
        System.out.println(size);
    }

}
