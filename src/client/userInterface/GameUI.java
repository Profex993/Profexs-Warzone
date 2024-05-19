package client.userInterface;

import client.clientMain.ConstantsClient;
import client.clientMain.GameCore;
import client.clientMain.GamePanel;
import client.clientMain.KeyHandler;
import client.clientMain.sound.SoundManager;
import client.entity.Entity;
import client.entity.MainPlayer;
import client.entity.Player;

import java.awt.*;
import java.util.ArrayList;

/**
 * class for drawing game UI
 */
public class GameUI {
    private final int screenWidth, screenHeight;
    private final MainPlayer player;
    private final ArrayList<Player> playerList;
    private int deathScreenCounter = 0;
    private final KeyHandler keyHandler;
    private final GameCore core;
    private final ArrayList<ChatMessage> chatMessages = new ArrayList<>();

    /**
     * @param player MainPlayer for displaying stats and leaderboard
     * @param playerList Arraylist of Players list for leaderboard
     * @param screenWidth int screen width
     * @param screenHeight int screen height
     * @param keyHandler keyHandler for keyboard input
     * @param core GameCore for accessing components
     */
    public GameUI(MainPlayer player, ArrayList<Player> playerList, int screenWidth, int screenHeight, KeyHandler keyHandler, GameCore core) {
        this.player = player;
        this.playerList = playerList;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.keyHandler = keyHandler;
        this.core = core;
    }

    /**
     * method which selects what to draw
     * @param g2 Graphics2D
     */
    public void draw(Graphics2D g2) {
        g2.setColor(Color.red);
        if (player.isDeath()) {
            drawDeathScreen(g2);
        } else {
            drawGameUI(g2);
        }

        if (keyHandler.leaderBoard) {
            drawLeaderBoard(g2);
            g2.drawString(String.valueOf(core.getCurrentMatchTime()), 5, 30);
        }

        drawChat(g2);
    }

    /**
     * @param g2 Graphics2D
     */
    private void drawChat(Graphics2D g2) {
        g2.setColor(Color.red);
        g2.setFont(ConstantsClient.FONT_25);
        for (int i = 0; i < chatMessages.size(); i++) {
            ChatMessage message = chatMessages.get(i);
            g2.drawString(message.getMessage(), 30, i * 40 + 40);
            message.decreaseTimer();
            if (message.getTimer() == 0) {
                chatMessages.remove(message);
            }
        }
    }

    /**
     * draw player UI with stats and weapon
     * @param g2 Graphics2D
     */
    private void drawGameUI(Graphics2D g2) {
        g2.setFont(ConstantsClient.FONT_25);
        g2.drawString(String.valueOf(player.getHealth()), 30, screenHeight - 60);
        g2.fillRect(80, screenHeight - 75, player.getHealth(), 15);

        if (player.getWeapon() != null) {
            g2.setColor(ConstantsClient.TRANSPARENT_COLOR);
            g2.fillRect(screenWidth - 250, screenHeight - 140, 230, 120);
            g2.setColor(Color.red);
            g2.drawString(player.getWeapon().getName(), screenWidth - 230, screenHeight - 120);
            g2.drawString(player.getWeapon().getCurrentMagazineSize() + "/" + player.getWeapon().getMagazineSize(), screenWidth - 230, screenHeight - 90);
            g2.drawImage(player.getWeapon().getRightImage(), screenWidth - 230, screenHeight - 70, 180, 45, null);
        }

        if (core.getCurrentMatchTime() < 3) {
            drawCountDown(g2);
        }
    }

    /**
     * draw game over screen
     * @param g2 Graphics2D
     */
    public void drawGameOver(Graphics2D g2) {
        if (core.getCurrentMatchTime() > 3) {
            drawLeaderBoard(g2);
            g2.setFont(ConstantsClient.FONT_100);
            g2.setColor(Color.red);
            g2.drawString("GAME OVER",
                    (GamePanel.getScreenWidth() - g2.getFontMetrics().stringWidth("GAME OVER")) / 2,
                    GamePanel.getScreenHeight() / 2 + 150);
            g2.setFont(ConstantsClient.FONT_50);
            ArrayList<Entity> leaderBoard = makeLeaderBoard();
            if (leaderBoard.get(0).getKills() != 0) {
                g2.drawString("Winner is: " + leaderBoard.get(0).getName(),
                        (GamePanel.getScreenWidth() - g2.getFontMetrics().stringWidth("Winner is: " + leaderBoard.get(0).getName())) / 2,
                        GamePanel.getScreenHeight() / 2 + 250);
            }
        } else {
            drawCountDown(g2);
        }
    }

    /**
     * draw end game countdown
     * @param g2 Graphics2D
     */
    private void drawCountDown(Graphics2D g2) {
        g2.setFont(ConstantsClient.FONT_100);
        g2.setColor(Color.red);
        g2.drawString(String.valueOf(core.getCurrentMatchTime()),
                (GamePanel.getScreenWidth() - g2.getFontMetrics().stringWidth(String.valueOf(core.getCurrentMatchTime()))) / 2,
                GamePanel.getScreenHeight() / 2 + 150);
    }

    /**
     * draw death screen
     * @param g2 Graphics2D
     */
    private void drawDeathScreen(Graphics2D g2) {
        if (deathScreenCounter < 100) {
            g2.setFont(ConstantsClient.FONT_100);
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

    /**
     * draw and leader board on screen
     * @param g2 Graphics2D
     */
    private void drawLeaderBoard(Graphics2D g2) {
        ArrayList<Entity> leaderBoard = makeLeaderBoard();
        g2.setColor(ConstantsClient.TRANSPARENT_COLOR);
        g2.fillRect(GamePanel.screenWidth / 2 - 300, 0, 600, leaderBoard.size() * 40 + 40);
        g2.setColor(Color.red);
        g2.setFont(ConstantsClient.FONT_25);
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

    /**
     * make leader board
     * @return returns sorted Arraylist of players with the winner on index 0
     */
    private ArrayList<Entity> makeLeaderBoard() {
        ArrayList<Entity> leaderBoard = new ArrayList<>(playerList);
        leaderBoard.add(player);
        leaderBoard.sort((e1, e2) -> {
            int compareKills = Integer.compare(e2.getKills(), e1.getKills());
            if (compareKills != 0) {
                return compareKills;
            } else {
                return Integer.compare(e1.getDeaths(), e2.getDeaths());
            }
        });
        return leaderBoard;
    }

    /**
     * add chat message and play sound
     * @param message ChatMessage to add
     */
    public void addChatMessage(ChatMessage message) {
        chatMessages.add(message);
        SoundManager.playSound(SoundManager.chat);
    }
}
