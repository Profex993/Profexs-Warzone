package client.entity;

import client.clientMain.GamePanel;
import shared.ServerOutputToClient;

import java.awt.*;

public class PlayerMain extends Entity {

    public PlayerMain(String name, String playerModel, int worldX, int worldY) {
        screenX = GamePanel.getScreenWidth() / 2 - (48 / 2);
        screenY = GamePanel.getScreenHeight() / 2 - (48 / 2);
        this.name = name;
        this.worldX = worldX;
        this.worldY = worldY;

        setPlayerImage(playerModel);
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
