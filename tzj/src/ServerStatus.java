import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerStatus {
    private JLabel connectionStatus;
    private JLabel roomStatus;
    private JButton connectButton;
    private JButton joinRoomButton;

    public ServerStatus() {
        JFrame frame = new JFrame("服务器状态");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLayout(new FlowLayout());

        connectionStatus = new JLabel("未连接");
        roomStatus = new JLabel("未加入房间");

        connectButton = new JButton("连接服务器");
        joinRoomButton = new JButton("加入房间");
        joinRoomButton.setEnabled(false); // 默认禁用，直到连接到服务器

        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connectToServer();
            }
        });

        joinRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                joinRoom();
            }
        });

        frame.add(connectionStatus);
        frame.add(connectButton);
        frame.add(roomStatus);
        frame.add(joinRoomButton);

        frame.setVisible(true);
    }

    private void connectToServer() {
        // 这里替换为实际的服务器IP和端口
        String serverIP = "192.168.1.100"; // 替换为您的服务器IP地址
        int serverPort = 2317;

        try (Socket socket = new Socket(serverIP, serverPort)) {
            connectionStatus.setText("已连接到服务器");
            connectButton.setEnabled(false); // 连接后禁用按钮
            joinRoomButton.setEnabled(true); // 启用加入房间按钮
        } catch (IOException e) {
            connectionStatus.setText("连接失败");
            e.printStackTrace();
        }
    }

    private void joinRoom() {
        // 这里替换为实际的房间加入逻辑
        // 例如，可以提示用户输入邀请码，然后尝试加入房间
        roomStatus.setText("已加入房间");
        joinRoomButton.setEnabled(false); // 加入房间后禁用按钮
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ServerStatus();
            }
        });
    }
}
