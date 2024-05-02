package client.userInterface;

import client.clientMain.Constants;
import client.clientMain.GamePanel;
import client.clientMain.KeyHandler;
import client.entity.Entity;
import client.entity.MainPlayer;
import client.entity.Player;

import java.awt.*;
import java.util.ArrayList;

public class GameUI {
    private final int screenWidth, screenHeight;
    private final MainPlayer player;
    private final ArrayList<Player> playerList;
    private int deathScreenCounter = 0;
    private final KeyHandler keyHandler;

    public GameUI(MainPlayer player, ArrayList<Player> playerList, int screenWidth, int screenHeight, KeyHandler keyHandler) {
        this.player = player;
        this.playerList = playerList;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.keyHandler = keyHandler;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.red);
        if (player.isDeath()) {
            if (keyHandler.leaderBoard) {
                drawLeaderBoard(g2);
            } else {
                drawDeathScreen(g2);
            }
        } else {
            drawGameUI(g2);

            if (keyHandler.leaderBoard) {
                drawLeaderBoard(g2);
            }
        }
    }

    private void drawGameUI(Graphics2D g2) {
        g2.setFont(Constants.font25);
        g2.drawString(String.valueOf(player.getHealth()), 30, screenHeight - 60);
        g2.fillRect(80, screenHeight - 75, player.getHealth(), 15);

        if (player.getWeapon() != null) {
            g2.setColor(Constants.transparentColor);
            g2.fillRect(screenWidth - 250, screenHeight - 140, 230, 120);
            g2.setColor(Color.red);
            g2.drawString(player.getWeapon().getName(), screenWidth - 230, screenHeight - 120);
            g2.drawString(player.getWeapon().getCurrentMagazineSize() + "/" + player.getWeapon().getMagazineSize(), screenWidth - 230, screenHeight - 90);
            g2.drawImage(player.getWeapon().getRightImage(), screenWidth - 230, screenHeight - 70, 180, 45, null);
        }
    }

    private void drawDeathScreen(Graphics2D g2) {
        if (deathScreenCounter < 100) {
            g2.setFont(Constants.font100);
            g2.drawString("You have been killed by:",
                    (GamePanel.getScreenWidth() - g2.getFontMetrics().stringWidth("You have been killed by:")) / 2,
                    GamePanel.getScreenHeight() / 2 + 50);

            g2.drawString(player.getKilledBy(),
                    (GamePanel.getScreenWidth() - g2.getFontMetrics().stringWidth(player.getKilledBy())) / 2,
                    GamePanel.getScreenHeight() / 2 + 150);
        }

        deathScreenCounter++;
        if (deathScreenCounter >= 200) {
            deathScreenCounter = 0;
        }
    }

    private void drawLeaderBoard(Graphics2D g2) {
        ArrayList<Entity> leaderBoard = makeLeaderBoard();
        g2.setColor(Constants.transparentColor);
        g2.fillRect(GamePanel.screenWidth / 2 - 300, 0, 600, leaderBoard.size() * 40 + 40);
        g2.setColor(Color.red);
        g2.setFont(Constants.font25);
        g2.setStroke(new BasicStroke(2));
        g2.drawRect(GamePanel.screenWidth / 2 - 300, 0, 600, 40);
        g2.drawLine(GamePanel.screenWidth / 2 + 100, 0, GamePanel.screenWidth / 2 + 100, leaderBoard.size() * 40 + 40);
        g2.drawLine(GamePanel.screenWidth / 2 + 200, 0, GamePanel.screenWidth / 2 + 200, leaderBoard.size() * 40 + 40);
        g2.drawString("Name:", GamePanel.screenWidth / 2 - 295, 25);
        g2.drawString("Kills:", GamePanel.screenWidth / 2 + 105, 25);
        g2.drawString("Deaths:", GamePanel.screenWidth / 2 + 205, 25);
        for (int i = 0; i < leaderBoard.size(); i++) {
            g2.drawRect(GamePanel.screenWidth / 2 - 300, i * 40 + 40, 600, 40);
            g2.drawString(leaderBoard.get(i).getName(), GamePanel.screenWidth / 2 - 300 + 5, i * 40 + 65);
            g2.drawString(String.valueOf(leaderBoard.get(i).getKills()), GamePanel.screenWidth / 2 + 105, i * 40 + 65);
            g2.drawString(String.valueOf(leaderBoard.get(i).getDeaths()), GamePanel.screenWidth / 2 + 205, i * 40 + 65);
        }
    }

    private ArrayList<Entity> makeLeaderBoard() {
        ArrayList<Entity> leaderBoard = new ArrayList<>(playerList);
        leaderBoard.add(player);
        leaderBoard.sort((e1, e2) -> Integer.compare(e2.getKills(), e1.getKills()));
        return leaderBoard;
    }
}
