package server;

import server.entity.PlayerServerSide;
import shared.MapGenerator;
import shared.objects.Object;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class ServerCore {
    private final ArrayList<PlayerServerSide> playerList = new ArrayList<>();
    private final ServerUpdateManager serverUpdateManager = new ServerUpdateManager(playerList);
    private final CollisionManager collisionManager = new CollisionManager();
    public final static int mapNumber = 0; // this variable will be used for selecting map later
    private final ArrayList<Object> objectList = MapGenerator.getMapObjects(mapNumber);
    private final ArrayList<ClientHandler> clientHandlerList = new ArrayList<>();

    public ServerCore() {
        serverUpdateManager.startThread();
    }

    public void runServer(int port) {
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                System.out.println("server running on port: " + port + "\nwrite help for list of available commands");
                while (true) {
                    Socket socket = serverSocket.accept();
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    PlayerServerSide player = new PlayerServerSide(serverUpdateManager, collisionManager, objectList, this);
                    ClientHandler clientHandler = new ClientHandler(socket, in, out, player, this, playerList);
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
            clientHandler.getRemoveObject().add(remove.hashCode());
        }
    }

    public void addObject(Object add) {
        objectList.remove(add);
        for (ClientHandler clientHandler : clientHandlerList) {
            clientHandler.getAddObject().add(add);
        }
    }
    public void removePlayer(PlayerServerSide playerServerSide) {
        for (ClientHandler clientHandler : clientHandlerList) {
            clientHandler.getRemovePlayer().add(playerServerSide.getId());
        }
        playerList.remove(playerServerSide);
    }

    public void addPlayer(PlayerServerSide playerServerSide, ClientHandler originalClienthandler) {
        for (ClientHandler clientHandler : clientHandlerList) {
            if (originalClienthandler != clientHandler) {
                clientHandler.getAddPlayerList().add(playerServerSide);
            }
        }
        playerList.add(playerServerSide);
    }
    public ArrayList<PlayerServerSide> getPlayerList() {
        return playerList;
    }
}
