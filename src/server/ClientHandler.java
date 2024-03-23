package server;

import dataFormats.PlayerInput;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
    private final PlayerData player;
    private final Socket socket;
    private final BufferedWriter out;
    private final BufferedReader in;
    private final ArrayList<PlayerData> players;

    public ClientHandler(Socket socket, PlayerData player, ArrayList<PlayerData> players) {
        try {
            this.player = player;
            this.socket = socket;
            this.players = players;
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        while (socket.isConnected()) {
            try {
                PlayerInput playerInput = PlayerInput.parseFromString(in.readLine());
                player.setX(playerInput.x());
                player.setY(playerInput.y());
                StringBuilder playerOut = new StringBuilder();
                StringBuilder playerNames = new StringBuilder();
                for (PlayerData player : players) {
                    playerNames.append(player.getId()).append(" ");
                    playerOut.append(player.getX()).append(" ").append(player.getY()).append(" ").append(player.getId()).append(";");
                }
                out.write(playerNames.toString());
                out.newLine();
                out.write(playerOut.toString());
                out.newLine();
                out.flush();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
