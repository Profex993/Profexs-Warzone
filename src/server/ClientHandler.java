package server;

import server.entity.PlayerServerSide;
import shared.object.objectClasses.Object;
import shared.packets.*;

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
    private final ArrayList<PlayerServerSide> addPlayerList = new ArrayList<>();
    private final ArrayList<String> removePlayer = new ArrayList<>();
    private final ArrayList<Object> addObject = new ArrayList<>();
    private final ArrayList<Integer> removeObject = new ArrayList<>();
    private boolean changeMap = true;

    public ClientHandler(Socket socket, BufferedReader in, BufferedWriter out, PlayerServerSide player, ServerCore core,
                         ArrayList<PlayerServerSide> playerList) {
        this.player = player;
        this.socket = socket;
        this.core = core;
        this.playerList = playerList;
        this.in = in;
        this.out = out;

        addPlayerList.addAll(playerList);
    }

    @Override
    public void run() {
        setName();
        core.addPlayer(player, this);
        try {
            while (socket.isConnected()) {
                String inputLine = in.readLine();
                if (inputLine.isEmpty()) {
                    break;
                }
                updatePlayer(inputLine);

                int numberOfPackets = addPlayerList.size() + removePlayer.size() + removeObject.size()
                        + playerList.size() + addObject.size();


                if (!playerList.isEmpty()) numberOfPackets--;
                if (changeMap) numberOfPackets++;
                out.write(String.valueOf(numberOfPackets));
                out.newLine();

                if (changeMap) {
                    Packet_ChangeMap packetChangeMap = new Packet_ChangeMap(ServerCore.mapNumber);
                    out.write(packetChangeMap.toString());
                    out.newLine();
                    changeMap = false;
                }

                if (!addPlayerList.isEmpty()) {
                    for (PlayerServerSide player : addPlayerList) {
                        if (this.player != player) {
                            Packet_AddPlayer packet = new Packet_AddPlayer(player.getId(), player.getPlayerModel());
                            out.write(packet.toString());
                            out.newLine();
                        }
                    }
                    addPlayerList.clear();
                }

                if (!removePlayer.isEmpty()) {
                    for (String player : removePlayer) {
                        Packet_RemovePlayer packet = new Packet_RemovePlayer(player);
                        out.write(packet.toString());
                        out.newLine();
                    }
                    removePlayer.clear();
                }

                if (!playerList.isEmpty()) {
                    for (PlayerServerSide player : playerList) {
                        if (this.player != player) {
                            Packet_PlayerUpdateInput packet = Packet_PlayerUpdateInput.getFromPlayerData(player);
                            out.write(packet.toString());
                            out.newLine();
                        }
                    }
                }

                if (!removeObject.isEmpty()) {
                    for (Integer hash : removeObject) {
                        Packet_RemoveObject packet = Packet_RemoveObject.getFromObject(hash);
                        out.write(packet.toString());
                        out.newLine();
                    }
                    removeObject.clear();
                }

                if (!addObject.isEmpty()) {
                    for (Object object : addObject) {
                        Packet_AddObject packet = Packet_AddObject.getFromObject(object);
                        out.write(packet.toString());
                        out.newLine();
                    }
                    addObject.clear();
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
        player.updateFromPlayerInput(Packet_PlayerInputToServer.parseString(line));
        out.write(Packet_ServerOutputToClient.getFromPlayerData(player).toString());
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

            player.setInitData(Packet_AddPlayerToServer.parseString(in.readLine()));
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

    public ArrayList<PlayerServerSide> getAddPlayerList() {
        return addPlayerList;
    }

    public ArrayList<String> getRemovePlayer() {
        return removePlayer;
    }

    public ArrayList<Object> getAddObject() {
        return addObject;
    }

    public ArrayList<Integer> getRemoveObject() {
        return removeObject;
    }

}
