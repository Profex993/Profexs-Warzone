package client.entity;

import client.clientMain.GamePanel;
import client.clientMain.KeyHandler;
import client.clientMain.MouseHandler;
import client.clientMain.UpdateManager;
import shared.packets.ServerOutputToClient;
import shared.weapon.abstracts.Weapon;
import shared.weapon.abstracts.WeaponGenerator;

import java.awt.*;

public class MainPlayer extends Entity {
    private final MouseHandler mouseHandler;
    private final KeyHandler keyHandler;
    private boolean singleFireLock = true;
    private int health;
    private String killedBy;

    public MainPlayer(String name, String playerModel, int worldX, int worldY, MouseHandler mouseHandler, KeyHandler keyHandler) {
        super(name, playerModel);
        screenX = GamePanel.getScreenWidth() / 2 - (48 / 2);
        screenY = GamePanel.getScreenHeight() / 2 - (48 / 2);
        this.worldX = worldX;
        this.worldY = worldY;
        this.mouseHandler = mouseHandler;
        this.keyHandler = keyHandler;
    }

    public void updateFromServerInput(ServerOutputToClient input) {
        this.health = input.health();
        if (death && !input.death()) {
            death = false;
        } else if (!death && input.death()) {
            triggerDeath(input);
            death = true;
        }
        kills = input.kills();
        deaths = input.deaths();
        this.worldX = input.x();
        this.worldY = input.y();
        this.directionFace = input.directionFace();

        if (weapon == null || !weapon.getName().equals(input.weapon())) {
            weapon = WeaponGenerator.getWeaponByName(input.weapon());
        }

        if (input.walking()) {
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

    public void update() {
        if (!death) {
            if (mouseHandler.isShooting()) {
                if (!weapon.isAutomatic() && singleFireLock) {
                    weapon.triggerBlast(UpdateManager.tick);
                    singleFireLock = false;
                } else if (weapon.isAutomatic()) {
                    weapon.triggerBlast(UpdateManager.tick);
                }
            } else if (!singleFireLock) {
                singleFireLock = true;
            }

            if (keyHandler.reload) {
                weapon.triggerReload(UpdateManager.tick);
            } else if (weapon.isReloading()) {
                weapon.reload(UpdateManager.tick);
            }
        }
    }

    public void draw(Graphics2D g2) {
        if (death) {
            g2.drawImage(deathImg, screenX, screenY, null);
        } else {
            if (weaponDrawFirst && weapon != null) {
                weapon.draw(g2, directionFace, screenX, screenY, (int) mouseHandler.getX(), (int) mouseHandler.getY(), UpdateManager.tick);
            }
            super.draw(g2);
            if (!weaponDrawFirst && weapon != null) {
                weapon.draw(g2, directionFace, screenX, screenY, (int) mouseHandler.getX(), (int) mouseHandler.getY(), UpdateManager.tick);
            }
        }
    }

    private void triggerDeath(ServerOutputToClient input) {
        killedBy = input.killedBy();
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
    public int getHealth() {
        return health;
    }
    public Weapon getWeapon() {
        return weapon;
    }

    public String getKilledBy() {
        return killedBy;
    }
}
