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


    public ServerCommunication(PlayerMain playerMain, String playerModel, ArrayList<Player> playerList, Socket socket, BufferedReader in, BufferedWriter out) {
        this.playerMain = playerMain;
        this.playerList = playerList;
        this.out = out;
        this.socket = socket;
        this.in = in;
        try {
            out.write(playerMain.getName() + " " + playerModel);
            out.newLine();
            out.flush();
        } catch (IOException e) {
            ClientMain.closeSocket(socket, in, out);
            throw new RuntimeException(e);
        }
    }

    public void update() {
        try {
            out.write(playerMain.outputToServer());
            out.newLine();
            out.flush();
            String line = in.readLine();
            if (!line.equals("noPlayers")) {
                String[] playerLines = line.split(";");
                if (playerLines.length != playerList.size()) {
                    if (playerLines.length > playerList.size()) {
                        for (String playerLine : playerLines) {
                            String[] playerData = playerLine.split(" ");
                            if (!playerLine.equals(playerMain.getName())) {
                                boolean nameExists = false;
                                for (Player player : playerList) {
                                    if (player.getName().equals(playerData[0])) {
                                        nameExists = true;
                                        break;
                                    }
                                }
                                if (!nameExists) {
                                    Player newPlayer = new Player(playerMain, playerData[0], playerData[1]);
                                    playerList.add(newPlayer);
                                }
                            }
                        }
                    } else {
                        ArrayList<Player> newPlayerList = new ArrayList<>();
                        for (Player player : playerList) {
                            for (String name : playerLines) {
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
                for (String input : playerInputs) {
                    PlayerInput playerInput = PlayerInput.parseFromString(input);
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
