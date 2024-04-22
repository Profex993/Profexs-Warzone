package client.clientMain;

import client.entity.MainPlayer;
import client.entity.Player;
import client.enums.GameState;
import client.menu.Menu;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.Socket;
import java.util.ArrayList;

public class GameCore {
    private final GamePanel gamePanel;
    public static int gameState = GameState.GAME.intValue;

    public GameCore(String name, String playerModel, Socket socket, BufferedReader in, BufferedWriter out) {
        ArrayList<Player> playerList = new ArrayList<>();
        MouseHandler mouseHandler = new MouseHandler();
        KeyHandler keyHandler = new KeyHandler();
        MainPlayer mainPlayer = new MainPlayer(name, playerModel, 0, 0, mouseHandler, keyHandler);
        Menu menu = new Menu(mouseHandler);
        TileManager tileManager;
        try {
            tileManager = new TileManager(mainPlayer);
        } catch (Exception e) {
            ClientMain.closeSocket(socket, in, out);
            throw new RuntimeException();
        }
        gamePanel = new GamePanel(mainPlayer, playerList, keyHandler, mouseHandler, tileManager, menu);
        ServerCommunication serverCommunication = new ServerCommunication(mainPlayer, playerModel, playerList, socket, in, out,
                keyHandler, mouseHandler);
        UpdateManager updateManager = new UpdateManager(serverCommunication, menu, mainPlayer);
        updateManager.startThread();
        gamePanel.startThread();
    }

    public static void changeGameState(int change) {
        gameState = change;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }
}
