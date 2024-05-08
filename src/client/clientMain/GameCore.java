package client.clientMain;

import client.clientMain.serverCommunication.ServerCommunication;
import client.entity.MainPlayer;
import client.entity.Player;
import client.enums.GameState;
import client.userInterface.menu.Menu;
import shared.MapGenerator;
import shared.object.objectClasses.Object;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class GameCore {
    private final GamePanel gamePanel;
    private final UpdateManager updateManager;
    public static int gameState = GameState.GAME.intValue;
    private final MainPlayer mainPlayer;
    private final ArrayList<Player> playerList;
    private final TileManager tileManager;
    private ArrayList<Object> objectList;

    public GameCore(String name, String playerModel, Socket socket, BufferedReader in, BufferedWriter out) {
        playerList = new ArrayList<>();
        MouseHandler mouseHandler = new MouseHandler();
        KeyHandler keyHandler = new KeyHandler();
        Menu menu = new Menu(mouseHandler);
        mainPlayer = new MainPlayer(name, playerModel, 0, 0, mouseHandler, keyHandler);
        ServerCommunication serverCommunication = new ServerCommunication(mainPlayer, playerModel, socket, in, out,
                keyHandler, mouseHandler, this);
        try {
            tileManager = new TileManager(mainPlayer);
        } catch (Exception e) {
            ClientMain.closeSocket(socket, in, out);
            throw new RuntimeException();
        }
        gamePanel = new GamePanel(mainPlayer, playerList, keyHandler, mouseHandler, tileManager, menu);
        updateManager = new UpdateManager(serverCommunication, menu, mainPlayer, mouseHandler);

        updateManager.startThread();
        gamePanel.startThread();
    }

    public void setMap(int mapNumber) {
        ArrayList<Object> newObjectList = MapGenerator.getMapObjects(mapNumber);
        gamePanel.setObjectList(newObjectList);
        updateManager.setObjectList(newObjectList);
        objectList = newObjectList;
        try {
            tileManager.loadMap(mapNumber);
        } catch (IOException e) {
            ClientMain.closeSocket();
            throw new RuntimeException(e);
        }
    }

    public void addToObjectList(Object object) {
        try {
            object.initializeRes();
            objectList.add(object);
        } catch (IOException e) {
            ClientMain.closeSocket();
            throw new RuntimeException(e);
        }
    }

    public static void changeGameState(int change) {
        gameState = change;
    }
    public GamePanel getGamePanel() {
        return gamePanel;
    }
    public MainPlayer getMainPlayer() {
        return mainPlayer;
    }
    public ArrayList<Player> getPlayerList() {
        return playerList;
    }
    public ArrayList<Object> getObjectList() {
        return objectList;
    }
}
