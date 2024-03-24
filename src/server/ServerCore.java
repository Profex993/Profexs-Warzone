package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerCore {
    private final ArrayList<PlayerData> players = new ArrayList<>();

    public void runServer(int port) {
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                System.out.println("server running on port: " + port);
                while (true) {
                    Socket socket = serverSocket.accept();
                    BufferedReader inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PlayerData player = new PlayerData(inputStream.readLine());
                    players.add(player);
                    Thread thread = new Thread(new ClientHandler(socket, player, players));
                    thread.start();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public ArrayList<PlayerData> getPlayers() {
        return players;
    }
}
