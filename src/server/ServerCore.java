package server;

import server.entity.PlayerServerSide;
import shared.MapGenerator;
import shared.object.objectClasses.Object;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class ServerCore {
    private final int MAP_NUMBER = 0; // this variable will be used for selecting map later
    private int MATCH_TIME = 60;
    private final ArrayList<PlayerServerSide> playerList = new ArrayList<>();
    private final ArrayList<Object> objectList = MapGenerator.getMapObjects(MAP_NUMBER);
    private final ArrayList<ClientHandler> clientHandlerList = new ArrayList<>();
    private final ServerUpdateManager serverUpdateManager = new ServerUpdateManager(playerList, clientHandlerList, this);
    private final CollisionManager collisionManager = new CollisionManager(objectList);

    public ServerCore() {
        serverUpdateManager.startThread();
    }

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
            } catch (SocketException ignored) {
                //goofy ahh exception
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    private ClientHandler getClientHandler(Socket socket) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        PlayerServerSide player = new PlayerServerSide(serverUpdateManager, collisionManager, objectList, this);
        return new ClientHandler(socket, in, out, player, this, playerList, serverUpdateManager.getMatchState());
    }

    public static void closeSocket(Socket socket, BufferedWriter out, BufferedReader in) {
        try {
            socket.close();
            in.close();
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeObject(Object remove) {
        objectList.remove(remove);
        for (ClientHandler clientHandler : clientHandlerList) {
            clientHandler.getRemoveObjectRequestList().add(remove);
        }
    }

    public void addObject(Object add) {
        objectList.add(add);
        for (ClientHandler clientHandler : clientHandlerList) {
            clientHandler.getAddObjectRequestList().add(add);
        }
        System.out.println(add);
    }

    public void removePlayer(PlayerServerSide playerServerSide) {
        for (ClientHandler clientHandler : clientHandlerList) {
            clientHandler.getRemovePlayerRequestList().add(playerServerSide);
        }
        playerList.remove(playerServerSide);
    }

    public void addPlayer(PlayerServerSide playerServerSide, ClientHandler originalClienthandler) {
        for (ClientHandler clientHandler : clientHandlerList) {
            if (originalClienthandler != clientHandler) {
                clientHandler.getAddPlayerRequestList().add(playerServerSide);
            }
        }
        playerList.add(playerServerSide);
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
}
