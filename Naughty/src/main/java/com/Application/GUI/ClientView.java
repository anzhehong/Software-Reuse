package com.Application.GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Created by Kang Huilin on 2016/3/27.
 */
public class ClientView extends JFrame {
    public JPanel contentPane;
    public JScrollPane ShowScroll;
    public JScrollPane EditScroll;


    public JTextArea MessageShow;//消息展示区
    public JTextArea MessageEdit;//消息编辑区
    public  JTextArea BlankArea;
    public JButton ConfirmButton; //发送键
    public JButton CloseButton;//关闭键
    //public JTextArea SplitLine;  //分割线


    public static void main(String args[] )
    {
        ClientView clientView=new ClientView();
    }

    public ClientView()
    {
        this.init();
        this.setSize(650,600);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    public void init() {

        contentPane=new JPanel();
        contentPane.setBorder(new EmptyBorder(30,15,15,15));
        GridBagLayout layout = new GridBagLayout();
        contentPane.setLayout(layout);
        this.setContentPane(contentPane);
        EditScroll=new JScrollPane();
        ShowScroll=new JScrollPane();

        layout.columnWidths = new int[]{400, 100, 100, 0};
        layout.columnWeights = new double[]{0, 0, 0, 1.0E-4};
        layout.rowHeights = new int[]{250, 1, 225, 24, 0};
        layout.rowWeights = new double[]{0, 0, 0, 0, 1.0E-4};

       //MessageShow 面板
        MessageShow = new JTextArea();
        MessageShow.setEnabled(false);
        ShowScroll.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        this.add(ShowScroll,
                new GBC(0, 0, 6, 1).
                setFill(GBC.BOTH).
                setWeight(1, 250));
        ShowScroll.setViewportView(MessageShow);

        //MessageEdit
        MessageEdit = new JTextArea();
        MessageEdit.setLineWrap(true);
        EditScroll.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        this.add(EditScroll,
                new GBC(0, 2, 6, 1).
                setFill(GBC.BOTH).
                setWeight(1, 225));
        EditScroll.setViewportView(MessageEdit);

        //BlankArea 面板
        BlankArea = new JTextArea();
        BlankArea.setEnabled(false);
        BlankArea.setBackground(new Color(235,235,235));
        this.add(BlankArea,
                new GBC(0, 3, 1, 1).
                setFill(GBC.BOTH).
                setWeight(1, 24));

        CloseButton = new JButton("exit");
        this.add(CloseButton,
                new GBC(1, 3, 1, 1).
                setFill(GBC.BOTH).
                setWeight(0, 24).setInsets(10,5,5,3));

        ConfirmButton=new JButton("send");
        this.add(ConfirmButton,
                new GBC(2, 3, 1, 1).
                setFill(GBC.BOTH).
                setWeight(0, 24).setInsets(10,3,5,5));
        this.setTitle("连接正常");
        /*
        //SplitLine 分割线
        SplitLine = new JTextArea();
        SplitLine.setBackground(Color.PINK);
        SplitLine.setEnabled(false);
        this.add(SplitLine,
                new GBC(0, 1, 6, 1).
                setFill(GBC.BOTH).
                setWeight(1, 1));
        */

    }
}
