package com.Warehouse.Client;

import com.Warehouse.entity.User;
import com.Warehouse.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.jms.*;
import javax.swing.*;
import javax.xml.ws.handler.MessageContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by MSI on 2016/3/23.
 */
public class ClientInterface implements ActionListener {
    public Client_base client_base;
    private Session session;
    private ClientInterface clientInterface;
    private JFrame jFrame = new JFrame();
    private JPanel jPanel = new JPanel();
    private Label username_label = new Label();
    private Label password_label = new Label();
    private JTextField username_input = new JTextField();
    private JTextField password_input = new JTextField();
    private JButton login_btn = new JButton();
    private JButton signup_btn = new JButton();

    public void setSession(Session session) {
        this.session = session;
    }

    public ClientInterface() {

    }

    public String getUsername() {
        return this.username_input.getText().trim();
    }

    public String getPassword() {
        return this.password_input.getText().trim();
    }


    public void init()throws JMSException{

        client_base = new Client_base("localhost","61616","testQueue");
        client_base.start();

        //窗口大小、位置
        this.jFrame.setSize(400,300);
        this.jFrame.setResizable(false);
        this.jFrame.setVisible(true);
        this.jFrame.setTitle("LOG IN/UP");
        this.jFrame.setLocation(200,100);
        this.jFrame.show();

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

    }
    @Autowired
    MainService mainService;

    public Session getSession() {
        return session;
    }

    public boolean checkPassword(String username,String password)throws JMSException
    {
        Message message = client_base.getSession().createMessage();
        // 发送登录请求 type = 0 意味着登录请求
        message.setIntProperty("type",0);
        message.setStringProperty("username", username);
        message.setStringProperty("password", password);
        client_base.getProducer().send(message);
        // 获得返回结果 type = 1 意味着返回结果
        Message messageReceive = client_base.getConsumer().receive();
        if(messageReceive.getIntProperty("type")==1)
        {
            System.out.println(messageReceive.getBooleanProperty("confirm"));
            return messageReceive.getBooleanProperty("confirm");
        }
        else
            return false;
    }
    public boolean Signup(String username,String password) throws JMSException {
        Message message = client_base.getSession().createMessage();
        // 发送注册请求 type = 2 意味着注册请求
        message.setIntProperty("type",2);
        message.setStringProperty("username", username);
        message.setStringProperty("password", password);
        client_base.getProducer().send(message);
        // 获得返回结果 type = 1 意味着返回结果
        Message messageReceive = client_base.getConsumer().receive();
        if(messageReceive.getIntProperty("type")==1)
        {
            System.out.println(messageReceive.getBooleanProperty("confirm"));
            return messageReceive.getBooleanProperty("confirm");
        }
        else
            return false;

    }
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==login_btn){

            try {
                checkPassword(this.username_input.getText().trim(), this.password_input.getText().trim());

            } catch (JMSException e1) {
                e1.printStackTrace();
            }

        }
        if(e.getSource()==signup_btn){
            try {
                Signup(this.username_input.getText().trim(), this.password_input.getText().trim());
            } catch (JMSException e1) {
                e1.printStackTrace();
            }


        }


    }
    public static void main(String[] args) throws JMSException{
        ClientInterface clientInterface1 = new ClientInterface();
        clientInterface1.init();


    }
}
