package client.clientMain;

import client.entity.Player;
import client.entity.PlayerMain;
import shared.PlayerInput;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ServerCommunication {
    private final Socket socket;
    private final BufferedWriter out;
    private final BufferedReader in;
    private final PlayerMain playerMain;
    private final ArrayList<Player> playerList;


    public ServerCommunication(PlayerMain playerMain, ArrayList<Player> playerList, Socket socket, BufferedReader in, BufferedWriter out) {
        this.playerMain = playerMain;
        this.playerList = playerList;
        this.out = out;
        this.socket = socket;
        this.in = in;
        try {
            out.write(playerMain.getName());
            out.newLine();
            out.flush();
        } catch (IOException e) {
            ClientMain.closeSocket(socket, in, out);
            throw new RuntimeException(e);
        }
    }

    public void update() {
        try {
            out.write(playerMain.getX() + " " + playerMain.getY() + " " + playerMain.getName());
            out.newLine();
            out.flush();
            String line = in.readLine();
            if (!line.equals("noPlayers")) {
                String[] playerNames = line.split(" ");
                if (playerNames.length != playerList.size()) {
                    if (playerNames.length > playerList.size()) {
                        for (int i = playerList.size(); i < playerNames.length; i++) {
                            playerList.add(new Player(playerNames[i]));
                        }
                    } else {
                        ArrayList<Player> newPlayerList = new ArrayList<>();
                        for (Player player : playerList) {
                            for (String name : playerNames) {
                                if (name.equals(player.getName())) {
                                    newPlayerList.add(player);
                                    break;
                                }
                            }
                        }
                        playerList.removeIf(player -> !newPlayerList.contains(player));
                    }
                }
                line = in.readLine();
                String[] playerInputs = line.split(";");
                for (int i = 0; i < playerList.size(); i++) {
                    PlayerInput playerInput = PlayerInput.parseFromString(playerInputs[i]);
                    for (Player player : playerList) {
                        if (player.getName().equals(playerInput.id())) {
                            player.updateFromInputData(playerInput);
                        }
                    }
                }
            } else {
                if (!playerList.isEmpty()) {
                    playerList.clear();
                }
            }
        } catch (IOException e) {
            ClientMain.closeSocket(socket, in, out);
            throw new RuntimeException(e);
        }
    }
}
