package client.entity;

import client.clientMain.UpdateManager;
import shared.PlayerInput;
import shared.weapon.abstracts.WeaponGenerator;

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
        if (weaponDrawFirst && weapon != null) {
            weapon.draw(g2, directionFace, screenX, screenY, (int) mouseX, (int) mouseY, UpdateManager.tick);
        }
        super.draw(g2);
        if (!weaponDrawFirst && weapon != null) {
            weapon.draw(g2, directionFace, screenX, screenY, (int) mouseX, (int) mouseY, UpdateManager.tick);
        }
    }

    public void updateFromInputData(PlayerInput playerInput) {
        this.worldX = playerInput.x();
        this.worldY = playerInput.y();
        this.directionFace = playerInput.directionFace();
        this.mouseX = playerInput.mouseX();
        this.mouseY = playerInput.mouseY();
        if (weapon == null || !weapon.getName().equals(playerInput.weapon())) {
            this.weapon = WeaponGenerator.getWeaponByName(playerInput.weapon());
        }

        if (playerInput.shooting()) {
            if (!weapon.isAutomatic()) {
                if (shootLock) {
                    weapon.triggerBlast(UpdateManager.tick);
                    shootLock = false;
                }
            } else {
                weapon.triggerBlast(UpdateManager.tick);
            }
        } else if (!shootLock) {
            shootLock = true;
        }

        if (playerInput.reloading()) {
            weapon.triggerReload(UpdateManager.tick);
        } else if (weapon.isReloading()) {
            weapon.reload(UpdateManager.tick);
        }

        if (playerInput.walking()) {
            walkCounter++;
        } else {
            idleCounter++;
        }

        if (walkCounter > 20) {
            if (walkAnimNum == 1) {
                walkAnimNum = 2;
            } else if (walkAnimNum == 2) {
                walkAnimNum = 3;
            } else if (walkAnimNum == 3) {
                walkAnimNum = 4;
            } else {
                walkAnimNum = 1;
            }
        }

        if (idleCounter >= 10) {
            walkAnimNum = 2;
            idleCounter = 0;
        }

        if (walkCounter > 20) {
            walkCounter = 0;
        }
    }

    public String getName() {
        return name;
    }
}
