package client.entity;

import client.clientMain.GamePanel;
import client.clientMain.KeyHandler;
import client.clientMain.MouseHandler;

import java.awt.*;

public class PlayerMain extends Entity {
    private final KeyHandler keyHandler;
    private final MouseHandler mouseHandler;

    public PlayerMain(String name, int worldX, int worldY, KeyHandler keyHandler, MouseHandler mouseHandler) {
        screenX = GamePanel.getScreenWidth() / 2 - (48 / 2);
        screenY = GamePanel.getScreenHeight() / 2 - (48 / 2);
        this.name = name;
        this.worldX = worldX;
        this.worldY = worldY;
        this.keyHandler = keyHandler;
        this.mouseHandler = mouseHandler;

        setPlayerImage();
    }

    @Override
    public void update() {
        super.update();

        if (keyHandler.up) {
            worldY -= speed;
            walkCounter++;
            walking = true;
            idleCounter = 0;
        } else if (keyHandler.down) {
            worldY += speed;
            walkCounter++;
            walking = true;
            idleCounter = 0;
        } else if (keyHandler.left) {
            worldX -= speed;
            walkCounter++;
            walking = true;
            idleCounter = 0;
        } else if (keyHandler.right) {
            worldX += speed;
            walkCounter++;
            walking = true;
            idleCounter = 0;
        } else {
            idleCounter++;
            walking = false;
        }

        if (keyHandler.up) {
            direction = "up";
        } else if (keyHandler.down) {
            direction = "down";
        } else if (keyHandler.left) {
            direction = "left";
        } else if (keyHandler.right) {
            direction = "right";
        }

        double angle = Math.atan2(mouseHandler.getY() - (screenY + (double) width / 2), mouseHandler.getX() - (screenX + (double) width / 2));
        if (angle >= -Math.PI / 4 && angle < Math.PI / 4) {
            directionFace = "right";
        } else if (angle >= Math.PI / 4 && angle < 3 * Math.PI / 4) {
            directionFace = "down";
        } else if (angle >= -3 * Math.PI / 4 && angle < -Math.PI / 4) {
            directionFace = "up";
        } else {
            directionFace = "left";
        }
    }

    public void draw(Graphics2D g2) {
        super.draw(g2);
    }
    public String outputToServer() {
        return name + "," + worldX + "," + worldY + "," + direction + "," + directionFace + "," + walking;
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
}
