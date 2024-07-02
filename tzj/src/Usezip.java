import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.io.*;
import java.sql.*;
import java.util.zip.*;

public class Usezip {
    // 数据库连接字符串
    private String url = "jdbc:mysql://vlab.tute.edu.cn:7186/db0304230217?useSSL=false&useUnicode=true&characterEncoding=UTF-8";
    // 数据库用户名
    private String user = "0304230217";
    // 数据库密码
    private String password = "0304230217";
    private String filePath = "AWT.txt";
    private String username;

    public Usezip() {
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

    public void uploadZipFile(String zipFilePath) {
        Connection connection = null;
        PreparedStatement statement = null;
        FileInputStream fis = null;
        File zipFile = new File(zipFilePath);

        try {
            // 连接到数据库
            connection = DriverManager.getConnection(url, user, password);

            // 准备SQL语句
            String sql = "INSERT INTO compressed_files (file_name, file_data) VALUES (?, ?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, zipFile.getName());

            // 将ZIP文件转换为字节数组
            byte[] zipData = new byte[(int) zipFile.length()];
            fis = new FileInputStream(zipFile);
            fis.read(zipData);

            // 将字节数组设置为预准备语句的参数
            statement.setBytes(2, zipData);

            // 执行SQL语句
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("ZIP file uploaded successfully.");
            } else {
                System.out.println("ZIP file upload failed.");
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException("Error while uploading ZIP file to database.", e);
        } finally {
            // 关闭资源
            try {
                if (fis != null) {
                    fis.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void downloadZipFile(String outputFilePath, String fileName) {
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement("SELECT block_number, block_data FROM file_blocks WHERE file_name = ? ORDER BY block_number")) {

            statement.setString(1, fileName);

            try (ResultSet resultSet = statement.executeQuery();
                 FileOutputStream fos = new FileOutputStream(outputFilePath)) {

                while (resultSet.next()) {
                    byte[] blockData = resultSet.getBytes("block_data");
                    fos.write(blockData);
                }
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException("Error while downloading ZIP file from database.", e);
        }
    }

    public void zips(String zipFilePath, String outputFolder){



        // 创建输出文件夹
        File folder = new File(outputFolder);
        if (!folder.exists()) {
            folder.mkdir();
        }

        // 解压ZIP文件
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry zipEntry = zis.getNextEntry();
            byte[] buffer = new byte[1024];
            while (zipEntry != null) {
                String fileName = zipEntry.getName();
                File newFile = new File(outputFolder + File.separator + fileName);
                // 创建必要的父目录
                new File(newFile.getParent()).mkdirs();
                try (FileOutputStream fos = new FileOutputStream(newFile);
                     BufferedOutputStream bos = new BufferedOutputStream(fos)) {
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        bos.write(buffer, 0, len);
                    }
                }
                zipEntry = zis.getNextEntry();
            }
            zis.closeEntry();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
