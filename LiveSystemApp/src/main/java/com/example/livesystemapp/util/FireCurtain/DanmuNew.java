package com.example.livesystemapp.util.FireCurtain;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import com.sun.awt.AWTUtilities;

public class DanmuNew extends JFrame implements Runnable{

    private static final long serialVersionUID = 1L;

    private int windowwidth;
    private int windwoheight;
    private int spzce;
    private int speed=1;
    private int frame=200;
    private int maxScreenDanmuCount=50;
    private void ShowDanmu(){
        // Thread t = new Thread(this);//启动面板的动画线程
        //  t.start();
        paintDanmu();
    }

    public DanmuNew(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        windwoheight=(int) screenSize.getHeight();
        windowwidth=(int) screenSize.getWidth();
        spzce=speed;
        System.out.println(windwoheight+" "+windowwidth);

        Rectangle bounds = new Rectangle(screenSize);
        this.setTitle("发送弹幕");
        this.setLayout(null);
        this.setBounds(bounds);
        this.setExtendedState(Frame.MAXIMIZED_BOTH);

        this.setAlwaysOnTop(true);
        // 判断是否支持透明度
        this.setUndecorated(true); // 禁用或启用此窗体的修饰。只有在窗体不可显示时
        //this.setDefaultLookAndFeelDecorated(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
//        Image image=Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("llll.png"));
//        setIconImage(image);
        AWTUtilities.setWindowOpaque(this, false);
        this.setVisible(true);

    }


    // TODO Auto-generated constructor stub

    public void add(DanContent c) {

        Random random=new Random();
        c.y=random.nextInt(windwoheight-150)+50;
        c.x=windowwidth;
        list.add(c);
    }
    // Queue<DanContent> list=new LinkedList<>();
    private List<DanContent> list=new ArrayList<>();
    @Override
    public void paint(Graphics gg) {
        super.paint(gg);
        //  gg.setColor(Color.red);
        // gg.setFont(new Font("Dialog", Font.BOLD,50 ));
        int st=0;
        if(list.size()>maxScreenDanmuCount) st=list.size()-maxScreenDanmuCount;

        for(int i=st;i<list.size();i++) {
            DanContent text=list.get(i);
            if(text.x<-windowwidth)continue;
            gg.setColor(getColor(text.getColor()));
            gg.setFont(new Font("楷体",text.getStyle(), text.getSize()));
            gg.drawString(text.getText(), text.x, text.y);
        }

    }

    //使用线程刷新弹幕
    @Override
    public void run() {
        paint2();
    }

    private void paint2() {
        while(true){
            int st=0;
            if(list.size()>maxScreenDanmuCount) st=list.size()-maxScreenDanmuCount;
            for(int i=st;i<list.size();i++) {
                DanContent c=list.get(i);
                c.x-=spzce;
            }
            try {
                Thread.sleep(1000/frame);//
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            repaint();//
        }
    }
    //使用Timer刷新弹幕
    private void paintDanmu() {
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                int st=0;
                if(list.size()>maxScreenDanmuCount) st=list.size()-maxScreenDanmuCount;
                for(int i=st;i<list.size();i++) {
                    DanContent c=list.get(i);
                    c.x-=spzce;
                }
                repaint();//
            }
        }, 100,1000/frame);
    }

    private Color getColor(int idx) {
        switch(idx) {
            case 0:return Color.RED;
            case 1:return Color.BLUE;
            case 2:return Color.BLACK;
            case 3:return Color.CYAN;
            case 4:return Color.DARK_GRAY;
            case 5:return Color.GREEN;
            case 6:return Color.ORANGE;
            case 7:return Color.WHITE;
            case 8:return Color.PINK;

            default :return Color.WHITE;

        }
    }

    public void setMaxDanmuNum(int num) {
        this.maxScreenDanmuCount=num;
    }

    private static DanmuNew instacne=null;
    private static Semaphore semaphore=new Semaphore(0);
    public static synchronized DanmuNew getInstance() {
        if(instacne==null) {
            try {
                show2();
                semaphore.acquire();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return instacne;
        }
        return instacne;
    }



    private static void show2() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                instacne = new DanmuNew();
                instacne.ShowDanmu();
                semaphore.release();
            }
        });
    }
}