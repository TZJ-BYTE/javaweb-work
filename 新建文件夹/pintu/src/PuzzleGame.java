
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class PuzzleGame extends JFrame implements KeyListener, ActionListener {
    private int[][] data ;
    private int x = 5;
    private int y = 5;
    private int[][] win;
    private int step = 0;
    private JMenuItem replay = new JMenuItem("重新游戏");
    private JMenuItem close = new JMenuItem("退出");
    private JMenuItem account = new JMenuItem("关于");
    private JMenuItem relogin = new JMenuItem("重新登陆");
    private JMenuItem grade = new JMenuItem("最好成绩");
    private JMenuItem chart =new JMenuItem("排行榜");
    private String pathrode = "/picture";
    private int n;
    private String id=null;
    private int myscore;
    private Instant start,end ;
    private long time=0;
    private int plong;

    public PuzzleGame(String id, int n) throws FileNotFoundException {
        MySQL mysql = new MySQL();

        myscore= mysql.getScore(n);

        new GameJframe(id,n);
        if (id.length() >= 4) {
            String modifiedString = id.substring(0, id.length() - 4);
            System.out.println("Modified String: " + modifiedString);
            System.out.println(id.substring(id.length() - 4));
            this.id = modifiedString;
        } else {
            System.out.println("字符串长度不足，无法删除四个字符。");
        }

        this.n = n;
        data = new int[n][n];
        win = new int[n][n];
        int num=0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                win[i][j]=num;
                num++;
            }
        }
        start=Instant.now();
        initJFrame();
        initJMenuBar();
        initData();
        initImage();
        new SetMusic(this);
        setVisible(true);
    }

    private void initData() {
        int num = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                win[i][j] = num++;
            }
        }
        Random r = new Random();
        int[] arr = new int[n * n];
        if (n != 2) {
            for (int i = 0; i < arr.length; i++) {
                arr[i] = i;
            }
            // 随机打乱arr数组
            for (int i = arr.length - 1; i > 0; i--) {
                int index = r.nextInt(i + 1);
                int temp = arr[index];
                arr[index] = arr[i];
                arr[i] = temp;
            }
        } else {
            // 当n=2时，初始化arr为{2, 1, 3, 0}
            arr = new int[]{1, 3, 2, 0};
            System.out.println(n);
        }

        int arrnum = 0;
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data.length; j++) {
                data[i][j] = arr[arrnum++];
            }
        }

        // 将0值移动到数组的最后一个位置
        for (int i = data.length - 1; i >= 0; i--) {
            for (int j = data.length - 1; j >= 0; j--) {
                if (data[i][j] == 0) {
                    data[i][j] = data[data.length - 1][data.length - 1];
                    data[data.length - 1][data.length - 1] = 0;
                    x = data.length - 1;
                    y = data.length - 1;
                    break;
                }
            }
        }
    }




    private void initImage() {
        this.getContentPane().removeAll();
        if (victory()) {
            JLabel winJLabel = new JLabel(new ImageIcon("images/win.png"));
            winJLabel.setBounds(getWidth()/2-250, getHeight()+100, 500, 250);
            add(winJLabel);


            end = Instant.now();
            Duration duration = Duration.between(start, end);
            time = duration.toMillis(); // 转换为毫秒
            double weightS = 2.0*n; // 步数的权重
            double weightT = 1.0*n; // 时间的权重

            myscore = (int) (n*n*1000*(weightS / (step + 1)) + (weightT / (time + 1)));

            MySQL mySQL=new MySQL();
            mySQL.setScore(n,myscore);


            JDialog win = new JDialog((Frame) null, "最终得分", true);
            win.setBounds(getWidth()/2, getHeight()/2, 250, 150);
            JLabel winLabel = new JLabel(String.valueOf(myscore));
            winLabel.setBounds(getWidth()/2, getHeight()/2, 500, 250);
            win.add(winLabel);
            win.setVisible(true);

        }

        JLabel stepCount = new JLabel("步数: " + step);
        stepCount.setBounds(50, 50, 150, 30); // 设置标签的位置和大小
        stepCount.setFont(new Font("SimSun", Font.BOLD, 25)); // 设置字体为Arial，加粗，大小为16
        stepCount.setForeground(Color.BLACK); // 设置文字颜色为白色
        this.getContentPane().add(stepCount); // 将标签添加到内容面板

        int icnum = 0;
        ImageIcon icon[] = new ImageIcon[n*n];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data.length; j++) {
                icon[icnum] = new ImageIcon("output_images/"+id+"_"+n+"/" + "image_" + i + "_" + j + ".jpg");
                icnum++;
            }
        }

        // 获取本地图形环境
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        // 获取默认屏幕设备
        Dimension screenSize = ge.getMaximumWindowBounds().getSize();

        plong =(screenSize.height-100)/n;



        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data.length; j++) {
                int num = data[i][j];
                // 加载图片并调整大小
                Image image = icon[num].getImage();
                Image scaledImage = image.getScaledInstance(plong, plong, Image.SCALE_SMOOTH);
                icon[num] = new ImageIcon(scaledImage);

                JLabel jLabel = new JLabel(icon[num]);
                jLabel.setBounds(plong * j + 600, plong * i +20, plong, plong);
                jLabel.setBorder(new BevelBorder(BevelBorder.LOWERED));

                this.getContentPane().add(jLabel);
            }
        }
        JLabel background = new JLabel(new ImageIcon("picture/back.jpg"));
        background.setBounds(50, 40, 508, 560);
        this.getContentPane().add(background);
        this.getContentPane().repaint();
    }


    private void initJMenuBar() {
        JMenuBar jMenuBar = new JMenuBar();
        JMenu functionJMenu = new JMenu("功能");
        JMenu aboutJMenu = new JMenu("帮助");
        JMenu gradeJMenu = new JMenu("成绩");
        functionJMenu.add(replay);
        functionJMenu.add(relogin);
        functionJMenu.add(close);
        aboutJMenu.add(account);
        gradeJMenu.add(grade);
        gradeJMenu.add(chart);
        replay.addActionListener((ActionListener) this);
        close.addActionListener((ActionListener) this);
        account.addActionListener((ActionListener) this);
        relogin.addActionListener((ActionListener) this);
        grade.addActionListener((ActionListener) this);
        chart.addActionListener((ActionListener) this);
        jMenuBar.add(functionJMenu);
        jMenuBar.add(aboutJMenu);
        jMenuBar.add(gradeJMenu);
        this.setJMenuBar(jMenuBar);
    }

    private void initJFrame() {
        // 获取本地图形环境
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        // 获取默认屏幕设备
        Dimension screenSize = ge.getMaximumWindowBounds().getSize();

        // 设置窗口大小
        setSize(screenSize.width, screenSize.height);
        setTitle("拼图");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 当用户关闭窗口时，程序会终止。
        setLocationRelativeTo(null);
        setDefaultCloseOperation(2);
        setLayout(null);
        setResizable(false);
        addKeyListener((KeyListener) this);

        // 获取JFrame的内容面板并设置背景颜色
        Container contentPane = getContentPane();
        contentPane.setBackground(new Color(255, 200, 200)); // 粉色背景
    }


    public void keyTyped(KeyEvent e) {
    }


    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == 112 || code == 65) {
            this.getContentPane().removeAll();
            JLabel all = new JLabel(new ImageIcon("picture/"+id+".jpg"));
            all.setBounds(600, 20, plong*n, plong*n);
            this.getContentPane().add(all);
            this.getContentPane().repaint();
        }
    }

    public void keyReleased(KeyEvent e) {
        if (victory()) {
            return;
        }
        int code = e.getKeyCode();
        System.out.println(code);
        if (code == 39) {
            System.out.println("向左移动");
            if (y == n-1) {
                return;
            }
            data[x][y] = data[x][y + 1];
            data[x][y + 1] = 0;
            y++;
            step++;
            initImage();
        } else if (code == 40) {
            System.out.println("向上移动");
            if (x == n-1) {
                return;
            }
            data[x][y] = data[x + 1][y];
            data[x + 1][y] = 0;
            x++;
            step++;
            initImage();
        } else if (code == 37) {
            System.out.println("向右移动");
            if (y == 0) {
                return;
            }
            data[x][y] = data[x][y - 1];
            data[x][y - 1] = 0;
            y--;
            step++;
            initImage();
        } else if (code == 38) {
            System.out.println("向下移动");
            if (x == 0) {
                return;
            }
            data[x][y] = data[x - 1][y];
            data[x - 1][y] = 0;
            x--;
            step++;
            initImage();
        } else if (code == 65) {
            initImage();
        } else if (code == 87) {
            int num=0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    win[i][j]=num;
                    num++;
                }
            }
            initImage();
        }
    }

    public boolean victory() {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (data[i][j] != win[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if (obj == replay) {
            System.out.println("重新游戏");
            step = 0;
            //initData();
            //initImage();
            setVisible(false);
            try {
                new GameMenu();
            } catch (UnsupportedAudioFileException ex) {
                throw new RuntimeException(ex);
            } catch (LineUnavailableException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } else if (obj == relogin) {
            System.out.println("重新登录");
            setVisible(false);
            new Window();
        } else if (obj == close) {
            System.out.println("关闭游戏");
            System.exit(0);
        } else if (obj == account) {
            System.out.println("关于");
            JDialog jDialog = new JDialog();
            jDialog.setSize(344, 344);
            JLabel clue = new JLabel("a刷新，长按a显示原图，就这些了");
            clue.setBounds(0, 0, 100, 20);
            jDialog.getContentPane().add(clue);
            jDialog.setAlwaysOnTop(true);
            jDialog.setLocationRelativeTo(null);
            jDialog.setModal(true);
            new SetMusic(jDialog);
            jDialog.setVisible(true);
        }else if (obj == grade) {
            System.out.println("个人最好成绩");
            JDialog a=new JDialog();

            a.setSize(344, 344);

            JLabel grade = new JLabel("您的最好成绩是"+myscore);
            grade.setBounds(0, 0, 100, 20);
            a.getContentPane().add(grade);
            a.setAlwaysOnTop(true);
            a.setLocationRelativeTo(null);
            a.setModal(true);
            new SetMusic(a);
            a.setVisible(true);

        }else if (obj == chart) {
            MySQL mysql=new MySQL();
            JFrame frame = new JFrame("排行榜");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(500, 600);
            frame.setLocationRelativeTo(null);
            frame.setAlwaysOnTop(true);

            // 创建 JList 并设置模型为 leaderboard
            JList<String> list = new JList<>(mysql.getLeaderboardData(n).toArray(new String[0]));
            // 为了使列表可滚动，我们将其放入 JScrollPane
            JScrollPane scrollPane = new JScrollPane(list);
            scrollPane.setPreferredSize(new Dimension(480, 580));

            // 将 JScrollPane 添加到 JFrame
            frame.add(scrollPane);
            frame.setVisible(true);
        }
    }

}