package client.clientMain;

import client.entity.MainPlayer;
import client.entity.Player;
import shared.ConstantsShared;
import shared.packets.PlayerInitialData;
import shared.packets.PlayerInputToServer;
import shared.packets.PlayerUpdateInput;
import shared.packets.ServerOutputToClient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ServerCommunication {
    private final Socket socket;
    private final BufferedWriter out;
    private final BufferedReader in;
    private final MainPlayer mainPlayer;
    private final ArrayList<Player> playerList;
    private final MouseHandler mouseHandler;
    private final KeyHandler keyHandler;
    private final int mapNumber;


    public ServerCommunication(MainPlayer mainPlayer, String playerModel, ArrayList<Player> playerList, Socket socket, BufferedReader in,
                               BufferedWriter out, KeyHandler keyHandler, MouseHandler mouseHandler) {
        this.mainPlayer = mainPlayer;
        this.playerList = playerList;
        this.out = out;
        this.socket = socket;
        this.in = in;
        this.keyHandler = keyHandler;
        this.mouseHandler = mouseHandler;
        startCommunication(playerModel);

        try {
            this.mapNumber = Integer.parseInt(in.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void startCommunication(String playerModel) {
        try {
            PlayerInitialData data = new PlayerInitialData(mainPlayer.getName(), playerModel);
            out.write(data.toString());
            out.newLine();
            out.flush();
        } catch (IOException e) {
            ClientMain.closeSocket(socket, in, out);
            throw new RuntimeException(e);
        }
    }

    public void update() {
        try {
            out.write(PlayerInputToServer.getFromPlayerInput(keyHandler, mouseHandler, mainPlayer.getScreenX(), mainPlayer.getScreenY()).toString());
            out.newLine();
            out.flush();

            String inputLine = in.readLine();
            mainPlayer.updateFromServerInput(ServerOutputToClient.parseFromString(inputLine));
            inputLine = in.readLine();
            if (inputLine.equals("noPlayers")) {
                if (!playerList.isEmpty()) {
                    playerList.clear();
                }
            } else {
                updatePlayerList(inputLine);
                inputLine = in.readLine();
                updatePlayers(inputLine);
            }
        } catch (IOException e) {
            ClientMain.closeSocket(socket, in, out);
            throw new RuntimeException(e);
        }
    }

    private void updatePlayers(String inputLine) throws IOException {
        String[] playerInputs = inputLine.split(ConstantsShared.protocolPlayerLineEnd);
        for (String input : playerInputs) {
            PlayerUpdateInput playerUpdateInput = PlayerUpdateInput.parseFromString(input);
            for (Player player : playerList) {
                if (player.getName().equals(playerUpdateInput.id())) {
                    player.updateFromInputData(playerUpdateInput);
                }
            }
        }
    }

    private void updatePlayerList(String inputLine) throws IOException {
        String[] playerInputLines = inputLine.split(ConstantsShared.protocolPlayerLineEnd);
        if (playerInputLines.length == playerList.size()) return;

        if (playerInputLines.length > playerList.size()) {
            addPlayer(playerInputLines);
        } else {
            removePlayer(playerInputLines);
        }
    }

    private void removePlayer(String[] playerInputLines) {
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

    private void addPlayer(String[] playerInputLines) throws IOException {
        for (String playerLine : playerInputLines) {
            PlayerInitialData data = PlayerInitialData.parseString(playerLine);
            if (!data.name().equals(mainPlayer.getName()) && isNewPlayer(data)) {
                Player newPlayer = new Player(mainPlayer, data.name(), data.playerModel());
                playerList.add(newPlayer);
            }
        }
    }

    private boolean isNewPlayer(PlayerInitialData data) {
        return playerList.stream()
                .noneMatch(player -> player.getName().equals(data.name()));
    }

    public int getMapNumber() {
        return mapNumber;
    }
}
