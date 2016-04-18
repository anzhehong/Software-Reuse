import reuse.communication.entity.AAMessage;
import reuse.communication.InterfaceEvent;
import reuse.communication.MQ.MQConnect;
import reuse.communication.MQ.MQFactory;
import reuse.cm.ReadJson;
import reuse.utility.EventController;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * Created by MSI on 2016/3/27.
 */
public class Client {

    /**
     * 登录请求
     */
    private MQConnect baseConnect;

    /**
     * 每一个Client和Server的消息传送通道
     */
    public MQConnect privateConnect;

    /**
     * 公用Topic
     */
    public MQConnect topicConenct;

    /**
     * 构造函数，初始化共有的 baseconnect
     */
    public Client() {
        try {
            this.baseConnect = new MQConnect(MQFactory.getproducer("testQueue"));
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    /**
     * 登录，通过判断message的type来判断状态；发送信息
     * @param aaMessage
     * @throws JMSException
     */
    public void Login(final AAMessage aaMessage) throws JMSException {
        String userName = aaMessage.getUser().getUserName();

        baseConnect.sendMessage(aaMessage.getFinalMessage());
        privateConnect = new MQConnect(MQFactory.getproducer("CS_" + userName),
                MQFactory.getConsumer("SC_" +userName));
        privateConnect.addMessageHandler(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                System.out.println("test on message");
                int type = 0;
                //TODO: 收到登录验证
                try {
                    type = message.getIntProperty("type");
                    if (type == 1) {
                        loginSuccessHandler();
                    }else if (type == 999){
                        getReDoLogInHandler();

                    }else if (type == 888) {
                        getReLogInPermittedHandler();
                    }
                    else if (type == 2) {
                        EventController.eventBus.post(new InterfaceEvent(message.getStringProperty("content")));
                    }
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 私有queue发送信息给server
     * @param aaMessage
     * @throws JMSException
     */
    public void SendMessage(AAMessage aaMessage) throws JMSException {
        if (privateConnect != null) {
            System.out.println("Client send a message to server...");
            privateConnect.sendMessage(aaMessage.getFinalMessage());
        }else {
            //TODO:断开连接？
        }
    }

    public void loginSuccessHandler()throws JMSException{
        //TODO:登录成功写入文件
        topicConenct = new MQConnect(MQFactory.getSubscriber("Topic"));
        topicListener();
        EventController.eventBus.post(new InterfaceEvent("loginSuccessfully"));
    }
    public void getReDoLogInHandler() throws JMSException{
        //TODO: 断开重连
        //TODO: 发送给interface 不能再输入了
        //TODO: 发送给server ：请求重连
        //TODO: 关闭topic，保证不会继续接受后面其它client 发送的消息
        //TODO: 继续监听，直到拿到server发给我的『你成功重连啦！』的消息->加入topic，告诉interface可以接受。
        EventController.eventBus.post(new InterfaceEvent("inputForbidden"));
        topicConenct.getMessageConsumer().close();

        AAMessage reloginMessage = new AAMessage(777, "Relogin Request");
        privateConnect.sendMessage(reloginMessage.getFinalMessage());

    }
    public void getReLogInPermittedHandler() throws JMSException{
        //TODO: 『你成功重连啦！』的消息->加入topic，告诉interface可以接受。
        topicConenct = new MQConnect(MQFactory.getSubscriber("Topic"));
        topicListener();
        //将信息发出
        EventController.eventBus.post(new InterfaceEvent("LoggedAgain"));
    }

    private void topicListener() throws JMSException {
        topicConenct.addMessageHandler(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                try {
                    EventController.eventBus.post(new InterfaceEvent("MessageReceived",message));
                    System.out.println(message.getStringProperty("content"));
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
