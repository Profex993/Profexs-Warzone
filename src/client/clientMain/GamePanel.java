package client.clientMain;

import client.entity.Player;
import client.entity.PlayerMain;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {
    private Thread thread;
    private int currentFps = 0;
    private final PlayerMain playerMain;
    private final ArrayList<Player> playerList;


    public GamePanel(PlayerMain player, ArrayList<Player> playerList, KeyHandler keyHandler) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setPreferredSize(new Dimension(screenSize.width, screenSize.height));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
//        this.addMouseListener(mouseHandler);
        this.setFocusable(true);
        this.playerMain = player;
        this.playerList = playerList;
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
        playerMain.draw(g2);
        for (Player player : playerList) {
            player.draw(g2);
        }
        g2.drawString(String.valueOf(currentFps), 5, 10);
        g2.dispose();
    }

}
