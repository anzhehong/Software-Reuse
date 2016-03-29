package com.Application.Server;

import com.Communication.MQConnect;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fowafolo
 * Date: 16/3/30
 * Time: 01:52
 */
public class ServerTest {

    /**
     * 用来存收发消息的MQConnect
     */
    private static ArrayList<MQConnect> mqConnects = new ArrayList<MQConnect>();
    /**
     * 用来记录每一个登录且发送过消息的client这次session的每次消息时间
     */
    private static Map<String, ArrayList<Date>> connectArrayListMap = new HashMap<String, ArrayList<Date>>();

    @Before
    public void befor() throws Exception {
        for (int i = 0; i < 10; i++){
            MQConnect
            mqConnects
        }
    }

    @Test
    public void testIsConnectExist() throws Exception {

    }

    @Test
    public void testIsMessageValid() throws Exception {

    }
}