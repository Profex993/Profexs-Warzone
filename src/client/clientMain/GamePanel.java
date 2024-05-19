package client.clientMain;

import client.entity.MainPlayer;
import client.entity.Player;
import client.enums.GameStateClient;
import client.userInterface.ChatMessage;
import client.userInterface.GameUI;
import client.userInterface.menu.Menu;
import shared.object.objectClasses.MapObject;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * class for graphics
 */
public class GamePanel extends JPanel implements Runnable {
    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static final int screenWidth = screenSize.width, screenHeight = screenSize.height;
    private Thread thread;
    private int currentFps = 0;
    private final MainPlayer mainPlayer;
    private final ArrayList<Player> playerList;
    private ArrayList<MapObject> mapObjectList = new ArrayList<>();
    private final TileManager tileManager;
    private final Menu menu;
    private final GameUI gameUI;
    private final MouseHandler mouseHandler;
    private final GameCore core;

    /**
     * constructor initializes GameUi
     * @param player MainPlayer
     * @param playerList Arraylist of Players
     * @param keyHandler KeyHandler
     * @param mouseHandler MouseHandler
     * @param tileManager TileManager
     * @param menu pause Menu
     * @param core GameCore for checking ClientGameState
     */
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
    }

    public void startThread() {
        thread = new Thread(this);
        thread.start();
    }

    /**
     * loop for drawing, locked on 120 fps in double interval
     */
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

    /**
     * method for drawing components
     * @param g the <code>Graphics</code> object to protect
     */
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
        g2.setFont(ConstantsClient.FONT_10);
        g2.drawString(String.valueOf(currentFps), 5, 10);
        g2.dispose();
    }

    /**
     * drawing game components
     * @param g2 Graphics2D
     */
    public void drawGame(Graphics2D g2) {
        tileManager.draw(g2);
        for (int i = 0; i < mapObjectList.size(); i++) {
            if (mapObjectList.get(i).isDrawUnderPlayer()) {
                mapObjectList.get(i).draw(g2, mainPlayer, (int) mouseHandler.getX(), (int) mouseHandler.getY());
            }
        }
        mainPlayer.draw(g2);
        for (Player player : playerList) {
            player.draw(g2);
        }
        for (int i = 0; i < mapObjectList.size(); i++) {
            if (!mapObjectList.get(i).isDrawUnderPlayer()) {
                mapObjectList.get(i).draw(g2, mainPlayer, (int) mouseHandler.getX(), (int) mouseHandler.getY());
            }
        }
    }

    /**
     * set ObjectList and initialize resource
     * @param mapObjectListUnderPlayer Arraylist of MapObjects
     */
    public void setObjectList(ArrayList<MapObject> mapObjectListUnderPlayer) {
        this.mapObjectList = mapObjectListUnderPlayer;

        try {
            for (MapObject mapObject : mapObjectListUnderPlayer) {
                mapObject.initializeRes();
            }
        } catch (IOException e) {
            ClientMain.showErrorWindowAndExit("can not load resources", e);
        }
    }

    public void addChatMessage(ChatMessage message) {
        gameUI.addChatMessage(message);
    }
    public static int getScreenWidth() {
        return screenWidth;
    }

    public static int getScreenHeight() {
        return screenHeight;
    }
}
