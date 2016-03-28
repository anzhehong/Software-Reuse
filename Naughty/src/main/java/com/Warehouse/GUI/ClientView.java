package tongji.edu;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Kang Huilin on 2016/3/27.
 */
public class ClientView extends JFrame {

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


    JTextField MessageShow;//消息展示区
    JTextArea MessageEdit;//消息编辑区
    JTextField NumberList;//当前成员
    //JPanel BlankArea;
    JButton ConfirmButton; //发送键
    JButton CloseButton;//关闭键

    public void init() {

        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);

        //MessageShow 面板
        MessageShow = new JTextField();
       // MessageShow.setBackground(Color.green);
        this.add(MessageShow, new GBC(0, 0, 2, 1).
                setFill(GBC.BOTH).
                setIpad(550, 350).
                setWeight(1, 1));


        //MessageEdit
        MessageEdit = new JTextArea();
        //MessageEdit.setBackground(Color.YELLOW);
        MessageEdit.setLineWrap(true);
        this.add(MessageEdit, new GBC(0, 1, 2, 1).
                setFill(GBC.BOTH).
                setIpad(550,200 ).
                setWeight(1, 1));


        //NumberList
        NumberList = new JTextField();
        //NumberList.setBackground(Color.black);
        this.add(NumberList,new GBC(2,0,1,3).
                setFill(GBC.BOTH).
                setIpad(200,600).
                setWeight(0,1));


        ConfirmButton= new JButton("exit");
        // ConfirmButton.setBackground(Color.LIGHT_GRAY);
        this.add(ConfirmButton, new GBC(0, 2, 1, 1).
                setFill(GBC.BOTH).
                setIpad(225, 50).
                setWeight(1, 1));
        // this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        CloseButton=new JButton("send");
       // CloseButton.setBackground(Color.orange);

        this.add(CloseButton, new GBC(1, 2, 1, 1).
                setFill(GBC.BOTH).
                setIpad(225, 50).
                setWeight(1, 1));
    }

}

