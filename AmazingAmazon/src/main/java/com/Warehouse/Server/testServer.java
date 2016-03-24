package com.Warehouse.Server;

import javax.jms.JMSException;
import javax.jms.Message;

/**
 * Created by MSI on 2016/3/23.
 */
public class testServer {
    public static void main(String[] args) throws JMSException {
        Server_base server_base = new Server_base("localhost","61616","testQueue");
        server_base.start();
        server_base.sendMessage();
        server_base.close();

    }

}
