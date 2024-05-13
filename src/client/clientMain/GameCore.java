package client.clientMain;

import client.clientMain.serverCommunication.ServerCommunication;
import client.entity.MainPlayer;
import client.entity.Player;
import client.enums.GameStateClient;
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
    private GameStateClient gameState = GameStateClient.GAME;
    private int currentMatchTime;
    private final MainPlayer mainPlayer;
    private final ArrayList<Player> playerList;
    private final TileManager tileManager;
    private ArrayList<Object> objectList;

    public GameCore(String name, String playerModel, Socket socket, BufferedReader in, BufferedWriter out) {
        playerList = new ArrayList<>();
        MouseHandler mouseHandler = new MouseHandler(this);
        KeyHandler keyHandler = new KeyHandler(this);
        Menu menu = new Menu(mouseHandler, this);
        mainPlayer = new MainPlayer(name, playerModel, mouseHandler, keyHandler, this);
        ServerCommunication serverCommunication = new ServerCommunication(mainPlayer, playerModel, socket, in, out,
                keyHandler, mouseHandler, this);
        try {
            tileManager = new TileManager(mainPlayer);
        } catch (Exception e) {
            ClientMain.closeSocket(socket, in, out);
            throw new RuntimeException();
        }
        gamePanel = new GamePanel(mainPlayer, playerList, keyHandler, mouseHandler, tileManager, menu, this);
        updateManager = new UpdateManager(serverCommunication, menu, mainPlayer, mouseHandler, this);

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

    public void setMatchTime(int set) {
        currentMatchTime = set;
    }

    public void decreaseCurrentMatchTime() {
        currentMatchTime--;
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

    public GameStateClient getGameState() {
        return gameState;
    }

    public void setGameState(GameStateClient gameState) {
        this.gameState = gameState;
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
    public int getTick() {
        return updateManager.getTick();
    }

    public int getCurrentMatchTime() {
        return currentMatchTime;
    }
}
