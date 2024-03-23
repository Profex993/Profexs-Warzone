package client.clientMain;

import client.entity.Player;
import client.entity.PlayerMain;

import java.util.ArrayList;

public class GameCore {
    private final KeyHandler keyHandler = new KeyHandler();
    private final PlayerMain playerMain;
    private final ArrayList<Player> playerList = new ArrayList<>();
    private final ServerCommunication serverCommunication;
    private final GamePanel gamePanel;
    private final UpdateManager updateManager;

    public GameCore(String name) {
        playerMain = new PlayerMain(name, 0, 0, keyHandler);
        gamePanel = new GamePanel(playerMain, playerList, keyHandler);
        serverCommunication = new ServerCommunication(playerMain, playerList);
        updateManager = new UpdateManager(playerMain, playerList, serverCommunication);
        updateManager.startThread();
        gamePanel.startThread();
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public KeyHandler getKeyHandler() {
        return keyHandler;
    }

    public PlayerMain getPlayerMain() {
        return playerMain;
    }

    public ArrayList<Player> getPlayerList() {
        return playerList;
    }

    public ServerCommunication getServerCommunication() {
        return serverCommunication;
    }

    public UpdateManager getUpdateManager() {
        return updateManager;
    }
}
