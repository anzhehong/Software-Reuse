package com.Communication;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * Created by fowafolo
 * Date: 16/3/30
 * Time: 01:46
 */
public class MQFactoryTest {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(MQConnectTest.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        System.out.println(result.wasSuccessful());
    }

    @Test
    public void testGetSession() throws Exception {

    }

    @Test
    public void testGetConsumer() throws Exception {

    }

    @Test
    public void testGetproducer() throws Exception {

    }

    @Test
    public void testGetpublisher() throws Exception {

    }

    @Test
    public void testGetSubscriber() throws Exception {

    }

    @Test
    public void testGetMessage() throws Exception {

    }
}