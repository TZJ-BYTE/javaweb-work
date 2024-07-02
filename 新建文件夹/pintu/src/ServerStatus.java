import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ServerStatus {
    private JLabel connectionStatus;
    private JLabel roomStatus;
    private JButton connectButton;
    private JButton createRoomButton;
    private JButton joinRoomButton;
    private JTextField inviteCodeField;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private int serverPort;
    private String serverIP;

    public ServerStatus() {
        JFrame frame = new JFrame("服务器状态");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(550, 300, 400, 250);
        frame.setLayout(new FlowLayout());

        connectionStatus = new JLabel("未连接");
        roomStatus = new JLabel("未加入房间");

        connectButton = new JButton("连接服务器");
        createRoomButton = new JButton("创建房间");
        joinRoomButton = new JButton("加入房间");
        inviteCodeField = new JTextField(10);

        createRoomButton.setEnabled(false); // 默认禁用，直到连接到服务器
        joinRoomButton.setEnabled(false); // 默认禁用，直到连接到服务器

        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                discoverServer();
            }
        });

        createRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createRoom();
            }
        });

        joinRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                joinRoom(inviteCodeField.getText());
            }
        });

        frame.add(connectionStatus);
        frame.add(connectButton);
        frame.add(createRoomButton);
        frame.add(inviteCodeField);
        frame.add(joinRoomButton);
        frame.add(roomStatus);
        frame.setVisible(true);
    }

    private void discoverServer() {
        new Thread(() -> {
            try (DatagramSocket udpSocket = new DatagramSocket()) {
                byte[] buffer = new byte[1024];
                InetAddress address = InetAddress.getByName("255.255.255.255");
                String message = "DISCOVER_FIT_SERVER";
                buffer = message.getBytes();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 8888);
                udpSocket.send(packet);

                buffer = new byte[1024];
                packet = new DatagramPacket(buffer, buffer.length);
                udpSocket.receive(packet);
                serverIP = new String(packet.getData(), 0, packet.getLength()).trim();

                SwingUtilities.invokeLater(() -> {
                    connectToServer(serverIP);
                });
            } catch (IOException e) {
                e.printStackTrace();
                SwingUtilities.invokeLater(() -> {
                    connectionStatus.setText("服务器发现失败");
                });
            }
        }).start();
    }

    private void connectToServer(String serverIP) {
        serverPort = 2317;

        try {
            socket = new Socket(serverIP, serverPort);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            connectionStatus.setText("已连接到服务器：" + serverIP);
            connectButton.setEnabled(false);
            createRoomButton.setEnabled(true);
            joinRoomButton.setEnabled(true);
        } catch (IOException e) {
            connectionStatus.setText("连接失败");
            e.printStackTrace();
        }
    }

    private void createRoom() {
        out.println("CREATE_ROOM");

        try {
            String inviteCode = in.readLine();
            if (inviteCode != null && !inviteCode.isEmpty()) {
                roomStatus.setText("房间已创建，：" + inviteCode);
                JOptionPane.showMessageDialog(null, "您的邀请码是：" + inviteCode);
                createRoomButton.setEnabled(false);
                joinRoomButton.setEnabled(false);
            } else {
                roomStatus.setText("创建房间失败");
            }
        } catch (IOException e) {
            handleDisconnection();
            e.printStackTrace();
        }
    }

    private void joinRoom(String inviteCode) {
        if (!inviteCode.isEmpty()) {
            out.println("JOIN_ROOM " + inviteCode);

            try {
                String response = in.readLine();
                if ("SUCCESS".equals(response)) {
                    roomStatus.setText("已加入房间：" + inviteCode);
                    joinRoomButton.setEnabled(false);
                    createRoomButton.setEnabled(false);
                } else {
                    roomStatus.setText("加入房间失败");
                }
            } catch (IOException e) {
                handleDisconnection();
                e.printStackTrace();
            }
        } else {
            roomStatus.setText("邀请码不能为空");
        }
    }

    private void handleDisconnection() {
        try {
            if (out != null) out.close();
            if (in != null) in.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        connectionStatus.setText("连接断开");
        roomStatus.setText("未加入房间");
        connectButton.setEnabled(true);
        createRoomButton.setEnabled(false);
        joinRoomButton.setEnabled(false);
    }

    // 添加以下方法以处理游戏的开始和结束
    private void handleGameStart() throws IOException {
        out.println("START_GAME");
        // 等待服务器确认
        String response = in.readLine();
        if ("STARTED".equals(response)) {
            // 游戏已经开始
            // 这里可以添加游戏逻辑，比如开始倒计时
        } else {
            // 游戏开始失败
            // 这里可以添加错误处理逻辑
        }
    }

    private void handleGameEnd(int score) throws IOException {
        // 处理游戏的结束逻辑
        out.println("END_GAME " + score);
        // 等待服务器确认
        String result = in.readLine();
        if ("WIN".equals(result)) {
            // 游戏胜利
            // 这里可以添加胜利逻辑
        } else if ("LOSE".equals(result)) {
            // 游戏失败
            // 这里可以添加失败逻辑
        } else if ("TIE".equals(result)) {
            // 平局
            // 这里可以添加平局逻辑
        } else {
            // 未知结果
            // 这里可以添加未知结果的处理逻辑
        }
    }

    // 添加以下方法以处理游戏逻辑
    private void gameLogic() {
        // 游戏逻辑
        new Thread(() -> {
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
        });
        // 这里可以添加游戏的主循环、得分逻辑等
    }


    // 修改客户端按钮的ActionListener以处理游戏逻辑
    private void handleGameLogic() throws IOException {
        // 处理游戏逻辑
        gameLogic();
        // 检查是否游戏结束
        if (gameOver()) {
            int score = calculateScore();
            handleGameEnd(score);
        } else {
            // 继续游戏逻辑
        }
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
    private boolean gameOver() {
        // 判断游戏是否结束
        // 这里可以添加游戏结束的条件
        return false; // 假设游戏没有结束
    }

    private int calculateScore() throws IOException {
        // 假设游戏规则是：用户猜测一个数字，如果猜对了，得100分；如果猜错了，得0分
        if (isCorrectGuess()) {
            return 100;
        } else {
            return 0;
        }
    }

    private boolean isCorrectGuess() throws IOException {
        // 假设服务器发送了一个随机生成的数字，这里需要解析这个数字
        String serverNumber = in.readLine();
        int serverNumberInt = Integer.parseInt(serverNumber);
        int userGuess = 1;// 获取用户的猜测
        return userGuess == serverNumberInt;
    }
}