package server;

import server.entity.PlayerServerSide;
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

public class ClientHandler implements Runnable {
    private final PlayerServerSide player;
    private final Socket socket;
    private final BufferedWriter out;
    private final BufferedReader in;
    private final ArrayList<PlayerServerSide> playerList;
    private final ServerCore core;

    public ClientHandler(Socket socket, BufferedReader in, BufferedWriter out, PlayerServerSide player, ServerCore core)  {
        this.player = player;
        this.socket = socket;
        this.core = core;
        this.playerList = core.getPlayerList();
        this.in = in;
        this.out = out;
    }

    @Override
    public void run() {
        setName();
        playerList.add(player);
        try {
            out.write(String.valueOf(ServerCore.mapNumber));
            out.newLine();
            out.flush();
            while (socket.isConnected()) {
                String line = in.readLine();
                if (line.isEmpty()) break;
                updatePlayer(line);
                if (playerList.size() > 1) {
                    sendPlayerData();
                } else {
                    out.write("noPlayers");
                    out.newLine();
                }
                out.flush();
            }
        } catch (Exception e) {
            core.removePlayer(player);
            ServerCore.closeSocket(socket, out, in);
        }
        core.removePlayer(player);
        ServerCore.closeSocket(socket, out, in);
    }

    private void updatePlayer(String line) throws IOException {
        PlayerInputToServer playerInputToServer;
        playerInputToServer = PlayerInputToServer.parseString(line);
        player.updateFromPlayerInput(playerInputToServer);
        out.write(ServerOutputToClient.getFromPlayerData(player).toString());
        out.newLine();
    }

    private void sendPlayerData() throws IOException {
        StringBuilder playerOut = new StringBuilder();
        StringBuilder playerNames = new StringBuilder();
        for (PlayerServerSide player : playerList) {
            playerNames.append(player.getId()).append(ConstantsShared.protocolPlayerVariableSplit)
                    .append(player.getPlayerModel()).append(ConstantsShared.protocolPlayerLineEnd);
            playerOut.append(PlayerUpdateInput.getFromPlayerData(player).getString()).append(ConstantsShared.protocolPlayerLineEnd);
        }
        out.write(playerNames.toString());
        out.newLine();
        out.write(playerOut.toString());
        out.newLine();
    }

    public void setName() {
        try {
            String nameTest;
            boolean test = true;
            do {
                nameTest = in.readLine();

                if (checkNameValidity(nameTest, out)) {
                    if (checkNameAvailability(nameTest, out)) {
                        test = false;
                        out.write("name available");
                        out.newLine();
                        out.flush();
                    }
                }
            } while (test);

            player.setInitData(PlayerInitialData.parseString(in.readLine()));
        } catch (Exception e) {
            ServerCore.closeSocket(socket, out, in);
        }
    }

    private boolean checkNameAvailability(String name, BufferedWriter out) throws Exception {
        for (PlayerServerSide playerServerSide : playerList) {
            if (playerServerSide != player && playerServerSide.getId().equals(name)) {
                out.write("name taken");
                out.newLine();
                out.flush();
                return false;
            }
        }
        return true;
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
}
