package server;

import shared.Constants;

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
                    String nameTest;
                    boolean test = true;
                    do {
                        nameTest = in.readLine();

                        if (checkNameValidity(nameTest, out)) {
                            if (!checkNameAvailability(out, nameTest)) {
                                test = false;
                                out.write("name available");
                                out.newLine();
                                out.flush();
                            }
                        }
                    } while (test);
                    String[] playerInitData = in.readLine().split(Constants.protocolPlayerVariableSplit);  //[0] is name and [1] is player model
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

    private boolean checkNameAvailability(BufferedWriter out, String name) throws Exception {
        for (PlayerData playerData : playerList) {
            if (playerData.getId().equals(name)) {
                out.write("name taken");
                out.newLine();
                out.flush();
                return true;
            }
        }
        return false;
    }

    private boolean checkNameValidity(String name, BufferedWriter out) throws Exception {
        if (name.matches("[a-zA-Z\\d_]{1,15}")) {
            return true;
        } else {
            out.write("invalid name");
            out.newLine();
            out.flush();
            return false;
        }
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
