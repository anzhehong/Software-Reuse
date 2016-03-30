package com.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by fowafolo
 * Date: 16/3/29
 * Time: 21:39
 */
public class ConfigData {
    @Autowired
    GlobalConfig globalConfig;

    /**
     * 拿到Spring注入IOC的实例
     * @return
     */
    static public ConfigData getConfigData() {
        ApplicationContext beanFactory;
        beanFactory = new ClassPathXmlApplicationContext("/WEB-INF/applicationContext.xml");

        ConfigData configData = (ConfigData) beanFactory.getBean("configData");
        return configData;
    }

    /**
     * 下面的方法可直接调用
     * @return
     */
    /**
     * ActiveMQ的一些基本配置
     * @return
     */
    public static String getBaseQueueDestination (){
        return ConfigData.getConfigData().globalConfig.getBaseQueueDestination();
    }

    public static String getMqHost() {
        return ConfigData.getConfigData().globalConfig.getMqHost();
    }
    /**
     * 请求类型
     * @return
     */


    public static String getRequestLogin() {
        return ConfigData.getConfigData().globalConfig.getRequestLogin();
    }

    public static String getLoginPermitted() {
        return ConfigData.getConfigData().globalConfig.getLoginPermitted();
    }

    public static String getLoginRefused() {
        return ConfigData.getConfigData().globalConfig.getLoginRefused();
    }

    public static String getRedoLogin() {
        return ConfigData.getConfigData().globalConfig.getRedoLogin();
    }

    public static String getReLoginPermitted() {
        return ConfigData.getConfigData().globalConfig.getReLoginPermitted();
    }

    public static String getCSMessage() {
        return ConfigData.getConfigData().globalConfig.getCSMessage();
    }

    public static String getRequestReLogin() {
        return ConfigData.getConfigData().globalConfig.getRequestReLogin();
    }

    /**
     * Log
     */
    public static String getLoginLog() {
        return ConfigData.getConfigData().globalConfig.getLoginLog();
    }

    public static String getLoginLogSecond() {
        return ConfigData.getConfigData().globalConfig.getLoginLogSecond();
    }
}
