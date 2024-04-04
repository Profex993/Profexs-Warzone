package server;

import shared.Constants;
import shared.PlayerInput;
import shared.PlayerInputToServer;
import shared.ServerOutputToClient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
    private final PlayerServerSide player;
    private final Socket socket;
    private final BufferedWriter out;
    private final BufferedReader in;
    private final ArrayList<PlayerServerSide> playerList;

    public ClientHandler(Socket socket, BufferedReader in, BufferedWriter out, PlayerServerSide player, ArrayList<PlayerServerSide> players) {
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
                PlayerInputToServer playerInputToServer;
                if (!line.isEmpty()) {
                    playerInputToServer = PlayerInputToServer.parseString(line);
                } else {
                    throw new Exception("player disconnected");
                }
                player.updateFromPlayerInput(playerInputToServer);
                out.write(ServerOutputToClient.getFromPlayerData(player).toString());
                out.newLine();
                if (playerList.size() > 1) {
                    StringBuilder playerOut = new StringBuilder();
                    StringBuilder playerNames = new StringBuilder();
                    for (PlayerServerSide player : playerList) {
                        playerNames.append(player.getId()).append(Constants.protocolPlayerVariableSplit)
                                .append(player.getPlayerModel()).append(Constants.protocolPlayerLineEnd);
                        playerOut.append(PlayerInput.getFromPlayerData(player).getString()).append(Constants.protocolPlayerLineEnd);
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
