package client.entity;

import shared.PlayerInput;

import java.awt.*;

public class Player {
    private int worldX, worldY, screenX, screenY;
    private final String name;
    private final PlayerMain playerMain;

    public Player(PlayerMain playerMain, String name) {
        this.playerMain = playerMain;
        this.name = name;
    }

    public void draw(Graphics2D g2) {
        screenX = worldX - playerMain.getWorldX() + playerMain.getScreenX();
        screenY = worldY - playerMain.getWorldY() + playerMain.getScreenY();
        g2.drawRect(screenX, screenY, 48, 48);
        g2.drawString(name, screenX, screenY - 5);
    }

    public void update() {

    }

    public void updateFromInputData(PlayerInput playerInput) {
        this.worldX = playerInput.x();
        this.worldY = playerInput.y();
    }

    public String getName() {
        return name;
    }

    public int getWorldX() {
        return worldX;
    }

    public void setWorldX(int worldX) {
        this.worldX = worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    public void setWorldY(int worldY) {
        this.worldY = worldY;
    }
}
