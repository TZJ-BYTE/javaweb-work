import java.io.*;
import java.net.*;
import java.util.*;

public class GameServer {
    private static final int PORT = 2317;
    private static final Map<String, GameRoom> gameRooms = new HashMap<>();

    public GameServer() {
        System.out.println("游戏服务器启动，监听端口：" + PORT);
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("新的客户端连接：" + clientSocket.getRemoteSocketAddress());

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

    private String generateInviteCode() {
        // 这里应该生成一个唯一的邀请码
        // 为了简单起见，我们只是返回一个随机字符串
        return UUID.randomUUID().toString().substring(0, 6);
    }
}

class GameRoom {
    private final String inviteCode;

    public GameRoom(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    // 这里可以添加游戏逻辑和房间状态
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
                System.out.println("客户端输入：" + inputLine);
                // 这里可以添加游戏逻辑，比如解析客户端的动作，更新游戏状态等
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
