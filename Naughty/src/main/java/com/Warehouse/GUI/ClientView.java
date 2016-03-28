package com.Warehouse.GUI;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Kang Huilin on 2016/3/27.
 */
public class ClientView extends JFrame {

    public JTextField MessageShow;//消息展示区
    public JTextArea MessageEdit;//消息编辑区
    public JButton ConfirmButton; //发送键
    public JButton CloseButton;//关闭键


    public static void main(String args[] )
    {
        ClientView clientView=new ClientView();
    }

    public ClientView()
    {
        init();
        this.setSize(750,600);
        this.setVisible(true);
    }


    public void init() {

        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);

        //MessageShow 面板
        MessageShow = new JTextField();
        MessageShow.setEnabled(false);
        this.add(MessageShow, new GBC(0, 0, 2, 1).
                setFill(GBC.BOTH).
                setIpad(550, 350).
                setWeight(1, 1));


        //MessageEdit
        MessageEdit = new JTextArea();
        MessageEdit.setLineWrap(true);
        this.add(MessageEdit, new GBC(0, 1, 2, 1).
                setFill(GBC.BOTH).
                setIpad(550,200 ).
                setWeight(1, 1));

/*
        //NumberList
        NumberList = new JTextField();
        this.add(NumberList,new GBC(2,0,1,3).
                setFill(GBC.BOTH).
                setIpad(200,600).
                setWeight(1,1));

*/
        ConfirmButton= new JButton("exit");
        this.add(ConfirmButton, new GBC(0, 2, 1, 1).
                setFill(GBC.BOTH).
                setIpad(225, 50).
                setWeight(1, 1));


        CloseButton=new JButton("send");
        this.add(CloseButton, new GBC(1, 2, 1, 1).
                setFill(GBC.BOTH).
                setIpad(225, 50).
                setWeight(1, 1));
    }



}
