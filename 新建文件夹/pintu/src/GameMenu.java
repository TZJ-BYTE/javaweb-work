import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.sound.sampled.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;

public class GameMenu extends JFrame {

    private JFrame settingsFrame;
    private JSlider volumeSlider;
    private JButton fileUploadButton;
    private JLabel volumeLabel;

    public GameMenu() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        String filePath = "music/C418.wav";
        SetMusic.playMusic(filePath, true);

        // 设置窗口标题
        setTitle("游戏菜单");
        // 设置窗口默认关闭操作
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 设置窗口大小
        setBounds(getWidth()/2, getHeight()/2,600, 400);
        setResizable(false);
        // 设置窗口居中
        setLocationRelativeTo(null);
        // 设置布局管理器
        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        setBak("images/back1.jpg");

        // 创建空白面板，用于占据顶部空间
        JPanel spacerPanel = new JPanel();
        spacerPanel.setOpaque(false);
        spacerPanel.setPreferredSize(new Dimension(0, getHeight() * 3 / 4)); // 设置空白面板的高度为容器的3/4

        // 创建下侧按钮面板
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new GridLayout(1, 4, 5, 5)); // 1行4列，水平和垂直间距为5
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // 水平居中对齐

        // 创建按钮
        JButton singlePlayerButton = new JButton("单人模式");
        JButton pvpButton = new JButton("PVP模式");
        JButton settingsButton = new JButton("设置");
        JButton exitButton = new JButton("退出");

        // 为按钮添加动作监听器
        singlePlayerButton.addActionListener(e -> {
            // 单人模式按钮点击事件处理
            System.out.println("单人模式按钮被点击");
            // TODO: Start single player game logic
            initJFrame();
            //new PuzzleGame("j",2);
            dispose();
        });

        pvpButton.addActionListener(e -> {
            // PVP模式按钮点击事件处理
            System.out.println("PVP模式按钮被点击");
            // TODO: Start PVP game logic
            new ServerStatus();
        });

        settingsButton.addActionListener(e -> {
            // 设置按钮点击事件处理
            System.out.println("设置按钮被点击");
            SettingsMenu();
            // TODO: Open settings menu
        });

        exitButton.addActionListener(e -> {
            // 退出按钮点击事件处理
            System.out.println("退出按钮被点击");
            System.exit(0);
        });

        // 将按钮添加到按钮面板
        buttonPanel.add(singlePlayerButton);
        buttonPanel.add(pvpButton);
        buttonPanel.add(settingsButton);
        buttonPanel.add(exitButton);

        // 将空白面板和按钮面板添加到窗口
        add(spacerPanel);
        add(buttonPanel);

        // 显示窗口
        new SetMusic(this);
        setVisible(true);
    }

    public void setBak(String back) {
        ((JPanel) this.getContentPane()).setOpaque(false);
        ImageIcon img = new ImageIcon(back); // 添加图片
        Image scaledImg = img.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH); // 缩放图片以适应窗口
        ImageIcon scaledImgIcon = new ImageIcon(scaledImg);
        JLabel background = new JLabel(scaledImgIcon);
        this.getLayeredPane().add(background, Integer.valueOf(Integer.MIN_VALUE));
        background.setBounds(0, 0, getWidth(), getHeight()); // 设置背景图片的大小为窗口的大小
    }

    public void initJFrame() {
        // 创建难度选择窗口
        JDialog difficultyDialog = new JDialog();
        difficultyDialog.setTitle("难度选择");
        difficultyDialog.setSize(300, 200);
        difficultyDialog.setLocationRelativeTo(null);
        difficultyDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // 使用流布局
        difficultyDialog.setLayout(new FlowLayout());

        // 创建下拉列表，显示难度等级1-9
        Integer[] difficulties = new Integer[9];
        for (int i = 0; i < difficulties.length; i++) {
            difficulties[i] = i + 1;
        }
        JComboBox<Integer> difficultyComboBox = new JComboBox<>(difficulties);

        // 获取文件名列表
        List<String> fileNames = listFileNames("picture/"); // 假设这是图片文件夹的路径

        // 初始化文件名数组
        String[] pic = new String[fileNames.size()];
        fileNames.toArray(pic);

        // 创建图片选择下拉列表
        JComboBox<String> picBox = new JComboBox<>(pic);

        // 创建提交按钮
        JButton submitButton = new JButton("提交");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取用户选择的难度等级
                int difficulty = (int) difficultyComboBox.getSelectedItem();
                String picname = (String) picBox.getSelectedItem();
                // 打印选择的难度等级
                System.out.println("选择的难度等级是: " + difficulty);
                // 关闭难度选择窗口
                difficultyDialog.dispose();
                // 启动单人游戏逻辑
                SwingUtilities.invokeLater(() -> {
                    try {
                        new PuzzleGame(picname, difficulty + 1); // 假设PuzzleGame可以正确处理这些参数
                    } catch (FileNotFoundException ex) {
                        // 更详细的错误处理逻辑
                        System.err.println("文件未找到: " + ex.getMessage());
                    }
                });
            }
        });

        // 添加组件到难度选择窗口
        difficultyDialog.add(difficultyComboBox);
        difficultyDialog.add(picBox);
        difficultyDialog.add(submitButton);

        // 显示难度选择窗口
        difficultyDialog.setVisible(true);
    }

    public static List<String> listFileNames(String directoryPath) {
        List<String> fileNames = new ArrayList<>();
        Path directory = Paths.get(directoryPath);

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
            for (Path file : stream) {
                if (!Files.isDirectory(file)) {
                    fileNames.add(file.getFileName().toString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileNames;
    }

    private void SettingsMenu() {
        // 创建设置窗口
        settingsFrame = new JFrame("设置");
        settingsFrame.setBounds(getWidth()/2, getHeight()/2,400, 200);
        settingsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        settingsFrame.setLayout(new FlowLayout());

        // 创建音量滑块
        volumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        volumeSlider.setMajorTickSpacing(10);
        volumeSlider.setMinorTickSpacing(5);
        volumeSlider.setPaintTicks(true);
        volumeSlider.setPaintLabels(true);
        volumeSlider.addChangeListener(e -> {
            // 更新音量标签
            volumeLabel.setText("音量: " + volumeSlider.getValue() + "%");
            // 这里可以添加设置音量的代码
        });

        // 创建音量标签
        volumeLabel = new JLabel("音量: 50%");

        // 创建文件上传按钮
        fileUploadButton = new JButton("上传文件");
        fileUploadButton.addActionListener(e -> {
            // 打开文件选择对话框
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("文本文件 (*.jpg)", "jpg"));
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();

                // 创建JDialog实例
                JDialog dialog = new JDialog((Frame) null, "输入文件名", true);
                dialog.setLayout(new FlowLayout());

                // 创建输入框
                JTextField textField = new JTextField(20);
                dialog.add(textField);

                // 创建确认按钮
                JButton confirmButton = new JButton("确认");
                confirmButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String inputText = textField.getText();
                        // 关闭对话框
                        dialog.dispose();

                        // 目标文件夹地址
                        String destinationFolder = "picture/";

                        // 使用用户输入的文本作为文件名
                        String destinationFile = destinationFolder + inputText + ".jpg";

                        // 使用Paths工具类获取源文件和目标文件的Path对象
                        Path sourcePath = selectedFile.toPath();
                        Path destinationPath = Paths.get(destinationFile);

                        // 将文件复制到目标路径
                        try {
                            Files.copy(sourcePath, destinationPath);
                            MySQL mysql = new MySQL();
                            mysql.setPicture(inputText);

                            JOptionPane.showMessageDialog(null, "文件已上传: " + destinationPath);
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(null, "文件上传失败: " + ex.getMessage());
                        }
                    }
                });
                dialog.add(confirmButton);

                // 设置对话框的大小、位置和可见性
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        });

        // 添加组件到设置窗口
        settingsFrame.add(volumeSlider);
        settingsFrame.add(volumeLabel);
        settingsFrame.add(fileUploadButton);

        // 显示设置窗口
        settingsFrame.setVisible(true);
    }
}
