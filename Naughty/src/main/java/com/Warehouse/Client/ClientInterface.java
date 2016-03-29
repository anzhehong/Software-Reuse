package com.Warehouse.Client;

import com.Warehouse.Event.TestEvent;
import com.Warehouse.GUI.ClientView;
import com.Warehouse.entity.AAMessage;
import com.Warehouse.entity.StaticVarible;
import com.Warehouse.entity.User;
import com.Warehouse.service.MainService;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Timer;

/**
 * Created by MSI on 2016/3/23.
 */
public class ClientInterface implements ActionListener {

    private Client client;
    private static int second = 1000;
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
    private static String loginLog = "ClientLoginLog.txt";


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
        this.jFrame.setTitle("LOG IN/UP");
        this.jFrame.setLocation(200,100);
        this.jFrame.show();

    }

    private void uninit()
    {
        this.jFrame.setVisible(false);
    }
    @Autowired
    MainService mainService;

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
    public void listenEvent(TestEvent testEvent){

        //收到登录成功信息
        if(testEvent.getStr().toString().equals("loginSuccessfully")) {
            //TODO:登录成功写入文件
            validLoginCount +=1;
            System.out.println(testEvent.getStr().toString());
            uninit();
            clientView = new ClientView();
            clientView.ConfirmButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String content = clientView.MessageEdit.getText().trim();
                    AAMessage sendMes = new AAMessage(5, content);
                    try {
                        client.SendMessage(sendMes);
                    } catch (JMSException e1) {
                        e1.printStackTrace();
                    }

                    System.out.print("mouse clicked");
                }
            });
        }else if(testEvent.getStr().toString().equals("MessageReceived"))   //收到有信息收到的消息，更新聊天界面
        {
            Message message = testEvent.getMessage();
            try {
                clientView.MessageShow.setText(clientView.MessageShow.getText().trim()+
                        "\n" + message.getStringProperty("content"));
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }else if (testEvent.getStr().toString().equals("inputForbidden"))       //被告知需要断开
        {
            //TODO: button失效
            clientView.ConfirmButton.setVisible(false);
        }else if (testEvent.getStr().toString().equals("LoggedAgain")) {
            //TODO: button恢复
            clientView.ConfirmButton.setVisible(true);
        }else {
            inValidLoginCount += 1;
            String errorMsg = testEvent.getStr().toString();
            System.out.println("error: " + errorMsg);
        }


//        clientView.addMouseListener(new MouseAdapter() {//这里使用MouseAdapter代替MouseListener，因为MouseListener要重写的方法太多
//            public void mouseClicked(MouseEvent e) {
//                System.out.print("mouse clicked");
//            }
//        });
    }

    public static void main(String[] args) throws JMSException{

        ClientInterface clientInterface1 = new ClientInterface();
        clientInterface1.timer = new Timer();
        clientInterface1.timer.schedule(new WriteLoginTask(), 60 * second, 60 * second);
        clientInterface1.init(StaticVarible.baseQueueConsumer);
        EventBus eventBus = EventController.eventBus;
        eventBus.register(clientInterface1);
    }

    public static java.util.Timer timer;
    static class WriteLoginTask extends TimerTask
    {
        public void run() {
            //TODO: 把validLogin和invalidLogin记录到文件中
            Date date = new Date();
            WriteLog.write(loginLog, date + "\tValid Login Count: " + validLoginCount + "\tInvalid Login Count: " + inValidLoginCount);
            inValidLoginCount = 0;
            validLoginCount = 0;

        }
    }
}
