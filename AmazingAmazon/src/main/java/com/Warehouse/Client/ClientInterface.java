package com.Warehouse.Client;

import com.Warehouse.Event.TestEvent;
import com.Warehouse.entity.AAMessage;
import com.Warehouse.entity.StaticVarible;
import com.Warehouse.entity.User;
import com.Warehouse.service.MainService;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by MSI on 2016/3/23.
 */
public class ClientInterface implements ActionListener {

    private Client client;
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

        }
    }

    @Subscribe
    public void listenEvent(TestEvent testEvent){
        System.out.println(testEvent.getStr().toString());
    }

    public static void main(String[] args) throws JMSException{
        ClientInterface clientInterface1 = new ClientInterface();
        clientInterface1.init(StaticVarible.baseQueueConsumer);
        EventBus eventBus = EventController.eventBus;
        eventBus.register(clientInterface1);
    }
}
