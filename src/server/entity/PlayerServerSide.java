package server.entity;

import server.ServerUpdateManager;
import shared.ConstantsShared;
import shared.packets.PlayerInputToServer;
import shared.weapon.Weapon_AK;
import shared.weapon.Weapon_Core;
import shared.weapon.Weapon_Makarov;
import shared.weapon.abstracts.Weapon;
import shared.weapon.abstracts.WeaponGenerator;

import java.awt.*;

public class PlayerServerSide {
    private final ServerUpdateManager updateManager;
    private String id, playerModel;
    private int worldX = 0, worldY = 0, health = 100, kills, deaths, respawnDelay;
    private double rotation = 0;
    private String direction = "down", directionFace, killedBy = "";
    private boolean shootLock = true, shooting = false, reloadTrigger = false, walking, death;
    private final Rectangle solidArea;
    private Weapon_Core weapon = Weapon_AK.getServerSideWeapon();

    public PlayerServerSide(ServerUpdateManager updateManager) {
        this.updateManager = updateManager;
        solidArea = new Rectangle(worldX, worldY, ConstantsShared.playerWidth, ConstantsShared.playerHeight);
    }

    public void updateFromPlayerInput(PlayerInputToServer input) {
        if (!death) {
            if (input.up()) {
                worldY -= ConstantsShared.playerSpeed;
                walking = true;
            } else if (input.down()) {
                worldY += ConstantsShared.playerSpeed;
                walking = true;
            } else if (input.left()) {
                worldX -= ConstantsShared.playerSpeed;
                walking = true;
            } else if (input.right()) {
                worldX += ConstantsShared.playerSpeed;
                walking = true;
            } else {
                walking = false;
            }

            solidArea.x = worldX;
            solidArea.y = worldY;

            if (input.up()) {
                direction = "up";
            } else if (input.down()) {
                direction = "down";
            } else if (input.left()) {
                direction = "left";
            } else if (input.right()) {
                direction = "right";
            }

            rotation = Math.atan2(input.mouseY() - (input.screenY() + (double) solidArea.width / 2), input.mouseX() - (input.screenX() + (double) solidArea.width / 2));
            if (rotation >= -Math.PI / 4 && rotation < Math.PI / 4) {
                directionFace = "right";
            } else if (rotation >= Math.PI / 4 && rotation < 3 * Math.PI / 4) {
                directionFace = "down";
            } else if (rotation >= -3 * Math.PI / 4 && rotation < -Math.PI / 4) {
                directionFace = "up";
            } else {
                directionFace = "left";
            }

            if (input.leftCLick()) {
                if (weapon.isAutomatic()) {
                    weapon.shoot(updateManager.getProjectileList(), worldX, worldY, directionFace, this, updateManager.getTick(),
                            rotation);
                    shooting = true;
                } else {
                    if (shootLock) {
                        shooting = true;
                        shootLock = false;
                        weapon.shoot(updateManager.getProjectileList(), worldX, worldY, directionFace, this, updateManager.getTick(),
                                rotation);
                    }
                }
            } else if (!shootLock || shooting) {
                shootLock = true;
                shooting = false;
            }

            if (input.reload()) {
                weapon.triggerReload(updateManager.getTick());
                reloadTrigger = true;
            } else if (weapon.isReloading()) {
                reloadTrigger = false;
                weapon.reload(updateManager.getTick());
            }
        }
    }

    public void update() {
        if (death) {
            respawn();
        }
    }

    public void setInitData(String id, String playerModel) {
        this.id = id;
        this.playerModel = playerModel;
    }

    public void changeWeapon(Class<? extends Weapon> weaponClass) {
        this.weapon = WeaponGenerator.getServerSideWeapon(weaponClass);
    }

    public void removeHealth(int remove, PlayerServerSide enemyPlayer) {
        health -= remove;
        if (health <= 0 && !death) {
            death();
            enemyPlayer.addKill();
            killedBy = enemyPlayer.getId();
        }
    }

    private void death() {
        health = 0;
        death = true;
        deaths++;
        respawnDelay = updateManager.getTick() + 600;
    }

    private void respawn() {
        if (updateManager.getTick() >= respawnDelay) {
            health = 100;
            death = false;
            killedBy = "";
            worldX = 0;
            worldY = 0;
            changeWeapon(Weapon_Makarov.class);
        }
    }

    private void resetPlayer() {
        deaths = 0;
        kills = 0;
        respawn();
    }

    public void addKill() {
        kills++;
    }

    public String getId() {
        return id;
    }

    public String getPlayerModel() {
        return playerModel;
    }

    @Override
    public String toString() {
        return "id: " + id;
    }

    public int getWorldX() {
        return worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    public String getDirectionFace() {
        return directionFace;
    }

    public boolean isShooting() {
        return shooting;
    }

    public Rectangle getSolidArea() {
        return solidArea;
    }

    public boolean isWeaponReloading() {
        return reloadTrigger;
    }

    public String getWeaponName() {
        return weapon.getName();
    }

    public boolean isWalking() {
        return walking;
    }

    public int getHealth() {
        return health;
    }

    public boolean isDeath() {
        return death;
    }

    public int getKills() {
        return kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public String getKilledBy() {
        return killedBy;
    }

    public double getRotation() {
        return rotation;
    }
}
