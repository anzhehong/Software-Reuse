package com.Communication;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * Created by fowafolo
 * Date: 16/3/30
 * Time: 01:37
 */
public class MQConnectTest {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(MQConnectTest.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        System.out.println(result.wasSuccessful());
    }
}