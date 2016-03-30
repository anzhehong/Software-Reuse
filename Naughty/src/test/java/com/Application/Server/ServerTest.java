package com.Application.Server;

import com.Communication.MQConnect;
import com.Communication.MQFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by fowafolo
 * Date: 16/3/30
 * Time: 01:52
 */
public class ServerTest {

    Server server = new Server();
    /**
     * 用来记录每一个登录且发送过消息的client这次session的每次消息时间
     */
    private static Map<String, ArrayList<Date>> connectArrayListMap = new HashMap<String, ArrayList<Date>>();

    @Before
    public void befor() throws Exception {
        for (int i = 0; i < 10; i++){
            MQConnect connect = new MQConnect(MQFactory.getproducer("SC_" + i),MQFactory.getConsumer("CS_"+i));
            server.mqConnects.add(connect);
        }
    }

    @Test
    public void testIsConnectExist() throws Exception {
        /**
         * mqConnects得是protected或者是private才能测试
         */
        MQConnect connect1 = new MQConnect(MQFactory.getproducer("SC_" + 1),MQFactory.getConsumer("CS_"+1));
        MQConnect connect2 = new MQConnect(MQFactory.getproducer("SC_" + 20),MQFactory.getConsumer("CS_"+20));

        System.out.println(server.isConnectExist(connect1));
        assertEquals(true, server.isConnectExist(connect1));

        System.out.println("Connectiong 2: ");
        assertEquals(false, server.isConnectExist(connect2));
    }

    @Test
    public void testIsMessageValid() throws Exception {
        /**
         * TODO：测试一下这个方法，具体步骤：
         * 先在before中设置Message，connectArrayListMap和Connect
         * 然后在此调用server.isMessageVilid，用assertEquals(理想值，实际值)来看方法是否正确。
         */

    }
}