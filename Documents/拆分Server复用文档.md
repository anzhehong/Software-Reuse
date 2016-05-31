# 软件复用管理文档

>2016.05.29

# 项目组成员

| 姓名        | 学生账号           | Github Id  |
| ------------- |:-------------:| -----:|
| 林思妙| 1352849 | [linsimiao](https://github.com/linsimiao) | 
|  康慧琳     | 1352871      |   [1352871](https://github.com/1352871)|
| 安哲宏 | 1352834 | [anzhehong](https://github.com/anzhehong) |
| 张嘉琦 | 1352863      |   [kobpko](https://github.com/kobpko)  |

***

## 本次功能扩展要求
###拆分Server

1. 鉴权
2. 消息接收
3. 消息存储
4. 消息转发

## 设计思路
将原有的Server分成四个部分：Server,ForwardServer，AuthServer，PersistServer。
<img src="https://raw.githubusercontent.com/anzhehong/Software-Reuse/master/Documents/img/architecture.png" height = 400>

Server：作为Client与ForwardServer，AuthServer，PersistServer中间层     

          从client接受到消息,发送回执消息给client
 
          接收所有来自Client端的请求，并转发给ForwardServer，AuthServer，PersistServer，交由它们分别进行相应的处理。
        
          接收来自AuthServer的反馈信息
        
AuthServer：登录时,验证用户信息，并将结果反馈给Server。

ForwardServer：将接收到的消息转发给同组在线成员。

PersistServer：

##Server
###核心代码
```java
this.baseConnect = new MQConnect
(MQFactory.getConsumer(new ReadJson(jsonPath).getStringConfig("baseQueueDestination")));

this.authConnect = new MQConnect(MQFactory.getproducer
("Center_Auth"+new ReadJson(jsonPath).getStringConfig("authServerDestination")),
MQFactory.getConsumer("Auth_Center"+new ReadJson(jsonPath).getStringConfig("authServerDestination")));

this.persistConnect = new MQConnect(MQFactory.getproducer
("Center_Persist"+new ReadJson(jsonPath).getStringConfig("persistServerDestination")),
MQFactory.getConsumer("Persist_Center"+new ReadJson(jsonPath).getStringConfig("persistServerDestination")));

this.forwardConnect = new MQConnect(MQFactory.getproducer
("Center_Forward"+new ReadJson(jsonPath).getStringConfig("forwardServerDestination")),
MQFactory.getConsumer("Forward_Center"+new ReadJson(jsonPath).getStringConfig("forwardServerDestination")));
```

```java
//将消息转发给 ForwardServer
forwardConnect.sendMessage(message1);
//将消息转发给 persistServer 
persistConnect.sendMessage(message1);
```
```java
 /**
     * 从client接受到消息
     * @param message
     */
    public void receiveQueue(Message message);
    
```
```java
/**
     * 发送回执消息给client
     * @param flag
     * @param connect
     * @throws JMSException
     */
    public void sendQueue(boolean flag, final MQConnect connect, Object thisObject)
```


##AuthServer
###核心代码
```java
  this.queueConnect =new MQConnect(MQFactory.getproducer
  ("Auth_Center"+new ReadJson(jsonPath).getStringConfig("authServerDestination")),
  MQFactory.getConsumer("Center_Auth"+new ReadJson(jsonPath).getStringConfig("authServerDestination")));
```

```java
 queueConnect.addMessageHandler(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    try {
                       ...
                       //验证用户信息
                        messageToSend.setBooleanProperty("loginPermit", flag);
                        //将结果反馈给Server
                        queueConnect.sendMessage(messageToSend);
                        
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
```

##ForwardServer
###核心代码

```java
this.queueConnect = new MQConnect(MQFactory.getproducer
("Forward_Center"+new ReadJson(jsonPath).getStringConfig("forwardServerDestination")),
MQFactory.getConsumer("Center_Forward"+new ReadJson(jsonPath).getStringConfig("forwardServerDestination")));
```
```java
  queueConnect.addMessageHandler(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    try {
                         ...
                         //将接收到的消息转发给同组在线成员
                        thisConnect.sendMessage(message);
                  
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });
```
##PersistServer
###核心代码

```java
this.queueConnect = new MQConnect(MQFactory.getproducer
("Persist_Center"+new ReadJson(jsonPath).getStringConfig("persistServerDestination")),
MQFactory.getConsumer("Center_Persist"+new ReadJson(jsonPath).getStringConfig("persistServerDestination")));
```
```java
 queueConnect.addMessageHandler(new MessageListener() {

                @Override
                public void onMessage(Message message) {
                    ...
                    //写入消息
                    PMManager.WriteMsg(filename,contentStored,DateStr,"Server");

                }
            });
```
        
        
        
