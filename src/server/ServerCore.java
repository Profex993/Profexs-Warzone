package server;

import server.entity.PlayerServerSide;
import server.enums.ServerMatchState;
import shared.MapGenerator;
import shared.object.objectClasses.MapObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

/**
 * core class of server
 */
public class ServerCore {
    private final int MAP_NUMBER = ConstantsServer.MAPS[0]; // this variable will be used for selecting map later
    private int MATCH_TIME = ConstantsServer.MATCH_TIME;
    private ServerMatchState matchState = ServerMatchState.MATCH;
    private final Random random = new Random();
    private final ArrayList<PlayerServerSide> playerList = new ArrayList<>();
    private final ArrayList<MapObject> mapObjectList = MapGenerator.getMapObjects(MAP_NUMBER);
    private final ArrayList<SpawnLocation> spawnLocations = SpawnLocation.getPlayerSpawnLocationList(MAP_NUMBER);
    private final ArrayList<ItemSpawnLocation> itemSpawnLocations = ItemSpawnLocation.getPlayerSpawnLocationList(MAP_NUMBER);
    private final ArrayList<ClientHandler> clientHandlerList = new ArrayList<>();
    private final ServerUpdateManager serverUpdateManager = new ServerUpdateManager(playerList, clientHandlerList, this,
            itemSpawnLocations, mapObjectList, random);
    private final CollisionManager collisionManager = new CollisionManager(mapObjectList);

    /**
     * constructor starts threads of ServerUpdateManager and CollisionManager
     */
    public ServerCore() {
        serverUpdateManager.startThread();
        try {
            collisionManager.loadMap(MAP_NUMBER);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * start server and thread to accept new clients
     * @param port int server port
     */
    public void runServer(int port) {
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                System.out.println("server running on port: " + port + "\nwrite help for list of available commands");
                while (true) {
                    Socket socket = serverSocket.accept();
                    ClientHandler clientHandler = getClientHandler(socket);
                    clientHandlerList.add(clientHandler);
                    Thread thread = new Thread(clientHandler);
                    thread.start();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    /**
     * create clientHandler, player, input and output streams
     *
     * @param socket client Socket
     * @return ClientHandler
     * @throws IOException if socket stream socket input or output stream cant be loaded
     */
    private ClientHandler getClientHandler(Socket socket) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        PlayerServerSide player = new PlayerServerSide(serverUpdateManager, collisionManager, mapObjectList,
                this, spawnLocations, random);
        return new ClientHandler(socket, in, out, player, this, playerList, matchState, itemSpawnLocations, clientHandlerList);
    }

    /**
     * remove object from map and spawn locations and send request to remove it on client side
     *
     * @param remove Map_Object to remove
     */
    public void removeObject(MapObject remove) {
        mapObjectList.remove(remove);

        for (ClientHandler clientHandler : clientHandlerList) {
            clientHandler.getRemoveObjectRequestList().add(remove);
        }

        for (ItemSpawnLocation location : itemSpawnLocations) {
            if (remove == location.getObject()) {
                location.setObject(null);
            }
        }
    }

    /**
     * add object to map and send request to add it to client side
     *
     * @param add MapObject to add
     */
    public void addObject(MapObject add) {
        mapObjectList.add(add);

        for (ClientHandler clientHandler : clientHandlerList) {
            clientHandler.getAddObjectRequestList().add(add);
        }
    }

    /**
     * remove player and send request to remove it to other clients
     *
     * @param playerServerSide PlayerServerSide to remove
     */
    public void removePlayer(PlayerServerSide playerServerSide) {
        for (ClientHandler clientHandler : clientHandlerList) {
            clientHandler.getPlayerToRemoveList().add(playerServerSide);
        }
        playerList.remove(playerServerSide);
    }

    /**
     * add player and send request to add it to other clients
     *
     * @param playerAdd             PlayerServerSide to add
     * @param originalClientHandler original ClientHandler of playerAdd to prevent duplication
     */
    public void addPlayer(PlayerServerSide playerAdd, ClientHandler originalClientHandler) {
        for (ClientHandler clientHandler : clientHandlerList) {
            if (originalClientHandler != clientHandler) {
                clientHandler.getPlayersToAddList().add(playerAdd);
            }
        }
        playerList.add(playerAdd);
    }

    public void setMATCH_TIME(int MATCH_TIME) {
        this.MATCH_TIME = MATCH_TIME;
    }

    public ArrayList<PlayerServerSide> getPlayerList() {
        return playerList;
    }

    public int getMAP_NUMBER() {
        return MAP_NUMBER;
    }

    public int getMATCH_TIME() {
        return MATCH_TIME;
    }

    public ServerMatchState getMatchState() {
        return matchState;
    }

    public void setMatchState(ServerMatchState matchState) {
        this.matchState = matchState;
    }
}
