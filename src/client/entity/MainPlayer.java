package client.entity;

import client.clientMain.GamePanel;
import shared.ServerOutputToClient;

import java.awt.*;

public class MainPlayer extends Entity {

    public MainPlayer(String name, String playerModel, int worldX, int worldY) {
        super(name, playerModel);
        screenX = GamePanel.getScreenWidth() / 2 - (48 / 2);
        screenY = GamePanel.getScreenHeight() / 2 - (48 / 2);
        this.worldX = worldX;
        this.worldY = worldY;
    }

    public void updateFromServerInput(ServerOutputToClient input) {
        this.worldX = input.x();
        this.worldY = input.y();
        this.direction = input.direction();
        this.directionFace = input.directionFace();
        this.walkAnimNum = input.walkAnimNum();
    }

    public void draw(Graphics2D g2) {
        super.draw(g2);
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
