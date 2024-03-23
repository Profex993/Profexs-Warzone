package client.entity;

import client.clientMain.KeyHandler;

import java.awt.*;

public class PlayerMain {
    private int x, y;
    private final int speed = 4;
    private final String name;
    private final KeyHandler keyHandler;

    public PlayerMain(String name, int x, int y, KeyHandler keyHandler) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.keyHandler = keyHandler;
    }

    public void update() {
        if (keyHandler.up) {
            y -= speed;
        } else if (keyHandler.down) {
            y += speed;
        } else if (keyHandler.left) {
            x -= speed;
        } else if (keyHandler.right) {
            x += speed;
        }
    }

    public void draw(Graphics2D g2) {
        g2.drawRect(x, y, 48, 48);
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed(){
        return speed;
    }
}
