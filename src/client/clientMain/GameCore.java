package client.clientMain;

import client.clientMain.serverCommunication.ServerCommunication;
import client.entity.MainPlayer;
import client.entity.Player;
import client.enums.GameStateClient;
import client.userInterface.menu.Menu;
import shared.MapGenerator;
import shared.object.objectClasses.MapObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * core of client which contains all key components of client
 */
public class GameCore {
    private final GamePanel gamePanel;
    private final UpdateManager updateManager;
    private GameStateClient gameState = GameStateClient.GAME;
    private int currentMatchTime;
    private final MainPlayer mainPlayer;
    private final ArrayList<Player> playerList;
    private final TileManager tileManager;
    private ArrayList<MapObject> mapObjectList;

    /**
     * constructor initializes components and starts GamePanel thread and UpdateManager thread
     * @param name players name String
     * @param playerModel players model String
     * @param in BufferedReader input from server
     * @param out BufferedWriter output to server
     */
    public GameCore(String name, String playerModel, BufferedReader in, BufferedWriter out) {
        playerList = new ArrayList<>();
        MouseHandler mouseHandler = new MouseHandler(this);
        KeyHandler keyHandler = new KeyHandler(this);
        Menu menu = new Menu(mouseHandler, this);
        mainPlayer = new MainPlayer(name, playerModel, mouseHandler, keyHandler, this);
        ServerCommunication serverCommunication = new ServerCommunication(mainPlayer, playerModel, in, out,
                keyHandler, mouseHandler, this);
        tileManager = new TileManager(mainPlayer);
        gamePanel = new GamePanel(mainPlayer, playerList, keyHandler, mouseHandler, tileManager, menu, this);
        updateManager = new UpdateManager(serverCommunication, menu, mainPlayer, mouseHandler, this);

        updateManager.startThread();
        gamePanel.startThread();
    }

    /**
     * set new map
     * @param mapNumber int of new map
     */
    public void setMap(int mapNumber) {
        ArrayList<MapObject> newMapObjectList = MapGenerator.getMapObjects(mapNumber);
        gamePanel.setObjectList(newMapObjectList);
        updateManager.setObjectList(newMapObjectList);
        mapObjectList = newMapObjectList;
        try {
            tileManager.loadMap(mapNumber);
        } catch (IOException e) {
            ClientMain.showErrorWindowAndExit("can not load resources", e);
        }
    }

    public void setMatchTime(int set) {
        currentMatchTime = set;
    }

    public void decreaseCurrentMatchTime() {
        currentMatchTime--;
    }

    public void addToObjectList(MapObject mapObject) {
        try {
            mapObject.initializeRes();
            mapObjectList.add(mapObject);
        } catch (IOException e) {
            ClientMain.showErrorWindowAndExit("can not load resources", e);
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

    public ArrayList<MapObject> getObjectList() {
        return mapObjectList;
    }

    public int getTick() {
        return updateManager.getTick();
    }

    public int getCurrentMatchTime() {
        return currentMatchTime;
    }
}
