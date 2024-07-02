import java.io.*;
import java.net.*;
import java.util.*;

import static java.lang.System.out;

public class GameServer {
    private static final int PORT = 2317;
    private static final int BROADCAST_PORT = 8888;
    private static final String BROADCAST_MESSAGE = "DISCOVER_FIT_SERVER";
    private static final Map<String, GameRoom> gameRooms = new HashMap<>();

    public GameServer() {
        startUdpBroadcastListener(); // 启动UDP广播监听器
        out.println("游戏服务器启动，监听端口：" + PORT);
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                out.println("新的客户端连接：" + clientSocket.getRemoteSocketAddress());

                // 为新连接创建一个新的游戏房间
                String inviteCode = generateInviteCode();
                GameRoom gameRoom = new GameRoom(inviteCode);
                gameRooms.put(inviteCode, gameRoom);

                // 向客户端发送邀请码
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                out.println("您的邀请码是：" + inviteCode);

                // 启动一个新的线程来处理客户端的输入
                new Thread(new ClientHandler(clientSocket, gameRoom)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startUdpBroadcastListener() {
        new Thread(() -> {
            try (DatagramSocket udpSocket = new DatagramSocket(BROADCAST_PORT)) {
                byte[] buffer = new byte[1024];
                while (true) {
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    udpSocket.receive(packet);
                    String message = new String(packet.getData(), 0, packet.getLength()).trim();
                    if (BROADCAST_MESSAGE.equals(message)) {
                        InetAddress clientAddress = packet.getAddress();
                        int clientPort = packet.getPort();
                        String serverIp = InetAddress.getLocalHost().getHostAddress();
                        byte[] response = serverIp.getBytes();
                        DatagramPacket responsePacket = new DatagramPacket(response, response.length, clientAddress, clientPort);
                        udpSocket.send(responsePacket);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private String generateInviteCode() {
        // 这里应该生成一个唯一的邀请码
        // 为了简单起见，我只生成一个随机字符串
        return UUID.randomUUID().toString().substring(0, 6);
    }
}

class GameRoom {
    private final String inviteCode;
    private Socket clientSocket;
    private String player1Score;
    private String player2Score;

    public GameRoom(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public String getPlayer1Score() {
        return player1Score;
    }

    public void setPlayer1Score(String score) {
        player1Score = score;
    }

    public String getPlayer2Score() {
        return player2Score;
    }

    public void setPlayer2Score(String score) {
        player2Score = score;
    }

    public void close() {
        if (clientSocket != null) {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final GameRoom gameRoom;

    public ClientHandler(Socket clientSocket, GameRoom gameRoom) {
        this.clientSocket = clientSocket;
        this.gameRoom = gameRoom;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                // 处理客户端的输入
                out.println("客户端输入：" + inputLine);
                // 这里可以添加游戏逻辑，比如解析客户端的动作，更新游戏状态等

                // 假设客户端发送的格式为 "SCORE:player1,player2"
                String[] parts = inputLine.split(":");
                if (parts.length == 2) {
                    String action = parts[0];
                    String scores = parts[1];
                    String[] scoreParts = scores.split(",");
                    if (scoreParts.length == 2) {
                        String player1Score = scoreParts[0];
                        String player2Score = scoreParts[1];
                        gameRoom.setPlayer1Score(player1Score);
                        gameRoom.setPlayer2Score(player2Score);
                        // 比较得分并返回比赛结果
                        if (player1Score.equals("100") && player2Score.equals("200")) {
                            out.println("You win!");
                        } else if (player1Score.equals("200") && player2Score.equals("100")) {
                            out.println("You lose!");
                        } else {
                            out.println("It's a tie!");
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}