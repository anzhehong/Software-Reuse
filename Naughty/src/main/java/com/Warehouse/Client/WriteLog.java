package com.Warehouse.Client;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Date;

/**
 * Created by MSI on 2016/3/28.
 */
public class WriteLog {


    public static void write(String fileName, String content) {
        try {
            // 打开一个随机访问文件流，按读写方式
            RandomAccessFile randomFile = new RandomAccessFile("log/" + fileName, "rw");
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

    //记录登录记录，包括时间、client使用的username、是否成功
    public static void writeLoginForClient(String ClientName , boolean result){
        Date date = new Date();
        write("Log.txt","Client: " + ClientName + " login " + result + " at " + date);
    }

    public static void createNewFile(String name){

    }


}

