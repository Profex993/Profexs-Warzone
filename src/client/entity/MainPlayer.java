package client.entity;

import client.clientMain.*;
import client.clientMain.sound.SoundManager;
import shared.packets.Packet_ServerOutputToClient;
import shared.weapon.weaponClasses.Weapon;
import shared.weapon.weaponClasses.WeaponGenerator;

import java.awt.*;

public class MainPlayer extends Entity {
    private final MouseHandler mouseHandler;
    private final KeyHandler keyHandler;
    private boolean singleFireLock = true;
    private int health;
    private String killedBy;

    public MainPlayer(String name, String playerModel, MouseHandler mouseHandler, KeyHandler keyHandler, GameCore core) {
        super(name, playerModel, core);
        screenX = GamePanel.getScreenWidth() / 2 - (48 / 2);
        screenY = GamePanel.getScreenHeight() / 2 - (48 / 2);
        this.mouseHandler = mouseHandler;
        this.keyHandler = keyHandler;
    }

    public void updateFromServerInput(Packet_ServerOutputToClient input) {
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
        this.solidArea.x = worldX;
        this.solidArea.y = worldY;
        this.directionFace = input.directionFace();

        if (weapon == null || !weapon.getName().equals(input.weapon())) {
            try {
                weapon = WeaponGenerator.getWeaponByName(input.weapon());
                SoundManager.playSound(SoundManager.switchWeapon);
            } catch (Exception e) {
                ClientMain.closeSocket();
                throw new RuntimeException(e);
            }
        }

        if (input.walking()) {
            walkCounter++;
        } else {
            idleCounter++;
        }

        if (walkCounter > 20) {
            if (walkAnimNum == 1) {
                walkAnimNum = 2;
                SoundManager.playSound(SoundManager.walk1);
            } else if (walkAnimNum == 2) {
                walkAnimNum = 3;
            } else if (walkAnimNum == 3) {
                walkAnimNum = 4;
                SoundManager.playSound(SoundManager.walk2);
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
                    if (weapon.triggerBlast(core.getTick())) {
                        SoundManager.playSound(weapon.getFireSound());
                    }
                    singleFireLock = false;
                } else if (weapon.isAutomatic()) {
                    if (weapon.triggerBlast(core.getTick())) {
                        SoundManager.playSound(weapon.getFireSound());
                    }
                }
            } else if (!singleFireLock) {
                singleFireLock = true;
            }

            if (keyHandler.reload) {
                if (weapon.triggerReload(core.getTick())) {
                    SoundManager.playSound(weapon.getReloadSound());
                }
            } else if (weapon.isReloading()) {
                weapon.reload(core.getTick());
            }
        }
    }

    public void draw(Graphics2D g2) {
        rotation = Math.atan2(mouseHandler.getY() - (screenY + (double) solidArea.width / 2),
                mouseHandler.getX() - (screenX + (double) solidArea.width / 2));
        super.draw(g2);
    }

    private void triggerDeath(Packet_ServerOutputToClient input) {
        killedBy = input.killedBy();
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
