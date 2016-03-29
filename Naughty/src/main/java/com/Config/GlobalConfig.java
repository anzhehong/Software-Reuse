package com.Config;

/**
 * Created by fowafolo
 * Date: 16/3/29
 * Time: 21:16
 */
public class GlobalConfig {
    private String baseQueueDestination;
    private String requestLogin;
    private String loginPermitted;
    private String loginRefused;
    private String redoLogin;
    private String reLoginPermitted;
    private String csMessage;
    private String requestReLogin;
    private String mqHost;

    public void setBaseQueueDestination(String baseQueueDestination) {
        this.baseQueueDestination = baseQueueDestination;
    }

    public String getBaseQueueDestination() {
        return baseQueueDestination;
    }

    public void setRequestLogin(String requestLogin) {
        this.requestLogin = requestLogin;
    }

    public String getRequestLogin() {
        return requestLogin;
    }

    public void setLoginPermitted(String loginPermitted) {
        this.loginPermitted = loginPermitted;
    }

    public String getLoginPermitted() {
        return loginPermitted;
    }

    public void setLoginRefused(String loginRefused) {
        this.loginRefused = loginRefused;
    }

    public String getLoginRefused() {
        return loginRefused;
    }

    public void setRedoLogin(String redoLogin) {
        this.redoLogin = redoLogin;
    }

    public String getRedoLogin() {
        return redoLogin;
    }

    public void setReLoginPermitted(String reLoginPermitted) {
        this.reLoginPermitted = reLoginPermitted;
    }

    public String getReLoginPermitted() {
        return reLoginPermitted;
    }

    public void setCSMessage(String CSMessage) {
        csMessage = CSMessage;
    }

    public String getCSMessage() {
        return csMessage;
    }

    public void setRequestReLogin(String requestReLogin) {
        this.requestReLogin = requestReLogin;
    }

    public String getRequestReLogin() {
        return requestReLogin;
    }

    public void setMqHost(String mqHost) {
        this.mqHost = mqHost;
    }

    public String getMqHost() {
        return mqHost;
    }
}
