package server;

import server.entity.PlayerServerSide;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class ServerCore {
    private final ArrayList<PlayerServerSide> playerList = new ArrayList<>();
    private final ServerUpdateManager serverUpdateManager = new ServerUpdateManager(playerList);
    public final static int mapNum = 0; // this variable will be used for selecting map later

    public ServerCore() {
        serverUpdateManager.startThread();
    }

    public void runServer(int port) {
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                System.out.println("server running on port: " + port + "\nwrite help for list of available commands");
                while (true) {
                    Socket socket = serverSocket.accept();
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    PlayerServerSide player = new PlayerServerSide(serverUpdateManager);
                    Thread thread = new Thread(new ClientHandler(socket, in, out, player, playerList));
                    thread.start();
                }
            } catch (SocketException ignored) {
                //goofy ahh exception
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public static void closeSocket(Socket socket, BufferedWriter out, BufferedReader in) {
        try {
            socket.close();
            in.close();
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<PlayerServerSide> getPlayerList() {
        return playerList;
    }
}
