package client.entity;

import client.clientMain.GamePanel;
import client.clientMain.KeyHandler;

import java.awt.*;

public class PlayerMain {
    private int worldX, worldY;
    private final int speed = 4, screenX, screenY;
    private final String name;
    private final KeyHandler keyHandler;

    public PlayerMain(String name, int worldX, int worldY, KeyHandler keyHandler) {
        screenX = GamePanel.getScreenWidth() / 2 - (48 / 2);
        screenY = GamePanel.getScreenHeight() / 2 - (48 / 2);
        this.name = name;
        this.worldX = worldX;
        this.worldY = worldY;
        this.keyHandler = keyHandler;
    }

    public void update() {
        if (keyHandler.up) {
            worldY -= speed;
        } else if (keyHandler.down) {
            worldY += speed;
        } else if (keyHandler.left) {
            worldX -= speed;
        } else if (keyHandler.right) {
            worldX += speed;
        }
    }

    public void draw(Graphics2D g2) {
        g2.drawRect(screenX, screenY, 48, 48);
        g2.drawString(name, screenX, screenY - 5);
    }

    public String getName() {
        return name;
    }

    public int getWorldX() {
        return worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    public int getScreenX() {
        return screenX;
    }

    public int getScreenY() {
        return screenY;
    }

    public int getSpeed() {
        return speed;
    }
}
