package client.clientMain;

import client.entity.Player;
import client.entity.PlayerMain;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.Socket;
import java.util.ArrayList;

public class GameCore {
    private final KeyHandler keyHandler = new KeyHandler();
    private final PlayerMain playerMain;
    private final ArrayList<Player> playerList = new ArrayList<>();
    private final ServerCommunication serverCommunication;
    private final GamePanel gamePanel;
    private final UpdateManager updateManager;
    private final TileManager tileManager;

    public GameCore(String name, Socket socket, BufferedReader in, BufferedWriter out) {
        playerMain = new PlayerMain(name, 0, 0, keyHandler);
        try {
            this.tileManager = new TileManager(playerMain);
        } catch (Exception e) {
            ClientMain.closeSocket(socket, in, out);
            throw new RuntimeException();
        }
        gamePanel = new GamePanel(playerMain, playerList, keyHandler, tileManager);
        serverCommunication = new ServerCommunication(playerMain, playerList, socket, in , out);
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
