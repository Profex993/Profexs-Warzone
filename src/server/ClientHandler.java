package server;

import server.entity.PlayerServerSide;
import server.enums.ServerMatchState;
import shared.ConstantsShared;
import shared.object.objectClasses.MapObject;
import shared.packets.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

/**
 * class for clientHandling
 */
public class ClientHandler implements Runnable {
    private final PlayerServerSide player;
    private final Socket socket;
    private final BufferedWriter out;
    private final BufferedReader in;
    private final ServerCore core;
    private final ArrayList<PlayerServerSide> playerList;
    private final ArrayList<ClientHandler> clientHandlers;
    private final ArrayList<PlayerServerSide> playersToAddList = new ArrayList<>(), playerToRemoveList = new ArrayList<>();
    private final ArrayList<MapObject> mapObjectsToAddList = new ArrayList<>(), mapObjectsToRemoveList = new ArrayList<>();
    private final ArrayList<String> packetStringList = new ArrayList<>();
    private boolean changeMapRequestTrigger = true, endMatchRequestTrigger, startMatchRequestTrigger;

    /**
     *
     * @param socket client Socket
     * @param in client InputStream to read input
     * @param out client OutputStream to send output
     * @param player PlayerServerSide to update clients player
     * @param core ServerCore for requesting adding objects and players
     * @param playerList Arraylist of PlayerServerSide to add players connected to server
     * @param matchState ServerMatchState current match state to end or start match
     * @param itemSpawnLocation Arraylist of item spawn locations to add MapObjects already on map
     * @param clientHandlers Arraylist of clientHandler to remove this if client disconnects
     */
    public ClientHandler(Socket socket, BufferedReader in, BufferedWriter out, PlayerServerSide player, ServerCore core,
                         ArrayList<PlayerServerSide> playerList, ServerMatchState matchState,
                         ArrayList<ItemSpawnLocation> itemSpawnLocation, ArrayList<ClientHandler> clientHandlers) {
        this.player = player;
        this.socket = socket;
        this.core = core;
        this.playerList = playerList;
        this.in = in;
        this.out = out;
        this.clientHandlers = clientHandlers;

        //set match state for client
        if (matchState == ServerMatchState.MATCH_OVER) {
            triggerEndOfMatch();
        } else if (matchState == ServerMatchState.MATCH) {
            triggerStartOfMatch();
        }

        //add items from item spawn location
        itemSpawnLocation.forEach(e -> {
            if (e.getObject() != null) {
                mapObjectsToAddList.add(e.getObject());
            }
        });

        //add players
        playerList.forEach( e -> {
            Packet_AddPlayer packetAddPlayer = new Packet_AddPlayer(e.getName(), e.getPlayerModel(), false);
            packetStringList.add(packetAddPlayer.toString());
        });
    }

    /**
     * handle client
     */
    @Override
    public void run() {
        setName();
        core.addPlayer(player, this);
        try {
            while (socket.isConnected()) {
                String inputLine = in.readLine();
                if (inputLine == null) break; //no client input

                updatePlayer(inputLine);

                makePacketList();
                //send how many packets has the client expect
                out.write(String.valueOf(packetStringList.size()));
                out.newLine();
                // send out packets
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
        } catch (SocketException ignored) {
            // client has disconnected
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        core.removePlayer(player);
        endConnection();
    }

    /**
     * update player from input String and send update back to client
     * @param line Packet_ClientInputToServer.toString() from client
     * @throws IOException if line is corrupted
     */
    private void updatePlayer(String line) throws IOException {
        player.updateFromPlayerInput(Packet_PlayerInputToServer.parseString(line));
        out.write(Packet_ServerOutputToClient.getFromPlayerData(player).toString());
        out.newLine();
    }

    /**
     * create list of packets Strings
     */
    private void makePacketList() {
        if (changeMapRequestTrigger) {
            Packet_ChangeMap packetChangeMap = new Packet_ChangeMap(core.getMAP_NUMBER());
            packetStringList.add(packetChangeMap.toString());
            changeMapRequestTrigger = false;
        }

        if (endMatchRequestTrigger) {
            Packet_EndMatch packet = new Packet_EndMatch(ConstantsServer.MATCH_OVER_TIME);
            packetStringList.add(packet.toString());
            endMatchRequestTrigger = false;
        }

        if (startMatchRequestTrigger) {
            Packet_StartMatch packet = new Packet_StartMatch(core.getMATCH_TIME());
            packetStringList.add(packet.toString());
            startMatchRequestTrigger = false;
        }

        if (!playersToAddList.isEmpty()) {
            for (PlayerServerSide player : playersToAddList) {
                if (this.player != player) {
                    Packet_AddPlayer packet = new Packet_AddPlayer(player.getName(), player.getPlayerModel(), true);
                    packetStringList.add(packet.toString());
                }
            }
            playersToAddList.clear();
        }

        if (!playerToRemoveList.isEmpty()) {
            for (PlayerServerSide player : playerToRemoveList) {
                Packet_RemovePlayer packet = Packet_RemovePlayer.getFromPlayer(player);
                packetStringList.add(packet.toString());
            }
            playerToRemoveList.clear();
        }

        if (!playerList.isEmpty()) {
            for (PlayerServerSide player : playerList) {
                if (this.player != player) {
                    Packet_PlayerUpdateInput packet = Packet_PlayerUpdateInput.getFromPlayerData(player);
                    packetStringList.add(packet.toString());
                }
            }
        }

        if (!mapObjectsToRemoveList.isEmpty()) {
            for (int i = 0; i < mapObjectsToRemoveList.size(); i++) {
                Packet_RemoveObject packet = Packet_RemoveObject.getFromObject(mapObjectsToRemoveList.get(i));
                packetStringList.add(packet.toString());
            }
            mapObjectsToRemoveList.clear();
        }

        if (!mapObjectsToAddList.isEmpty()) {
            for (int i = 0; i < mapObjectsToAddList.size(); i++) {
                Packet_AddObject packet = Packet_AddObject.getFromObject(mapObjectsToAddList.get(i));
                packetStringList.add(packet.toString());
            }
            mapObjectsToAddList.clear();
        }
    }

    /**
     * set clients name and check its availability and validity
     */
    public void setName() {
        try {
            String nameTest;
            boolean test = true;
            do {
                nameTest = in.readLine();

                if (checkNameValidity(nameTest, out)) {
                    if (checkNameAvailability(nameTest, out)) {
                        test = false;
                        out.write(ConstantsShared.NAME_AVAILABLE);
                        out.newLine();
                        out.flush();
                    }
                }
            } while (test);

            //set players name and model
            player.setInitialData(Packet_AddPlayerToServer.parseString(in.readLine()));
        } catch (Exception e) {
            endConnection();
        }
    }

    /**
     * check if name is available
     * @param name String of name
     * @param out OutputStream to send response to client
     * @return returns true if name is available
     * @throws Exception output stream error
     */
    private boolean checkNameAvailability(String name, BufferedWriter out) throws Exception {
        for (PlayerServerSide playerServerSide : playerList) {
            if (playerServerSide != player && playerServerSide.getName().equals(name)) {
                out.write(ConstantsShared.NAME_TAKEN);
                out.newLine();
                out.flush();
                return false;
            }
        }
        return true;
    }

    /**
     * checks if name is valid by regex
     * @param name String of name
     * @param out OutputStream to send response back to client
     * @return returns true if name is valid
     * @throws Exception output stream error
     */
    private boolean checkNameValidity(String name, BufferedWriter out) throws Exception {
        if (name.matches("[a-zA-Z\\d_]{1,15}")) {
            return true;
        } else {
            out.write(ConstantsShared.INVALID_NAME);
            out.newLine();
            out.flush();
            return false;
        }
    }

    /**
     * end connection and remove client
     */
    private void endConnection() {
        clientHandlers.remove(this);
        try {
            socket.close();
            in.close();
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void triggerEndOfMatch() {
        endMatchRequestTrigger = true;
    }

    public void triggerStartOfMatch() {
        startMatchRequestTrigger = true;
    }

    public ArrayList<PlayerServerSide> getPlayersToAddList() {
        return playersToAddList;
    }

    public ArrayList<PlayerServerSide> getPlayerToRemoveList() {
        return playerToRemoveList;
    }

    public ArrayList<MapObject> getAddObjectRequestList() {
        return mapObjectsToAddList;
    }

    public ArrayList<MapObject> getRemoveObjectRequestList() {
        return mapObjectsToRemoveList;
    }
}
