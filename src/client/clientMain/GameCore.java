package client.clientMain;

import client.entity.MainPlayer;
import client.entity.Player;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.Socket;
import java.util.ArrayList;

public class GameCore {
    private final GamePanel gamePanel;

    public GameCore(String name, String playerModel, Socket socket, BufferedReader in, BufferedWriter out) {
        MouseHandler mouseHandler = new MouseHandler();
        KeyHandler keyHandler = new KeyHandler();
        MainPlayer mainPlayer = new MainPlayer(name, playerModel, 0, 0);
        TileManager tileManager;
        try {
            tileManager = new TileManager(mainPlayer);
        } catch (Exception e) {
            ClientMain.closeSocket(socket, in, out);
            throw new RuntimeException();
        }
        ArrayList<Player> playerList = new ArrayList<>();
        gamePanel = new GamePanel(mainPlayer, playerList, keyHandler, mouseHandler, tileManager);
        ServerCommunication serverCommunication = new ServerCommunication(mainPlayer, playerModel, playerList, socket, in, out,
                keyHandler, mouseHandler);
        UpdateManager updateManager = new UpdateManager(serverCommunication);
        updateManager.startThread();
        gamePanel.startThread();
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }
}
