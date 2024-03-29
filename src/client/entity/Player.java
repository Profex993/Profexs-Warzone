package client.entity;

import shared.PlayerInput;

import java.awt.*;

public class Player extends Entity {
    private final PlayerMain playerMain;

    public Player(PlayerMain playerMain, String name) {
        this.playerMain = playerMain;
        this.name = name;
        direction = "down";
        directionFace = "down";
        setPlayerImage();
    }

    public void draw(Graphics2D g2) {
        screenX = worldX - playerMain.getWorldX() + playerMain.getScreenX();
        screenY = worldY - playerMain.getWorldY() + playerMain.getScreenY();
        super.draw(g2);
    }

    public void update() {
        if (walking) {
            walkCounter++;
            idleCounter = 0;
        } else {
            idleCounter++;
        }
        super.update();
    }

    public void updateFromInputData(PlayerInput playerInput) {
        this.worldX = playerInput.x();
        this.worldY = playerInput.y();
        this.direction = playerInput.direction();
        this.directionFace = playerInput.directionFace();
        this.walking = playerInput.walking();
    }

    public String getName() {
        return name;
    }
}
