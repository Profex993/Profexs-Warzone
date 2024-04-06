package client.clientMain;

import client.entity.MainPlayer;
import client.entity.Player;
import client.enums.GameState;
import client.menu.Menu;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {
    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static final int screenWidth = screenSize.width, screenHeight = screenSize.height;
    private Thread thread;
    private int currentFps = 0;
    private final MainPlayer mainPlayer;
    private final ArrayList<Player> playerList;
    private final TileManager tileManager;
    private final client.menu.Menu menu;
    private final Font fpsFont = new Font("arial", Font.BOLD, 10);

    public GamePanel(MainPlayer player, ArrayList<Player> playerList, KeyHandler keyHandler, MouseHandler mouseHandler,
                     TileManager tileManager, Menu menu) {
        this.setPreferredSize(screenSize);
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.addMouseListener(mouseHandler);
        this.setFocusable(true);
        this.mainPlayer = player;
        this.playerList = playerList;
        this.tileManager = tileManager;
        this.menu = menu;
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

        if (GameCore.gameState == GameState.GAME.intValue) {
            drawGame(g2);
        } else if (GameCore.gameState == GameState.PAUSED.intValue) {
            drawGame(g2);
            menu.draw(g2);
        }

        g2.setFont(fpsFont);
        g2.drawString(String.valueOf(currentFps), 5, 10);
        g2.dispose();
    }

    public void drawGame(Graphics2D g2) {
        tileManager.draw(g2);
        mainPlayer.draw(g2);
        for (Player player : playerList) {
            player.draw(g2);
        }
    }

    public static int getScreenWidth() {
        return screenWidth;
    }

    public static int getScreenHeight() {
        return screenHeight;
    }
}
