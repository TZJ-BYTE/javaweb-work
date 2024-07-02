import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.*;

public class Window {
    private String filePath = "AWT.txt";
    private String token=null;
    private int height=500;
    private int width=800;
    //数据库连接字符串
    private String url = "jdbc:mysql://vlab.tute.edu.cn:7186/db0304230217?useSSL=false&useUnicode=true&characterEncoding=UTF-8";
    // 数据库用户名
    private String user = "0304230217";
    // 数据库密码
    private String password = "0304230217";

    public Window(){
        run();
    }


    public void run() {

        // 加载并注册JDBC驱动
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        JFrame mainFrame = new JFrame();
        mainFrame.setTitle("Window");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 当用户关闭窗口时，程序会终止。
        // 绝对布局
        mainFrame.setResizable(false);
        mainFrame.getContentPane().setLayout(null);
        mainFrame.setBounds(350, 150,width,height); // 设置窗口的大小
        setBak(mainFrame,"images/back9.jpg");

        JLabel label1 = new JLabel("用户名:");
        label1.setFont(new Font("SimSun", Font.BOLD, 14));
        label1.setForeground(Color.BLACK); // 设置字体颜色
        label1.setBounds(190, 100, 300, 30);
        mainFrame.getContentPane().add(label1);

        JTextField textField1 = new JTextField();
        textField1.setBounds(250, 100, 300, 30);
        mainFrame.getContentPane().add(textField1);
        textField1.setColumns(10);

        JLabel label2 = new JLabel("密码:");
        label2.setFont(new Font("SimSun", Font.BOLD, 14));
        label2.setForeground(Color.BLACK); // 设置字体颜色
        label2.setBounds(204, 200, 300, 30);
        mainFrame.getContentPane().add(label2);

        JPasswordField passwordField1 = new JPasswordField();
        passwordField1.setBounds(250, 200, 300, 30);
        mainFrame.getContentPane().add(passwordField1);

        String[] userAndPassword = checkPCUserAndPassword();
        if (userAndPassword != null) {
            textField1.setText(userAndPassword[0]);
            passwordField1.setText(userAndPassword[1]);
        }

        JButton button1 = new JButton("立即注册");
        button1.setBounds(200, 300, 100, 30); // 设置按钮的位置和大小
        mainFrame.getContentPane().add(button1); // 将按钮添加到内容面板
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //add(url,user,password,textField1.getText(),passwordField1.getPassword());
                neWindow(mainFrame,url,user,password,textField1.getText(),passwordField1.getPassword());
            }
        });

        JButton button2 = new JButton("登录");
        button2.setBounds(450, 300, 100, 30); // 设置按钮的位置和大小
        mainFrame.getContentPane().add(button2); // 将按钮添加到内容面板
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fpassword = new String(passwordField1.getPassword());
                if (textField1.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(mainFrame, "账号输入不能为空");
                } else if (fpassword.isEmpty()) {
                    JOptionPane.showMessageDialog(mainFrame, "密码输入不能为空");
                }else if(check(url,user,password,textField1.getText(),passwordField1.getPassword())){
                    mainFrame.dispose();
                }
            }
        });

        new SetMusic(mainFrame);
        mainFrame.setVisible(true); // 将窗口设置为可视[在添加完所有组件之后运行]
    }

    private boolean add(String url, String user, String password, String username, char[] userpassword) {
        String userpass = new String(userpassword);


        // 创建数据库连接
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            // 使用占位符来避免SQL注入
            String checkSql = "SELECT * FROM users WHERE name = ?";
            try (PreparedStatement checkStmt = connection.prepareStatement(checkSql)) {
                checkStmt.setString(1, username);
                ResultSet resultSet = checkStmt.executeQuery();
                if (resultSet.next()) {
                    System.out.println("用户名已存在！");
                    return false;
                }
            }

            // 用户名不存在，可以插入新记录
            String insertSql = "INSERT INTO users (name, password) VALUES (?, ?)";
            try (PreparedStatement insertStmt = connection.prepareStatement(insertSql)) {
                insertStmt.setString(1, username);
                insertStmt.setString(2, userpass);
                int rowsAffected = insertStmt.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("数据插入成功！");
                    return true;
                } else {
                    System.out.println("数据插入失败！");
                    return false;
                }
            }
        } catch (SQLException e) {
            // 异常处理，记录日志
            e.printStackTrace();
            return false;
        }
    }



    private boolean check(String url, String user, String password, String username, char[] userpassword) {
        String userpass = new String(userpassword);

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            try (Statement statement = connection.createStatement()) {
                // 根据用户名查询用户
                ResultSet resultSet = statement.executeQuery("SELECT * FROM users WHERE name = '" + username + "'");
                if (resultSet.next()) {
                    // 比较数据库中的密码和用户输入的密码
                    if (resultSet.getString("password").equals(userpass)) {
                        System.out.println("登录成功");
                        setLoginToken(username);
                        String insertSql = "update users set login_token=? where name=?";
                        try (PreparedStatement insertStmt = connection.prepareStatement(insertSql)) {
                            insertStmt.setString(1, token);
                            insertStmt.setString(2, username);
                            int rowsAffected = insertStmt.executeUpdate();
                            if (rowsAffected > 0) {
                                System.out.println("Token 更新成功: " + username);
                            } else {
                                System.out.println("Token 更新失败: " + username);
                            }
                        }
                        new GameMenu();
                        return true;
                    }
                }
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            } catch (UnsupportedAudioFileException e) {
                throw new RuntimeException(e);
            } catch (LineUnavailableException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("登录失败");
        return false;
    }

    public void neWindow(JFrame mainFrame,String url, String user, String password, String username, char[] userpassword){
        String userpass = new String(userpassword);

        JDialog dialog = new JDialog(mainFrame, "弹出窗口");
        dialog.setTitle("注册账号");
        // 绝对布局
        dialog.getContentPane().setLayout(null);
        dialog.setSize(550, 350);
        dialog.setLocation(mainFrame.getWidth()  - dialog.getWidth()/2 , mainFrame.getHeight() - dialog.getHeight()+50);
        setBak2(dialog,"images/back9.jpg");


        JLabel label1 = new JLabel("用户名:");
        label1.setFont(new Font("SimSun", Font.BOLD, 14));
        label1.setForeground(Color.BLACK); // 设置字体颜色
        label1.setBounds(86, 50, 300, 30);
        dialog.getContentPane().add(label1);


        JTextField textField1 = new JTextField();
        textField1.setBounds(140, 50, 300, 30);
        dialog.getContentPane().add(textField1);
        textField1.setColumns(10);

        JLabel label2 = new JLabel("密码:");
        label2.setFont(new Font("SimSun", Font.BOLD, 14));
        label2.setForeground(Color.BLACK); // 设置字体颜色
        label2.setBounds(100, 100, 300, 30);
        dialog.getContentPane().add(label2);

        JPasswordField passwordField1 = new JPasswordField();
        passwordField1.setBounds(140, 100, 300, 30);
        dialog.getContentPane().add(passwordField1);

        JLabel label3 = new JLabel("确认密码:");
        label3.setFont(new Font("SimSun", Font.BOLD, 14));
        label3.setForeground(Color.BLACK); // 设置字体颜色
        label3.setBounds(72, 150, 300, 30);
        dialog.getContentPane().add(label3);

        JPasswordField passwordField2 = new JPasswordField();
        passwordField2.setBounds(140, 150, 300, 30);
        dialog.getContentPane().add(passwordField2);

        JButton button1 = new JButton("注册");
        button1.setBounds(250, 200, 100, 30); // 设置按钮的位置和大小
        dialog.add(button1); // 将按钮添加到内容面板

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fpassword = new String(passwordField1.getPassword());
                String repassword = new String(passwordField2.getPassword());

                if (!textField1.getText().isEmpty()&&!fpassword.isEmpty() && !repassword.isEmpty() && fpassword.equals(repassword)) {
                    if(add(url, user, password, textField1.getText(), passwordField1.getPassword())){
                        dialog.dispose();
                    }
                } else if (textField1.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "账号输入不能为空");
                } else if (fpassword.isEmpty() || repassword.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "密码输入不能为空");
                }else {
                    JOptionPane.showMessageDialog(dialog, "两次输入密码不一致");
                }
            }
        });

        new SetMusic(dialog);
        dialog.setVisible(true);
    }


    private void setLoginToken(String username) throws UnknownHostException {
        // 获取本机InetAddress对象
        InetAddress address = InetAddress.getLocalHost();
        // 获取主机名
        String hostName = address.getHostName();
        token=hostName+"-"+username;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // 写入数据到文件
            writer.write(username);
            writer.newLine(); // 写入一个新行
            writer.write(token);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String[] checkPCUserAndPassword() {
        try (Connection connection = DriverManager.getConnection(url, user, password);
             BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            // 读取第一行作为用户名
            String username = reader.readLine();
            // 读取第二行作为登录令牌
            String loginToken = reader.readLine();

            if (username != null && loginToken != null) {
                // 检查用户名和令牌是否在数据库中
                String checkUserSql = "SELECT * FROM users WHERE name = ? AND login_token = ?";
                try (PreparedStatement checkUserStmt = connection.prepareStatement(checkUserSql)) {
                    checkUserStmt.setString(1, username);
                    checkUserStmt.setString(2, loginToken);
                    ResultSet resultSet = checkUserStmt.executeQuery();
                    if (resultSet.next()) {
                        // 用户名和令牌匹配，从数据库中读取密码
                        String userPassword = resultSet.getString("password");
                        return new String[]{username, userPassword};
                    } else {
                        System.out.println("用户名或令牌不匹配");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null; // 如果有任何错误或检查不通过，返回null
    }
    public void setBak(JFrame frame, String back) {
        ((JPanel)frame.getContentPane()).setOpaque(false);
        ImageIcon img = new ImageIcon(back); // 添加图片
        Image scaledImg = img.getImage().getScaledInstance(frame.getWidth(), frame.getHeight(), Image.SCALE_SMOOTH); // 缩放图片以适应窗口
        ImageIcon scaledImgIcon = new ImageIcon(scaledImg);
        JLabel background = new JLabel(scaledImgIcon);
        frame.getLayeredPane().add(background, Integer.valueOf(Integer.MIN_VALUE));
        background.setBounds(0, 0, frame.getWidth(), frame.getHeight()); // 设置背景图片的大小为窗口的大小
    }

    public void setBak2(JDialog frame, String back) {
        ((JPanel)frame.getContentPane()).setOpaque(false);
        ImageIcon img = new ImageIcon(back); // 添加图片
        Image scaledImg = img.getImage().getScaledInstance(frame.getWidth(), frame.getHeight(), Image.SCALE_SMOOTH); // 缩放图片以适应窗口
        ImageIcon scaledImgIcon = new ImageIcon(scaledImg);
        JLabel background = new JLabel(scaledImgIcon);
        frame.getLayeredPane().add(background, Integer.valueOf(Integer.MIN_VALUE));
        background.setBounds(0, 0, frame.getWidth(), frame.getHeight()); // 设置背景图片的大小为窗口的大小
    }
}