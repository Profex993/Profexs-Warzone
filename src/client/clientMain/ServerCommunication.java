package client.clientMain;

import client.entity.Player;
import client.entity.PlayerMain;
import dataFormats.PlayerInput;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ServerCommunication {
    private final Socket socket;
    private final BufferedWriter out;
    private final BufferedReader in;
    private final PlayerMain playerMain;
    private final ArrayList<Player> playerList;


    public ServerCommunication(PlayerMain playerMain, ArrayList<Player> playerList) {
        this.playerMain = playerMain;
        this.playerList = playerList;
        try {
            socket = new Socket("localhost", 8080);
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            out.write(playerMain.getName());
            out.newLine();
            out.flush();
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void update() {
        try {
            out.write(playerMain.getX() + " " + playerMain.getY());
            out.newLine();
            out.flush();
            String line = in.readLine();
            if (!line.isEmpty()) {
                String[] playerNames = line.split(" ");
                if (playerNames.length != playerList.size()) {
                    for (int i = playerList.size(); i < playerNames.length; i++) {
                        playerList.add(new Player(playerNames[i]));
                    }
                }
            }
            line = in.readLine();
            if (!line.isEmpty()) {
                String[] playerInputs = line.split(";");
                for (int i = 0; i < playerList.size(); i++) {
                    PlayerInput playerInput = PlayerInput.parseFromString(playerInputs[i]);
                    playerList.get(i).updateFromInputData(playerInput);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String inputStreamToString(BufferedReader inFromSocket) {
        try {
            String line = inFromSocket.readLine();
            StringBuilder out = new StringBuilder();
            while (line != null) {
                out.append(line).append("\n");
                line = inFromSocket.readLine();
            }
            return out.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
