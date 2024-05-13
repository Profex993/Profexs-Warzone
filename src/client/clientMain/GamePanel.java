package client.clientMain;

import client.entity.MainPlayer;
import client.entity.Player;
import client.enums.GameStateClient;
import client.userInterface.GameUI;
import client.userInterface.menu.Menu;
import shared.object.objectClasses.Object;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {
    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static final int screenWidth = screenSize.width, screenHeight = screenSize.height;
    private Thread thread;
    private int currentFps = 0;
    private final MainPlayer mainPlayer;
    private final ArrayList<Player> playerList;
    private ArrayList<Object> objectList = new ArrayList<>();
    private final TileManager tileManager;
    private final Menu menu;
    private final GameUI gameUI;
    private final MouseHandler mouseHandler;
    private final GameCore core;

    public GamePanel(MainPlayer player, ArrayList<Player> playerList, KeyHandler keyHandler, MouseHandler mouseHandler,
                     TileManager tileManager, Menu menu, GameCore core) {
        this.setPreferredSize(screenSize);
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.addMouseListener(mouseHandler);
        this.setFocusable(true);
        this.mainPlayer = player;
        this.playerList = playerList;
        this.tileManager = tileManager;
        this.mouseHandler = mouseHandler;
        this.menu = menu;
        this.core = core;
        gameUI = new GameUI(mainPlayer, playerList, screenWidth, screenHeight, keyHandler, core);

        try {
            for (Object object : objectList) {
                object.initializeRes();
            }
        } catch (IOException e) {
            ClientMain.showErrorWindowAndExit("can not load resources", e);
        }
    }

    public void startThread() {
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        double interval = 8333333; //16666666 60fps
        double delta = 0;
        long last = System.nanoTime();
        long time;
        long timer = 0;
        int fpsCounter = 0;
        while (thread != null) {
            time = System.nanoTime();
            delta += (time - last) / interval;
            timer += (time - last);
            last = time;
            if (delta >= 1) {
                repaint();
                delta--;
                fpsCounter++;
            }
            if (timer >= 1000000000) {
                currentFps = fpsCounter;
                fpsCounter = 0;
                timer = 0;
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.white);

        if (core.getGameState() == GameStateClient.GAME) {
            drawGame(g2);
            gameUI.draw(g2);
        } else if (core.getGameState() == GameStateClient.PAUSED) {
            drawGame(g2);
            menu.draw(g2);
        } else if (core.getGameState() == GameStateClient.MATCH_END) {
            drawGame(g2);
            gameUI.drawGameOver(g2);
        }

        g2.setColor(Color.white);
        g2.setFont(Constants.font10);
        g2.drawString(String.valueOf(currentFps), 5, 10);
        g2.dispose();
    }

    public void drawGame(Graphics2D g2) {
        tileManager.draw(g2);
        for (int i = 0; i < objectList.size(); i++) {
            if (objectList.get(i).isDrawUnderPlayer()) {
                objectList.get(i).draw(g2, mainPlayer, (int) mouseHandler.getX(), (int) mouseHandler.getY());
            }
        }
        mainPlayer.draw(g2);
        for (Player player : playerList) {
            player.draw(g2);
        }
        for (int i = 0; i < objectList.size(); i++) {
            if (!objectList.get(i).isDrawUnderPlayer()) {
                objectList.get(i).draw(g2, mainPlayer, (int) mouseHandler.getX(), (int) mouseHandler.getY());
            }
        }
    }

    public void setObjectList(ArrayList<Object> objectListUnderPlayer) {
        this.objectList = objectListUnderPlayer;

        try {
            for (Object object : objectListUnderPlayer) {
                object.initializeRes();
            }
        } catch (IOException e) {
            ClientMain.showErrorWindowAndExit("can not load resources", e);
            throw new RuntimeException();
        }
    }

    public static int getScreenWidth() {
        return screenWidth;
    }

    public static int getScreenHeight() {
        return screenHeight;
    }
}
