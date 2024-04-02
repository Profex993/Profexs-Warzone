package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class ServerCore {
    private final ArrayList<PlayerData> playerList = new ArrayList<>();

    public void runServer(int port) {
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                System.out.println("server running on port: " + port + "\nwrite help for list of available commands");
                while (true) {
                    Socket socket = serverSocket.accept();
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    String nameAvalibilityTest;
                    do {
                        nameAvalibilityTest = in.readLine();
                    } while (checkNameAvailability(out, nameAvalibilityTest));
                    String[] playerInitData = in.readLine().split(" ");         //[0] is name and [1] is player model
                    PlayerData player = new PlayerData(playerInitData[0], playerInitData[1]);
                    playerList.add(player);
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

    private boolean checkNameAvailability(BufferedWriter bufferedWriter, String name) throws Exception {
        for (PlayerData playerData : playerList) {
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

    public ArrayList<PlayerData> getPlayerList() {
        return playerList;
    }
}
