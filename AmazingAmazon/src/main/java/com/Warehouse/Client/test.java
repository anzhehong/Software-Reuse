package com.Warehouse.Client;

import javax.jms.JMSException;
import javax.jms.Message;
import com.Warehouse.Client.Client_base;
import org.springframework.stereotype.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by MSI on 2016/3/23.
 */
//@Controller
public class test {

//
//    public static void main(String[] args) throws JMSException{
//
//
//        Client_base client_base = new Client_base("localhost","61616","testQueue");
//        client_base.start();
//        ClientInterface clientInterface = new ClientInterface();
//
//        System.out.println("test session:"+ client_base.getSession());
//        clientInterface.setSession(client_base.getSession());
//        System.out.println("test session interface:"+ clientInterface.getSession());
//        clientInterface.init();
//        String username = clientInterface.getUsername();
//        String password = clientInterface.getPassword();
//
//
//        Message message = client_base.getMessage();
//        String name = message.getStringProperty("name");
//        int id = message.getIntProperty("id");
//        System.out.println(name);
//        System.out.println(id);
//        client_base.closeConnection();
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//
//    }
}
