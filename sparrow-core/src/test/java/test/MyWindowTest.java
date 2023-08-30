package test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;

public class MyWindowTest {

    public MyWindowTest() {
        ImageIcon image = new ImageIcon("donate.png");
        JLabel label = new JLabel(image);
        JProgressBar bar = new JProgressBar(1, 100);
        bar.setStringPainted(true); //描绘文字bar.setString("加载程序中,请稍候......");
        // 设置显示文字
        bar.setBackground(Color.RED);  //设置背景色
        JWindow win = new JWindow();
        win.add(label, BorderLayout.CENTER);
        win.add(bar, BorderLayout.SOUTH);  //增加进度条到容器上
        win.pack();      //注意先要执行这段代码再设置窗口位置，否则窗口位置将会显示不正确（窗口适应组件）
        // 设置窗口显示的位置
        Dimension screen = win.getToolkit().getScreenSize();
        win.setLocation((screen.width - win.getSize().width) / 2, (screen.height - win.getSize().height) / 2);
        win.setVisible(true);
        myRun(bar, win);
    }

    public void myRun(JProgressBar bar, JWindow win) {
        try {
            for (int i = 0; i < 10; i++) {
                Thread.sleep(10); //线程休眠
                bar.setValue(bar.getValue() + 1); //设置进度条值
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        win.dispose();
        //释放窗口
        JFrame frame = new JFrame();
        frame.setSize(300, 300);
        frame.setLocationRelativeTo(frame.getOwner());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
//        test.MyWindowTest windowt = new test.MyWindowTest();
    }
}
