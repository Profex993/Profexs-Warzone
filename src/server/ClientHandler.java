package server;

import server.entity.PlayerServerSide;
import server.enums.ServerMatchState;
import shared.ConstantsShared;
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
    private final ArrayList<PlayerServerSide> addPlayerRequestList = new ArrayList<>();
    private final ArrayList<PlayerServerSide> removePlayerRequestList = new ArrayList<>();
    private final ArrayList<Object> addObjectRequestList = new ArrayList<>(), removeObjectRequestList = new ArrayList<>();
    private boolean changeMapRequestTrigger = true, endMatchRequestTrigger, startMatchRequestTrigger;
    private final ArrayList<String> packetStringList = new ArrayList<>();

    public ClientHandler(Socket socket, BufferedReader in, BufferedWriter out, PlayerServerSide player, ServerCore core,
                         ArrayList<PlayerServerSide> playerList, ServerMatchState matchState) {
        this.player = player;
        this.socket = socket;
        this.core = core;
        this.playerList = playerList;
        this.in = in;
        this.out = out;

        if (matchState == ServerMatchState.MATCH_OVER) {
            triggerEndOfMatch();
        } else if (matchState == ServerMatchState.MATCH) {
            triggerStartOfMatch();
        }

        addPlayerRequestList.addAll(playerList);
    }

    @Override
    public void run() {
        setName();
        core.addPlayer(player, this);
        try {
            while (socket.isConnected()) {
                String inputLine = in.readLine();
                if (inputLine.isEmpty()) break;
                updatePlayer(inputLine);

                makePacketList();
                out.write(String.valueOf(packetStringList.size()));
                out.newLine();
                packetStringList.forEach(e -> {
                    try {
                        out.write(e);
                        out.newLine();
                    } catch (IOException ex) {
                        throw new RuntimeException(e);
                    }
                });
                if (!packetStringList.isEmpty()) packetStringList.clear();

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

    private void makePacketList() {
        if (changeMapRequestTrigger) {
            Packet_ChangeMap packetChangeMap = new Packet_ChangeMap(core.getMAP_NUMBER());
            packetStringList.add(packetChangeMap.toString());
            changeMapRequestTrigger = false;
        }

        if (endMatchRequestTrigger) {
            Packet_EndMatch packet = new Packet_EndMatch(ConstantsShared.MATCH_OVER_TIME);
            packetStringList.add(packet.toString());
            endMatchRequestTrigger = false;
        }

        if (startMatchRequestTrigger) {
            Packet_StartMatch packet = new Packet_StartMatch(core.getMATCH_TIME());
            packetStringList.add(packet.toString());
            startMatchRequestTrigger = false;
        }

        if (!addPlayerRequestList.isEmpty()) {
            for (PlayerServerSide player : addPlayerRequestList) {
                if (this.player != player) {
                    Packet_AddPlayer packet = new Packet_AddPlayer(player.getId(), player.getPlayerModel());
                    packetStringList.add(packet.toString());
                }
            }
            addPlayerRequestList.clear();
        }

        if (!removePlayerRequestList.isEmpty()) {
            for (PlayerServerSide player : removePlayerRequestList) {
                Packet_RemovePlayer packet = Packet_RemovePlayer.getFromPlayer(player);
                packetStringList.add(packet.toString());
            }
            removePlayerRequestList.clear();
        }

        if (!playerList.isEmpty()) {
            for (PlayerServerSide player : playerList) {
                if (this.player != player) {
                    Packet_PlayerUpdateInput packet = Packet_PlayerUpdateInput.getFromPlayerData(player);
                    packetStringList.add(packet.toString());
                }
            }
        }

        if (!removeObjectRequestList.isEmpty()) {
            for (Object Object : removeObjectRequestList) {
                Packet_RemoveObject packet = Packet_RemoveObject.getFromObject(Object);
                packetStringList.add(packet.toString());
            }
            removeObjectRequestList.clear();
        }

        if (!addObjectRequestList.isEmpty()) {
            for (Object object : addObjectRequestList) {
                Packet_AddObject packet = Packet_AddObject.getFromObject(object);
                packetStringList.add(packet.toString());
            }
            addObjectRequestList.clear();
        }
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

    public void triggerEndOfMatch() {
        endMatchRequestTrigger = true;
    }

    public void triggerStartOfMatch() {
        startMatchRequestTrigger = true;
    }

    public ArrayList<PlayerServerSide> getAddPlayerRequestList() {
        return addPlayerRequestList;
    }

    public ArrayList<PlayerServerSide> getRemovePlayerRequestList() {
        return removePlayerRequestList;
    }

    public ArrayList<Object> getAddObjectRequestList() {
        return addObjectRequestList;
    }

    public ArrayList<Object> getRemoveObjectRequestList() {
        return removeObjectRequestList;
    }

}
