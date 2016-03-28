package com.Warehouse.GUI;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Kang Huilin on 2016/3/27.
 */
public class ClientView extends JFrame {

    public JTextArea MessageShow;//消息展示区
    public JTextArea MessageEdit;//消息编辑区
    public JTextArea SplitLine;  //分割线
    public JTextArea BlankArea;  //分割线
    public JButton ConfirmButton; //发送键
    public JButton CloseButton;//关闭键
    public static void main(String args[] )
    {
        ClientView clientView=new ClientView();
    }

    public ClientView()
    {
        this.init();
        this.setSize(600,600);
        this.setVisible(true);
    }


    public void init() {

        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);

        //MessageShow 面板
        MessageShow = new JTextArea();
        MessageShow.setEnabled(false);
        this.add(MessageShow, new GBC(0, 0, 3, 1).
                setFill(GBC.BOTH).
                setIpad(600, 300).
                setWeight(1, 1));

        //SplitLine 分割线
        SplitLine = new JTextArea();
        SplitLine.setEnabled(false);
        SplitLine.setBackground(Color.lightGray);
        this.add(SplitLine, new GBC(0, 1, 3, 1).
                setFill(GBC.BOTH).
                setIpad(600, 1).
                setWeight(1, 0));


        //MessageEdit
        MessageEdit = new JTextArea();
        MessageEdit.setLineWrap(true);
        this.add(MessageEdit, new GBC(0, 2, 3, 1).
                setFill(GBC.BOTH).
                setIpad(600,249 ).
                setWeight(1, 1));


        //BlankArea 面板
        BlankArea = new JTextArea();
        BlankArea.setBackground(Color.white);
        BlankArea.setEnabled(false);
        this.add(BlankArea, new GBC(0, 1, 1, 1).
                setFill(GBC.BOTH).
                setIpad(400, 20).
                setWeight(1, 1));


        CloseButton = new JButton("exit");
        this.add(CloseButton, new GBC(1, 3, 1, 1).
                setFill(GBC.NONE).
                setIpad(100, 15).
                setWeight(1, 0));


        ConfirmButton=new JButton("send");
        this.add(ConfirmButton, new GBC(2, 3, 1, 1).
                setFill(GBC.NONE).
                setIpad(100, 15).
                setWeight(1, 0));
    }




}
