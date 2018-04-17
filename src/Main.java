import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {
    static boolean start = false;
    boolean pause = false; //控制线程执行的内容 产生随机数或者什么都不做
    JLabel label1 = new JLabel();
    JLabel label2 = new JLabel();
    JButton btn1 = new JButton("开始");
    JButton btn2 = new JButton("结束");
    Print p1 = new Print(label1);//把label和一个线程封装起来
    Print p2 = new Print(label2);

    //构造函数负责构造界面架构和界面功能（摇号按钮等）
    public Main() {
        setTitle("摇号机");
        setSize(600, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        init();//初始化按钮等
    }

    //初始化按钮和标签
    public void init() {
        add(label1);
        add(label2);
        add(btn1);
        add(btn2);
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start();//启动线程
            }
        });
        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start = pause;//相当于使线程不做任何事情
            }
        });
        setVisible(true);

    }

    public void start() {
        start = true;
        Thread thread1 = new Thread(p1);
        Thread thread2 = new Thread(p2);
        thread1.start();
        thread2.start();
    }

    public static void main(String[] args) {
        new Main();
    }

}

class Print implements Runnable {
    JLabel jLabel = null;//用于展示结果

    public Print(JLabel jLabel) {
        this.jLabel = jLabel;
    }

    @Override
    public void run() {
        while (Main.start) {
            int a = (int) (0 + Math.random() * 9);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String num = String.valueOf(a);
            jLabel.setText(num);
        }
    }
}
