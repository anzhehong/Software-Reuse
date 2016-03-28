package com.Warehouse.GUI;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Kang Huilin on 2016/3/28.
 */
public class ServerView extends JFrame {

    public static void main(String args[] )
    {
        ServerView serverView=new ServerView();
    }

    public ServerView()
    {
        init();
        this.setSize(900,600);
        this.setVisible(true);
    }

    public void init()
    {
        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);

        //接收状态展示区
        StateShow = new JTextArea();
        StateShow.setEnabled(false);
        this.add(StateShow, new GBC(0, 0, 1, 1).
                setFill(GBC.BOTH).
                setIpad(600, 600).
                setWeight(1, 1));

        //当前成员
        NumberList = new JTextField();
        NumberList.setEnabled(false);
        this.add(NumberList, new GBC(1, 0, 1, 1).
                setFill(GBC.BOTH).
                setIpad(300, 600).
                setWeight(1, 1));

    }
    JTextArea StateShow;//接收信息状态；
    JTextField NumberList;//当前成员
}
