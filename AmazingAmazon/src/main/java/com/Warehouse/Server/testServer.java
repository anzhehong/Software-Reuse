package com.Warehouse.Server;

import com.Warehouse.Client.ClientInterface;
import com.Warehouse.entity.User;
import com.Warehouse.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.jms.JMSException;
import javax.jms.Message;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Queue;

/**
 * Created by MSI on 2016/3/23.
 */
public class testServer {
    public static void main(String[] args) throws JMSException {
        ApplicationContext beanFactory;
        beanFactory = new ClassPathXmlApplicationContext("/WEB-INF/applicationContext.xml");
        testServer testserver = (testServer) beanFactory.getBean("server");
        while (true) {
            Server_base server_base = new Server_base("localhost", "61616", "testQueue");
            server_base.start();

            Message mesRec = server_base.getMessage();
            if (mesRec.getIntProperty("type") == 0) {
                String username = mesRec.getStringProperty("username");
                String password = mesRec.getStringProperty("password");
                server_base.sendMessage(1,testserver.checkPassword(username, password));
                writeLog("log1.txt", "QueueID: " + server_base.getQueueID() + "  action: log in " + " result:" + testserver.checkPassword(username, password));

            }
            if (mesRec.getIntProperty("type") == 2) {
                String username = mesRec.getStringProperty("username");
                String password = mesRec.getStringProperty("password");
                server_base.sendMessage(1,testserver.insert(username, password));
            }

            server_base.close();
        }

    }
    @Autowired
    MainService mainService;
    public boolean checkPassword(String username,String password)throws JMSException {
        User user = mainService.findByName(username);
        if(password.equals(user.getUserPassword()))
            return true;
        else
            return false;
    }

    public boolean insert(String username,String password)throws JMSException {
        User user = new User(username,password);
        mainService.insertUser(user);
        User user_ = mainService.findByName(username);
        if(user_.getUserPassword().equals(password))
            return true;
        else
            return false;

    }

    public static void writeLog(String fileName, String content) {
        try {
            // 打开一个随机访问文件流，按读写方式
            RandomAccessFile randomFile = new RandomAccessFile(fileName, "rw");
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
}






