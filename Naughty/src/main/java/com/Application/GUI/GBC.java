package com.Application.GUI;

import java.awt.*;

/**
 * Created by Kang Huilin on 2016/3/27.
 */
public class GBC extends GridBagConstraints {


    //初始化左上角位置
    public  GBC (int gridx,int gridy)
    {
        this.gridx=gridx;
        this.gridy=gridy;

    }

    //初始化左上角位置和所占行数与列数
    public GBC(int gridx,int gridy,int gridwidth,int gridheight)
    {
        this.gridx=gridx;
        this.gridy=gridy;
        this.gridwidth=gridwidth;
        this.gridheight=gridheight;
    }

    //对齐方式
    public GBC setAnchor(int anchor)
    {
        this.anchor=anchor;
        return this;

    }

    public GBC setFill(int fill)
    {
        this.fill=fill;
        return this;

    }

    //x和y方向上的增量
    public GBC setWeight(double weightx, double weighty)
    {
        this.weightx = weightx;
        this.weighty = weighty;
        return this;
    }

    //外部填充
    public GBC setInsets(int distance)
    {
        this.insets = new Insets(distance, distance, distance, distance);
        return this;
    }

    //外填充
    public GBC setInsets(int top, int left, int bottom, int right)
    {
        this.insets = new Insets(top, left, bottom, right);
        return this;
    }

    //内填充
    public GBC setIpad(int ipadx, int ipady)
    {
        this.ipadx = ipadx;
        this.ipady = ipady;
        return this;
    }
}

    /*
        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);

        //定义一个GridBagConstraints，
        GridBagConstraints s= new GridBagConstraints();

        //是用来控制添加进的组件的显示位置
        s.fill = GridBagConstraints.BOTH;

        //该方法是为了设置如果组件所在的区域比组件本身要大时的显示情况
        //NONE：不调整组件大小。
        //HORIZONTAL：加宽组件，使它在水平方向上填满其显示区域，但是不改变高度。
        //VERTICAL：加高组件，使它在垂直方向上填满其显示区域，但是不改变宽度。
        //BOTH：使组件完全填满其显示区域。

        s.gridwidth=1;//该方法是设置组件水平所占用的格子数，如果为0，就说明该组件是该行的最后一个
        s.weightx = 0;//该方法设置组件水平的拉伸幅度，如果为0就说明不拉伸，不为0就随着窗口增大进行拉伸，0到1之间
        s.weighty=0;//该方法设置组件垂直的拉伸幅度，如果为0就说明不拉伸，不为0就随着窗口增大进行拉伸，0到1之间

   */