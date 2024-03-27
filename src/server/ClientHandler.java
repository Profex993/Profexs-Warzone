package server;

import shared.PlayerInput;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
    private final PlayerData player;
    private final Socket socket;
    private final BufferedWriter out;
    private final BufferedReader in;
    private final ArrayList<PlayerData> playerList;

    public ClientHandler(Socket socket, BufferedReader in, BufferedWriter out, PlayerData player, ArrayList<PlayerData> players) {
        this.player = player;
        this.socket = socket;
        this.playerList = players;
        this.in = in;
        this.out = out;
    }

    @Override
    public void run() {
        while (socket.isConnected()) {
            try {
                String line = in.readLine();
                PlayerInput playerInput;
                if (!line.isEmpty()) {
                    playerInput = PlayerInput.parseFromString(line);
                } else {
                    throw new Exception("player disconnected");
                }
                player.setX(playerInput.x());
                player.setY(playerInput.y());
                if (playerList.size() > 1) {
                    StringBuilder playerOut = new StringBuilder();
                    StringBuilder playerNames = new StringBuilder();
                    for (PlayerData player : playerList) {
                        playerNames.append(player.getId()).append(" ");
                        playerOut.append(player.getX()).append(" ").append(player.getY()).append(" ")
                                .append(player.getId()).append(";");
                    }
                    out.write(playerNames.toString());
                    out.newLine();
                    out.write(playerOut.toString());
                    out.newLine();
                } else {
                    out.write("noPlayers");
                    out.newLine();
                }
                out.flush();
            } catch (Exception e) {
                playerList.remove(player);
                ServerCore.closeSocket(socket, out, in);
            }
        }
        playerList.remove(player);
        ServerCore.closeSocket(socket, out, in);
    }
}