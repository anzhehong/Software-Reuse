import GUI.ClientView;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

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


 //   static public String jsonPath = "/Users/fowafolo/Desktop/test.json";
//   static public String outPath = "/Users/fowafolo/Desktop/Log/Client/";

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
//        this.jFrame.setTitle(ReadJson.GetConfig("mqHost", jsonPath));
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
            }
        }
        if(e.getSource()==signup_btn){
            User user = new User(4, "abc", "abc");
            AAMessage aaMessage = new AAMessage(3, "Test Sending Message", user);
            try {
                client.SendMessage(aaMessage);
            } catch (JMSException e1) {
                e1.printStackTrace();
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
                    AAMessage sendChatMessage = new AAMessage(5, clientView.MessageEdit.getText().trim());
                    try {
                        client.SendMessage(sendChatMessage);
                    } catch (JMSException e1) {
                        e1.printStackTrace();
                    }
                }
            });
        }else if(interfaceEvent.getStr().toString().equals("MessageReceived"))   //收到有信息收到的消息，更新聊天界面
        {
            Message message = interfaceEvent.getMessage();
            try {
                String dateStr = message.getStringProperty("createdTime");
//                String name = message.getStringProperty("author");
                if (clientView.MessageShow.getText().trim().equals("")) {
                    clientView.MessageShow.setText(clientView.MessageShow.getText().trim()
                            + dateStr   + " : " + message.getStringProperty("content"));
                }else {
                    clientView.MessageShow.setText(clientView.MessageShow.getText().trim()
                            + "\n" + dateStr   + " : " + message.getStringProperty("content"));
                }
                receivedMessageCount += 1;
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }else if (interfaceEvent.getStr().toString().equals("inputForbidden"))       //被告知需要断开
        {
            clientView.ConfirmButton.setVisible(false);
            clientView.setTitle("连接断开，正在重连。。。");
        }else if (interfaceEvent.getStr().toString().equals("LoggedAgain")) {

            clientView.ConfirmButton.setVisible(true);
            clientView.setTitle("连接正常");
        } else {
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
 //       clientInterface1.init(ReadJson.GetConfig("baseQueueDestination", clientInterface1.jsonPath));
        clientInterface1.init("testQueue");
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
       //     PMManager.Write(loginLog, date + "\tValid Login Count: " + validLoginCount + "\tInvalid Login Count: " + inValidLoginCount, ClientInterface.outPath);
      //      PMManager.Write(receiveMessageLog,date + "\tReceived message Count: " + receivedMessageCount, ClientInterface.outPath);
            inValidLoginCount = 0;
            validLoginCount = 0;
            receivedMessageCount = 0;
        }
    }
}
