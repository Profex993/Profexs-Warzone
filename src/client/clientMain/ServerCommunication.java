package client.clientMain;

import client.entity.Player;
import client.entity.PlayerMain;
import shared.Constants;
import shared.PlayerInput;
import shared.PlayerInputToServer;
import shared.ServerOutputToClient;

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
    private final MouseHandler mouseHandler;
    private final KeyHandler keyHandler;


    public ServerCommunication(PlayerMain playerMain, String playerModel, ArrayList<Player> playerList,
                               Socket socket, BufferedReader in, BufferedWriter out, KeyHandler keyHandler, MouseHandler mouseHandler) {
        this.playerMain = playerMain;
        this.playerList = playerList;
        this.out = out;
        this.socket = socket;
        this.in = in;
        this.keyHandler = keyHandler;
        this.mouseHandler = mouseHandler;
        try {
            out.write(playerMain.getName() + Constants.protocolPlayerVariableSplit + playerModel);
            out.newLine();
            out.flush();
        } catch (IOException e) {
            ClientMain.closeSocket(socket, in, out);
            throw new RuntimeException(e);
        }
    }

    public void update() {
        try {
            out.write(PlayerInputToServer.getFromPlayerInput(keyHandler, mouseHandler, playerMain.getScreenX(), playerMain.getScreenY()).toString());
            out.newLine();
            out.flush();
            String inputLine = in.readLine();
            playerMain.updateFromServerInput(ServerOutputToClient.parseFromString(inputLine));
            inputLine = in.readLine();
            if (!inputLine.equals("noPlayers")) {
                String[] playerInputLines = inputLine.split(Constants.protocolPlayerLineEnd);
                if (playerInputLines.length != playerList.size()) {
                    if (playerInputLines.length > playerList.size()) {
                        for (String playerLine : playerInputLines) {
                            String[] playerData = playerLine.split(Constants.protocolPlayerVariableSplit);        //[0] is player name and [1] is player model
                            if (!playerData[0].equals(playerMain.getName())) {
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
                            for (String name : playerInputLines) {
                                if (name.equals(player.getName())) {
                                    newPlayerList.add(player);
                                    break;
                                }
                            }
                        }
                        playerList.removeIf(player -> !newPlayerList.contains(player));
                    }
                }
                inputLine = in.readLine();
                String[] playerInputs = inputLine.split(Constants.protocolPlayerLineEnd);
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
