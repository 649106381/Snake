package cn.sias.snake;

import javax.swing.*;

public class Msnake {
    public static void main(String[] args) {
        //画出900*700的白色窗口
        JFrame frome=new JFrame();
        frome.setBounds(10,10,900,720);
        //禁止手动拖到改变大小
        frome.setResizable(false);
        //默认点击关闭
        frome.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //使用MPanel中的MPanel方法
        frome.add(new MPanel());


        //展示出来
        frome.setVisible(true);


    }
}
