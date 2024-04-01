package client.clientMain;

import client.entity.Player;
import client.entity.PlayerMain;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.Socket;
import java.util.ArrayList;

public class GameCore {
    private final GamePanel gamePanel;

    public GameCore(String name, String playerModel, Socket socket, BufferedReader in, BufferedWriter out) {
        MouseHandler mouseHandler = new MouseHandler();
        KeyHandler keyHandler = new KeyHandler();
        PlayerMain playerMain = new PlayerMain(name, playerModel, 0, 0, keyHandler, mouseHandler);
        TileManager tileManager;
        try {
            tileManager = new TileManager(playerMain);
        } catch (Exception e) {
            ClientMain.closeSocket(socket, in, out);
            throw new RuntimeException();
        }
        ArrayList<Player> playerList = new ArrayList<>();
        gamePanel = new GamePanel(playerMain, playerList, keyHandler, mouseHandler, tileManager);
        ServerCommunication serverCommunication = new ServerCommunication(playerMain, playerModel, playerList, socket, in, out);
        UpdateManager updateManager = new UpdateManager(playerMain, playerList, serverCommunication);
        updateManager.startThread();
        gamePanel.startThread();
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }
}
