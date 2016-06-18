package me.brucezz.crawler;

import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Swing Test
 * 
 * @author hyliu
 * @date 2016-6-18
 */
public class HelloWorldSwing {

    static JPanel p1, p2;
    static ImageIcon []ic={new ImageIcon("img1.gif"),new ImageIcon("img2.gif"),new ImageIcon("img3.gif"),new ImageIcon("img4.gif"),new ImageIcon("img5.gif")};
    /**{
     * 创建并显示GUI。出于线程安全的考虑，
     * 这个方法在事件调用线程中调用。
     */
    private static void createAndShowGUI() {
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);

        //Create and set up the window.
        JFrame frame = new JFrame("HelloWorldSwing");

        frame.setSize(1400, 1500);
        frame.setVisible(true);
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = frame.getContentPane();// 添加容器
        c.setLayout(new GridLayout(2, 1));
        p1 = new JPanel();// 两个面板
        p2 = new JPanel();
        c.add(p1);
        c.add(p2);
        //Add the ubiquitous "Hello World" label.
        JLabel label = new JLabel("Hello World");
        
        c.add(label);

        //Display the window.
        frame.pack();
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}