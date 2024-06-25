import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInput;
import java.sql.*;

public class Window {
    private boolean pass=true;
    private int height=500;
    private int width=800;
    //数据库连接字符串
    private String url = "jdbc:mysql://vlab.tute.edu.cn:7186/db0304230217?useSSL=false&useUnicode=true&characterEncoding=UTF-8";
    // 数据库用户名
    private String user = "0304230217";
    // 数据库密码
    private String password = "0304230217";

    public Window(){

        setPass(true);

        run();

        while (this.getPass()){
            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean getPass(){
        return pass;
    }
    public void setPass(boolean pass){
        this.pass = pass;
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
        mainFrame.setSize(width, height); // 设置窗口的大小

        JLabel label1 = new JLabel("用户名:");
        label1.setFont(new Font("SimSun", Font.BOLD, 14));
        label1.setBounds(190, 100, 300, 30);
        mainFrame.getContentPane().add(label1);

        JTextField textField1 = new JTextField();
        textField1.setBounds(250, 100, 300, 30);
        mainFrame.getContentPane().add(textField1);
        textField1.setColumns(10);

        JLabel label2 = new JLabel("密码:");
        label2.setFont(new Font("SimSun", Font.BOLD, 14));
        label2.setBounds(204, 200, 300, 30);
        mainFrame.getContentPane().add(label2);


        JPasswordField passwordField1 = new JPasswordField();
        passwordField1.setBounds(250, 200, 300, 30);
        mainFrame.getContentPane().add(passwordField1);

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
                    setPass(false);
                    mainFrame.dispose();
                }
            }
        });


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
            // 更好的异常处理，例如记录日志
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
                        return true;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("登录失败");
        return false;
    }

    public void neWindow(Frame mainFrame,String url, String user, String password, String username, char[] userpassword){
        String userpass = new String(userpassword);

        JDialog dialog = new JDialog(mainFrame, "弹出窗口");
        dialog.setTitle("注册账号");
        // 绝对布局
        dialog.getContentPane().setLayout(null);
        dialog.setSize(600, 450);
        dialog.setLocation(mainFrame.getWidth() / 2 - dialog.getWidth() / 2, mainFrame.getHeight() / 2 - dialog.getHeight() / 2);



        JLabel label1 = new JLabel("用户名:");
        label1.setFont(new Font("SimSun", Font.BOLD, 14));
        label1.setBounds(86, 50, 300, 30);
        dialog.getContentPane().add(label1);


        JTextField textField1 = new JTextField();
        textField1.setBounds(140, 50, 300, 30);
        dialog.getContentPane().add(textField1);
        textField1.setColumns(10);

        JLabel label2 = new JLabel("密码:");
        label2.setFont(new Font("SimSun", Font.BOLD, 14));
        label2.setBounds(100, 100, 300, 30);
        dialog.getContentPane().add(label2);

        JPasswordField passwordField1 = new JPasswordField();
        passwordField1.setBounds(140, 100, 300, 30);
        dialog.getContentPane().add(passwordField1);

        JLabel label3 = new JLabel("确认密码:");
        label3.setFont(new Font("SimSun", Font.BOLD, 14));
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


        dialog.setVisible(true);
    }
}