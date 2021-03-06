import GUI.ClientView;
import reuse.communication.entity.AAMessage;
import reuse.communication.InterfaceEvent;
import reuse.communication.entity.User;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import reuse.cm.ReadJson;
import reuse.pm.PMManager;
import reuse.utility.AAEncryption;
import reuse.utility.ClassUtil;
import reuse.utility.EventController;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.Timer;

/**
 * Created by MSI on 2016/3/23.
 */
public class ClientInterface implements ActionListener {

    private Client client;
    public ClientView clientView;
    private Session session;
    private JFrame jFrame = new JFrame();
    private JPanel jPanel = new JPanel();
    private Label username_label = new Label();
    private Label password_label = new Label();
    private JTextField username_input = new JTextField();
    private JTextField password_input = new JTextField();
    private JButton login_btn = new JButton();
    private JButton signup_btn = new JButton();

    private static int validLoginCount = 0;
    private static int inValidLoginCount = 0;
    private static int receivedMessageCount = 0;
    private static int second = 1000;
    private static String loginLog = "ClientLoginLog.txt";
    private static String receiveMessageLog = "ClientMesReceived.txt";


    static public String jsonPath = "../Resources/test.json";
    static public String outPath = "../Resources/out/Log/Client/";

    private String errorOutPath = new ReadJson(jsonPath).getStringConfig("ErrorLogPath")+File.separator;
    private String errorOutName = "ErrorLog.txt";

    private String debugOutPath = new ReadJson(jsonPath).getStringConfig("DebugLogPath")+File.separator;
    private String debugOutName = "DebugLog.txt";

    public void setSession(Session session) {
        this.session = session;
    }

    public ClientInterface() {
        client = new Client();
    }

    public String getUsername() {
        return this.username_input.getText().trim();
    }

    public String getPassword() {
        return this.password_input.getText().trim();
    }


    public void init(String QueueId)throws JMSException{

        //组件init
        this.username_label.setText("username:");
        this.username_label.setBounds(50,50,120,30);
        this.username_label.setFont(new Font("Serif",Font.PLAIN,24));
        this.password_label.setText("password:");
        this.password_label.setBounds(50,100,120,30);
        this.password_label.setFont(new Font("Serif",Font.PLAIN,24));

        this.username_input.setBounds(180,50,180,30);
        this.username_input.setFont(new Font("Serif", Font.PLAIN, 24));
        this.password_input.setBounds(180,100,180,30);
        this.password_input.setFont(new Font("Serif",Font.PLAIN,24));

        this.login_btn.setBounds(80,160,100,40);
        this.login_btn.setText("Log In");
        this.login_btn.setFont(new Font("Serif", Font.PLAIN, 16));
        this.login_btn.addActionListener(this);
        this.signup_btn.setBounds(200,160,100,40);
        this.signup_btn.setText("Sign Up");
        this.signup_btn.setFont(new Font("Serif", Font.PLAIN, 16));
        this.signup_btn.addActionListener(this);

        this.jPanel.add(username_label);
        this.jPanel.add(password_label);
        this.jPanel.add(username_input);
        this.jPanel.add(password_input);
        this.jPanel.add(login_btn);
        this.jPanel.add(signup_btn);
        this.jPanel.setLayout(null);
        this.jFrame.add(this.jPanel);

        //窗口大小、位置
        this.jFrame.setSize(400,300);
        this.jFrame.setResizable(false);
        this.jFrame.setVisible(true);
        this.jFrame.setTitle(new ReadJson(jsonPath).getStringConfig("mqHost"));
        this.jFrame.setLocation(200,100);
        this.jFrame.show();

    }

    private void uninit()
    {
        this.jFrame.setVisible(false);
    }

    public Session getSession() {
        return session;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==login_btn){
            User user = new User(username_input.getText().trim(), password_input.getText().trim());
            try {
                AAMessage aaMessage = new AAMessage(0, user);
                client.Login(aaMessage);
            } catch (JMSException e1) {
                e1.printStackTrace();
                PMManager.ErrorLog(errorOutName, e1.toString(), this.getClass().getName(), ClassUtil.getLineNumber()
                        , errorOutPath);
            }
        }
        if(e.getSource()==signup_btn){
            User user = new User(4, "abc", "abc");
            AAMessage aaMessage = new AAMessage(3, "Test Sending Message", user);
            try {
                client.SendMessage(aaMessage);
            } catch (JMSException e1) {
                e1.printStackTrace();
                PMManager.ErrorLog(errorOutName, e1.toString(), this.getClass().getName(), ClassUtil.getLineNumber()
                        , errorOutPath);
            }
        }
    }

    @Subscribe
    public void listenEvent(InterfaceEvent interfaceEvent){

        //收到登录成功信息
        if(interfaceEvent.getStr().toString().equals("loginSuccessfully")) {

            /**
             *  关掉登录界面， 打开聊天界面
             */

            validLoginCount +=1;
            uninit();
            clientView = new ClientView();
            clientView.ConfirmButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    /**
                     * 发送聊天信息，让server去通过Topic 转发
                     */
                    PMManager.DebugLog(debugOutName,"client send message to server",this.getClass().getName(),ClassUtil.getLineNumber(),debugOutPath);
                    AAMessage sendChatMessage = new AAMessage(5, clientView.MessageEdit.getText().trim());
                    try {
                        client.SendMessage(sendChatMessage);
                        //有可能新建用时间来命名的文件夹
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH mm ss");
                        String DateStr = df.format(new Date());
                        //存储的消息

                        String contentStoredUnencrypted  = username_input.getText().trim()+"\t"+
                                sendChatMessage.getFinalMessage().getStringProperty("content")+"\t"+
                                sendChatMessage.getFinalMessage().getStringProperty("createdTime");

                        ArrayList<Object> result = AAEncryption.DefaultEncryptString(contentStoredUnencrypted);

                        String contentStored = (String)result.get(0);
                        ReadJson readJson = new ReadJson(jsonPath);
                        String filename = readJson.getStringConfig("sourcePath");
                        PMManager.WriteMsg(filename,contentStored,DateStr,"Client");



                    } catch (JMSException e1) {
                        e1.printStackTrace();
                        PMManager.ErrorLog(errorOutName, e1.toString(), this.getClass().getName(), ClassUtil.getLineNumber()
                                , errorOutPath);
                    }
                }
            });
        }else if(interfaceEvent.getStr().toString().equals("MessageReceived"))   //收到有信息收到的消息，更新聊天界面
        {
            System.out.println("收到topic消息");
            PMManager.DebugLog(debugOutName,"receive message via topic,refresh UI",this.getClass().getName(),ClassUtil.getLineNumber(),debugOutPath);
            Message message = interfaceEvent.getMessage();
            try {
                String messageToShow = message.getStringProperty("content");
                if(!messageToShow.equals("")) {
                    String dateStr = message.getStringProperty("createdTime");
                    messageToShow = dateStr + " : " + messageToShow;
                    System.out.println("messagetoshow:"+messageToShow);
//                String name = message.getStringProperty("author");
                    if (clientView.MessageShow.getText().trim().equals("")) {
                        clientView.MessageShow.setText(clientView.MessageShow.getText().trim()
                                + messageToShow);
                    } else {
                        clientView.MessageShow.setText(clientView.MessageShow.getText().trim()
                                + "\n" + messageToShow);
                    }
                    receivedMessageCount += 1;
                }else{
                    List<String> nameList = (ArrayList<String>)message.getObjectProperty("stateList");
                    clientView.friendState.setText(nameList.get(0));
                    for(int i=1;i<nameList.size();i++) {
                        clientView.friendState.setText(clientView.friendState.getText().trim()
                                + "\n" + nameList.get(i));
                        System.out.println(nameList.get(i));
                    }
                }
            } catch (JMSException e) {
                e.printStackTrace();
                PMManager.ErrorLog(errorOutName, e.toString(), this.getClass().getName(), ClassUtil.getLineNumber()
                        , errorOutPath);
            }
        }else if (interfaceEvent.getStr().toString().equals("inputForbidden"))       //被告知需要断开
        {
            PMManager.DebugLog(debugOutName,"inputForbidden",this.getClass().getName(),ClassUtil.getLineNumber(),debugOutPath);
            clientView.ConfirmButton.setVisible(false);
            clientView.setTitle("连接断开，正在重连。。。");
        }else if (interfaceEvent.getStr().toString().equals("LoggedAgain")) {
            PMManager.DebugLog(debugOutName,"LoggedAgain",this.getClass().getName(),ClassUtil.getLineNumber(),debugOutPath);
            clientView.ConfirmButton.setVisible(true);
            clientView.setTitle("连接正常");
        } else {
            PMManager.DebugLog(debugOutName,"login failed",this.getClass().getName(),ClassUtil.getLineNumber(),debugOutPath);
            inValidLoginCount += 1;
            String errorMsg = interfaceEvent.getStr().toString();

            /**
             * 登录失败，弹出警告框
             */
            //TODO:重复登录的问题
            JOptionPane.showMessageDialog(null, errorMsg, null,JOptionPane.ERROR_MESSAGE);
            System.out.println("error: " + errorMsg);
        }

    }

    public static void main(String[] args) throws JMSException{
        ClientInterface clientInterface1 = new ClientInterface();
        clientInterface1.timer = new Timer();
        clientInterface1.timer.schedule(new WriteLoginTask(), 5 * second, 5 * second);
        clientInterface1.init(ReadJson.getStringConfig("baseQueueDestination"));
        EventBus eventBus = EventController.eventBus;
        eventBus.register(clientInterface1);
    }

    public static Timer timer;
    static class WriteLoginTask extends TimerTask
    {
        public void run() {
            /**
             *   把validLogin和invalidLogin记录到文件中
             */

            Date date = new Date();
            PMManager.encipherWrite(loginLog, date + "\tValid Login Count: " + validLoginCount + "\tInvalid Login Count: " + inValidLoginCount, ClientInterface.outPath);
            PMManager.encipherWrite(receiveMessageLog, date + "\tReceived message Count: " + receivedMessageCount, ClientInterface.outPath);
            inValidLoginCount = 0;
            validLoginCount = 0;
            receivedMessageCount = 0;
        }
    }
}
