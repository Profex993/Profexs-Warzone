package client.entity;

import client.clientMain.ClientMain;
import client.clientMain.UpdateManager;
import client.clientMain.sound.SoundManager;
import shared.packets.Packet_PlayerUpdateInput;
import shared.weaponClasses.WeaponGenerator;

import java.awt.*;

public class Player extends Entity {
    private final MainPlayer mainPlayer;
    private boolean shootLock = true;

    public Player(MainPlayer mainPlayer, String name, String playerModel) {
        super(name, playerModel);
        this.mainPlayer = mainPlayer;
    }

    public void draw(Graphics2D g2) {
        screenX = worldX - mainPlayer.getWorldX() + mainPlayer.getScreenX();
        screenY = worldY - mainPlayer.getWorldY() + mainPlayer.getScreenY();
        if (death) {
            g2.drawImage(deathImg, screenX, screenY, 70, 60, null);
        } else {
            if (weaponDrawFirst && weapon != null) {
                weapon.draw(g2, directionFace, UpdateManager.tick, rotation, screenX, screenY);
            }
            super.draw(g2);
            if (!weaponDrawFirst && weapon != null) {
                weapon.draw(g2, directionFace, UpdateManager.tick, rotation, screenX, screenY);
            }
        }
    }

    public void updateFromInputData(Packet_PlayerUpdateInput input) {
        this.death = input.death();
        this.worldX = input.x();
        this.worldY = input.y();
        this.rotation = input.rotation();
        this.directionFace = input.directionFace();

        this.kills = input.kills();
        this.deaths = input.deaths();
        if (weapon == null || !weapon.getName().equals(input.weapon())) {
            try {
                this.weapon = WeaponGenerator.getWeaponByName(input.weapon());
            } catch (Exception e) {
                ClientMain.closeSocket();
                throw new RuntimeException(e);
            }
        }

        if (input.shooting()) {
            if (!weapon.isAutomatic()) {
                if (shootLock) {
                    if (weapon.triggerBlast(UpdateManager.tick)) {
                        SoundManager.playSound(mainPlayer, weapon.getFireSound(), worldX, worldY, 5000);
                    }
                    shootLock = false;
                }
            } else {
                if (weapon.triggerBlast(UpdateManager.tick)) {
                    SoundManager.playSound(mainPlayer, weapon.getFireSound(), worldX, worldY, 5000);
                }
            }
        } else if (!shootLock) {
            shootLock = true;
        }

        if (input.reloading()) {
            if (weapon.triggerReload(UpdateManager.tick)) {
                SoundManager.playSound(mainPlayer, weapon.getReloadSound(), worldX, worldY, 1000);
            }
        } else if (weapon.isReloading()) {
            weapon.reload(UpdateManager.tick);
        }

        if (input.walking()) {
            walkCounter++;
        } else {
            idleCounter++;
        }

        if (walkCounter > 20) {
            if (walkAnimNum == 1) {
                walkAnimNum = 2;
                SoundManager.playSound(mainPlayer, SoundManager.walk1, worldX, worldY, 1200);
            } else if (walkAnimNum == 2) {
                walkAnimNum = 3;
            } else if (walkAnimNum == 3) {
                walkAnimNum = 4;
                SoundManager.playSound(mainPlayer, SoundManager.walk2, worldX, worldY, 1200);
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
}
