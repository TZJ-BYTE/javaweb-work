import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQL {
    //数据库连接字符串
    private String url = "jdbc:mysql://vlab.tute.edu.cn:7186/db0304230217?useSSL=false&useUnicode=true&characterEncoding=UTF-8";
    // 数据库用户名
    private String user = "0304230217";
    // 数据库密码
    private String password = "0304230217";
    private String filePath="AWT.txt";
    private String username;

    public MySQL() {

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // 读取第一行作为用户名
            username = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 加载并注册JDBC驱动
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getUsername(){
        return username;
    }

    public int getScore(int grade) {
        // 创建数据库连接
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            // 使用占位符来避免SQL注入
            String checkSql = "SELECT * FROM users WHERE name = ?";
            try (PreparedStatement checkStmt = connection.prepareStatement(checkSql)) {
                checkStmt.setString(1, username);
                ResultSet resultSet = checkStmt.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getInt("score" + grade);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }


    public void setScore(int grade, int myScore) {
        // 创建数据库连接
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            // 使用占位符来避免SQL注入
            String sgrade = String.valueOf(grade);
            String scoreall = "score" + sgrade;
            String checkSql = "UPDATE users SET " + scoreall + " = ? WHERE name = ?";

            try (PreparedStatement checkStmt = connection.prepareStatement(checkSql)) {
                // 设置SQL语句中的占位符
                checkStmt.setInt(1, myScore);
                checkStmt.setString(2, username);

                // 执行更新操作
                int rowsUpdated = checkStmt.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("数据更新成功！");
                } else {
                    System.out.println("数据更新失败或数据未改变。");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




    public List<String> getLeaderboardData(int sortByColumnIndex) {
        List<String> leaderboard = new ArrayList<>();
        // 创建数据库连接
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            // 使用占位符来避免SQL注入
            String leaderboardSql = "SELECT name, score2, score3, score4, score5, score6, score7, score8, score9, score10 FROM users ORDER BY %s DESC";
            String orderByColumn = "score" + sortByColumnIndex;
            leaderboardSql = String.format(leaderboardSql, orderByColumn);

            try (PreparedStatement leaderboardStmt = connection.prepareStatement(leaderboardSql)) {
                ResultSet resultSet = leaderboardStmt.executeQuery();
                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    int score2 = resultSet.getInt("score2");
                    int score3 = resultSet.getInt("score3");
                    int score4 = resultSet.getInt("score4");
                    int score5 = resultSet.getInt("score5");
                    int score6 = resultSet.getInt("score6");
                    int score7 = resultSet.getInt("score7");
                    int score8 = resultSet.getInt("score8");
                    int score9 = resultSet.getInt("score9");
                    int score10 = resultSet.getInt("score10");
                    leaderboard.add(name + ": " + score2 + ", " + score3 + ", " + score4 + ", " + score5 + ", " + score6 + ", " + score7 + ", " + score8 + ", " + score9 + ", " + score10);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return leaderboard;
    }


    public void printLeaderboard() {
        List<String> leaderboardData = getLeaderboardData(1);
        int rank = 1;
        for (String entry : leaderboardData) {
            System.out.println(String.format("%d. %s", rank, entry));
            rank++;
        }
    }




    public void setPicture(String picturePath) {
        // 创建数据库连接
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            // 检查用户名是否已存在
            String checkSql = "SELECT * FROM picture WHERE ypicture = ?";
            try (PreparedStatement checkStmt = connection.prepareStatement(checkSql)) {
                checkStmt.setString(1, picturePath);
                ResultSet resultSet = checkStmt.executeQuery();
                if (resultSet.next()) {
                    System.out.println("图片名称存在！");
                    return; // 如果已存在，则不继续执行
                }
            }

            // 读取图片文件
            File pictureFile = new File("picture/"+picturePath+".jpg");
            BufferedImage image = ImageIO.read(pictureFile);

            // 将图片转换为二进制数组
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", baos);
            byte[] imageBytes = baos.toByteArray();

            // 插入图片数据到数据库
            String insertSql = "INSERT INTO picture (ypicture) VALUES (?)";
            try (PreparedStatement insertStmt = connection.prepareStatement(insertSql)) {
                insertStmt.setBytes(1, imageBytes);
                int rowsAffected = insertStmt.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("图片数据插入成功！");
                } else {
                    System.out.println("图片数据插入失败！");
                }
            }
        } catch (SQLException e) {
            // 异常处理，记录日志
            e.printStackTrace();
        } catch (IOException e) {
            // 图片读取异常处理
            e.printStackTrace();
        }
    }


}