import java.awt.*;
import javax.swing.*;


public class BallGame2 extends JFrame{
    Image ball = Toolkit.getDefaultToolkit().getImage("image/ball.png");
    Image desk = Toolkit.getDefaultToolkit().getImage("image/desk.jpg");
    double x=100;
    double y=100;
    double degree = 3.14/3;
    //画窗口的方法
    public void paint(Graphics g)
    {
        System.out.println("窗口被画了一次!");
        g.drawImage(desk,0,0,null);
        g.drawImage(ball,(int)x,(int)y,null);
        x=x+10*Math.cos(degree);
        y=y+10*Math.sin(degree);
        if(y>500-40-30||y<40+40)//500是窗口的高度，40是桌子边框，30是球的直径，最后一个40是标题栏的宽度
        {
            degree=-degree;
        }
        //碰到左右边界
        if(x<0+40||x>856-40-30)
        {
            degree=3.14-degree;
        }

    }
    //窗口加载
    void launchFrame(){
        setSize(856,500);
        setLocation(50,50);
        setVisible(true);
        //重画窗口,每秒20次
        while(true)
        {
            repaint();
            try{
                Thread.sleep(40);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    //main方法，程序执行的入口
    public static void main(String[] args)
    {
        System.out.println("寓教于乐！");
        BallGame2 game =new BallGame2();
        game.launchFrame();
    }
}
