package client.entity;

import client.clientMain.UpdateManager;
import shared.PlayerInput;

import java.awt.*;

public class Player extends Entity {
    private final MainPlayer mainPlayer;
    private double mouseX, mouseY;
    private boolean shootLock = true;

    public Player(MainPlayer mainPlayer, String name, String playerModel) {
        super(name, playerModel);
        this.mainPlayer = mainPlayer;
    }

    public void draw(Graphics2D g2) {
        screenX = worldX - mainPlayer.getWorldX() + mainPlayer.getScreenX();
        screenY = worldY - mainPlayer.getWorldY() + mainPlayer.getScreenY();
        super.draw(g2);
        weapon.draw(g2, directionFace, screenX, screenY, (int) mouseX, (int) mouseY, UpdateManager.tick);
    }

    public void updateFromInputData(PlayerInput playerInput) {
        this.worldX = playerInput.x();
        this.worldY = playerInput.y();
        this.direction = playerInput.direction();
        this.directionFace = playerInput.directionFace();
        this.walkAnimNum = playerInput.walkAnimNum();
        this.mouseX = playerInput.mouseX();
        this.mouseY = playerInput.mouseY();

        if (playerInput.shooting() && shootLock) {
            weapon.triggerBlast(UpdateManager.tick);
            shootLock = false;
        } else if (!playerInput.shooting() && !shootLock) {
            shootLock = true;
        }
    }

    public String getName() {
        return name;
    }
}
