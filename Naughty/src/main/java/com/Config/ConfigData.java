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

    static public ConfigData getConfigData() {
        ApplicationContext beanFactory;
        beanFactory = new ClassPathXmlApplicationContext("/WEB-INF/applicationContext.xml");

        ConfigData configData = (ConfigData) beanFactory.getBean("configData");
        return configData;
    }

    public static String getBaseQueueDestination (){
        return ConfigData.getConfigData().globalConfig.getBaseQueueDestination();
    }

    public static String getRequestLogin() {
        return ConfigData.getConfigData().globalConfig.getRequestLogin();
    }

    public String getLoginPermitted() {
        return ConfigData.getConfigData().globalConfig.getLoginPermitted();
    }

    public String getLoginRefused() {
        return ConfigData.getConfigData().globalConfig.getLoginRefused();
    }

    public String getRedoLogin() {
        return ConfigData.getConfigData().globalConfig.getRedoLogin();
    }

    public String getReLoginPermitted() {
        return ConfigData.getConfigData().globalConfig.getReLoginPermitted();
    }

    public String getCSMessage() {
        return ConfigData.getConfigData().globalConfig.getCSMessage();
    }

    public String getRequestReLogin() {
        return ConfigData.getConfigData().globalConfig.getRequestReLogin();
    }

    public String getMqHost() {
        return ConfigData.getConfigData().globalConfig.getMqHost();
    }
}
