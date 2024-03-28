package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class ServerCore {
    private final ArrayList<PlayerData> players = new ArrayList<>();

    public void runServer(int port) {
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                System.out.println("server running on port: " + port + "\nwrite help for list of available commands");
                while (true) {
                    Socket socket = serverSocket.accept();
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    String name;
                    do {
                        name = in.readLine();
                    } while (checkNameAvailability(out, name));
                    PlayerData player = new PlayerData(in.readLine());
                    players.add(player);
                    Thread thread = new Thread(new ClientHandler(socket,in, out, player, players));
                    thread.start();
                }
            } catch (SocketException ignored) {
                //goofy ahh exception
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    private boolean checkNameAvailability(BufferedWriter bufferedWriter, String name) throws Exception {
        for (PlayerData playerData : players) {
            if (playerData.getId().equals(name)) {
                bufferedWriter.write("nameTaken");
                bufferedWriter.newLine();
                bufferedWriter.flush();
                return true;
            }
        }
        bufferedWriter.write("nameAvailable");
        bufferedWriter.newLine();
        bufferedWriter.flush();
        return false;
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

    public ArrayList<PlayerData> getPlayers() {
        return players;
    }
}
