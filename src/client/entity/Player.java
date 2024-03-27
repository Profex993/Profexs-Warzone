package client.entity;

import shared.PlayerInput;

import java.awt.*;

public class Player {
    private int x, y;
    private final String name;

    public Player(String name) {
        this.name = name;
    }

    public void draw(Graphics2D g2) {
        g2.drawRect(x, y, 48, 48);
        g2.drawString(name, x, y - 5);
    }

    public void update() {

    }

    public void updateFromInputData(PlayerInput playerInput) {
        this.x = playerInput.x();
        this.y = playerInput.y();
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
