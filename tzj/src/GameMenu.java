import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameMenu extends JFrame {
    public GameMenu() {
        // 设置窗口标题
        setTitle("游戏菜单");
        // 设置窗口默认关闭操作
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 设置窗口大小
        setSize(300, 200);
        // 设置窗口居中
        setLocationRelativeTo(null);
        // 设置布局管理器
        setLayout(new GridLayout(4, 1, 5, 5)); // 4行1列，水平和垂直间距为5

        // 创建按钮
        JButton singlePlayerButton = new JButton("单人模式");
        JButton pvpButton = new JButton("PVP模式");
        JButton settingsButton = new JButton("设置");
        JButton exitButton = new JButton("退出");

        // 为按钮添加动作监听器
        singlePlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("单人模式按钮被点击");
                // 在这里添加单人模式的代码
            }
        });

        pvpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("PVP模式按钮被点击");
                // 在这里添加PVP模式的代码
            }
        });

        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("设置按钮被点击");
                // 在这里添加设置功能的代码
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("退出按钮被点击");
                // 关闭窗口
                dispose();
            }
        });

        // 将按钮添加到窗口
        add(singlePlayerButton);
        add(pvpButton);
        add(settingsButton);
        add(exitButton);

        // 显示窗口
        setVisible(true);
    }

    public static void main(String[] args) {
        // 使用SwingUtilities.invokeLater确保GUI的创建和更新在事件调度线程中执行
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GameMenu();
            }
        });
    }
}
