package cn.sias.snake;

import com.sun.corba.se.pept.transport.ListenerThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class MPanel extends JPanel implements KeyListener, ActionListener {

    //定义
    ImageIcon title=new ImageIcon("title.jpg");
    ImageIcon body=new ImageIcon("body.png");
    ImageIcon up=new ImageIcon("up.png");
    ImageIcon down=new ImageIcon("down.png");
    ImageIcon left=new ImageIcon("left.png");
    ImageIcon right=new ImageIcon("right.png");
    ImageIcon food=new ImageIcon("food.png");


    //初始化坐标数组
    int len=3;
    int [] snakex=new int[750];
    int [] snakey=new int[750];
    //食物坐标
    int foodx;
    int foody;
    //随机数
    Random random=new Random();

    //闹钟监听
    Timer timer=new Timer(100,this);
//控制方向
    String fx="R";//方向有"R","L","U","D"
    //定义开始和结束
    boolean isStart=false;
    boolean isOver=false;



    //构造函数
    public MPanel(){
        inSnake();
        //设置可以获取此Component中的焦点
        this.setFocusable(true);
        //设置监听
        this.addKeyListener(this);
        //闹钟开始
        timer.start();

    }
    //画布
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        //画一个背景色
        this.setBackground(Color.WHITE);
        title.paintIcon(this,g,25,11);

        g.fillRect(25,75,850,600);
        //这里注意，fillRect在图片插入先后顺序会出现不同的层级，图片在前会在幕布下面，显示不出
        //静态的图片引入
        if(fx=="R"){
            right.paintIcon(this,g,snakex[0],snakey[0]);
        }else if (fx=="L"){
            left.paintIcon(this,g,snakex[0],snakey[0]);
        }else if (fx=="U"){
            up.paintIcon(this,g,snakex[0],snakey[0]);
        }else if (fx=="D"){
            down.paintIcon(this,g,snakex[0],snakey[0]);
        }

        //控制身体
       for (int i=1;i<len;i++){
           body.paintIcon(this,g,snakex[i],snakey[i]);

       }

        //判断是否开始，开始则进行下面函数
       if(isStart==false){
           g.setColor(Color.white);
           g.setFont(new Font("arial",Font.BOLD,40));
           g.drawString("Start",400,350);
           fx="R";
       }else {
           //食物
           food.paintIcon(this,g,foodx,foody);
       }

        if(isStart==true&&isOver==true){
            g.setColor(Color.red);
            g.setFont(new Font("arial",Font.BOLD,40));
            g.drawString("Over",400,350);
        }




    }

    //构造链表函数
    public void inSnake(){

        //开始坐标
         len=3;
         snakex[0]=100;
         snakey[0]=100;
         snakex[1]=75;
         snakey[1]=100;
         snakex[2]=50;
         snakey[2]=100;

         //随即食物生成,限制使其出现在Component中
        foodx=25+25*random.nextInt(34);
        foody=75+25*random.nextInt(24);
    }

//获取焦点
    @Override
    public void keyTyped(KeyEvent e) {

    }
    //点击前

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode=e.getKeyCode();
        if (keyCode==KeyEvent.VK_SPACE){
            if (isOver=true){
                isOver=false;
                inSnake();
            }
            isStart=!isStart;
        }else if (keyCode==KeyEvent.VK_RIGHT){
            fx="R";
        }else if (keyCode==KeyEvent.VK_LEFT){
            fx="L";
        }else if (keyCode==KeyEvent.VK_UP){
            fx="U";
        }else if (keyCode==KeyEvent.VK_DOWN){
            fx="D";
        }
        //获取输入键为空格键，由于isStart开始定义为否，所以这里如果是，则定义为！isStart
        repaint();
        //重新再运行，重绘
    }
    //点击时

    @Override
    public void keyReleased(KeyEvent e) {


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //if判断暂停
        if (isStart==true&&isOver==false){
            //蛇身按链表数组进行递换
            for (int i=len-1;i>0;i--){
                snakex[i]=snakex[i-1];
                snakey[i]=snakey[i-1];
            }
            if (fx=="R"){
                //蛇头进行会增加25像素，若到达边界，则进行回溯
                snakex[0]=snakex[0]+25;
                if(snakex[0]>850){
                    snakex[0]=25;
                }
            }else if (fx=="L"){
                snakex[0]=snakex[0]-25;
                if(snakex[0]<25){
                    snakex[0]=850;
                }
            }else if (fx=="U"){
                snakey[0]=snakey[0]-25;
                if(snakey[0]<75){
                    snakey[0]=650;
                }
            }else if (fx=="D"){
                snakey[0]=snakey[0]+25;
                if(snakey[0]>650){
                    snakey[0]=75;
                }
            }

            //判断食物是否头部坐标等于食物，即被吃掉
            if (snakex[0]==foodx&&snakey[0]==foody){
                len++;
                foodx=25+25*random.nextInt(34);
                foody=75+25*random.nextInt(24);
            }




        }
        //repaint()放到if里面和外面效果一样
        repaint();

        //判断是否结束
        for (int i=1;i<len-1;i++){
            if (snakex[0]==snakex[i]&&snakey[0]==snakey[i]){
                isOver=true;
            }


        }
        timer.start();

    }
    //点击结束，松开
}
