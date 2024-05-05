package server.entity;

import server.CollisionManager;
import server.ServerCore;
import server.ServerUpdateManager;
import shared.ConstantsShared;
import shared.objects.Object;
import shared.packets.PlayerInitialData;
import shared.packets.PlayerInputToServer;
import shared.weapon.Weapon_AK;
import shared.weapon.Weapon_Core;
import shared.weapon.Weapon_Makarov;
import shared.weapon.abstracts.Weapon;
import shared.weapon.abstracts.WeaponGenerator;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class PlayerServerSide {
    private final ServerUpdateManager updateManager;
    private final CollisionManager collisionManager;
    private String id, playerModel;
    private int worldX = 100, worldY = 100, health = 100, kills, deaths, respawnDelay;
    private double rotation = 0;
    private String direction = "down", directionFace, killedBy = "";
    private boolean shootLock = true, shooting = false, reloadTrigger = false, walking, death;
    private final Rectangle solidArea;
    private Weapon_Core weapon = Weapon_AK.getServerSideWeapon();
    private final ArrayList<Object> objectList;

    public PlayerServerSide(ServerUpdateManager updateManager, CollisionManager collisionManager, ArrayList<Object> objectList) {
        this.updateManager = updateManager;
        solidArea = new Rectangle(worldX, worldY, ConstantsShared.playerWidth, ConstantsShared.playerHeight);
        this.collisionManager = collisionManager;
        this.objectList = objectList;

        try {
            collisionManager.loadMap(ServerCore.mapNumber);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateFromPlayerInput(PlayerInputToServer input) {
        if (!death) {
            if (input.up()) {
                direction = "up";
            } else if (input.down()) {
                direction = "down";
            } else if (input.left()) {
                direction = "left";
            } else if (input.right()) {
                direction = "right";
            }

            if (collisionManager.checkTile(this)) {
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
            } else {
                walking = false;
            }

            solidArea.x = worldX;
            solidArea.y = worldY;

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
                            rotation, collisionManager);
                    shooting = true;
                } else {
                    if (shootLock) {
                        shooting = true;
                        shootLock = false;
                        weapon.shoot(updateManager.getProjectileList(), worldX, worldY, directionFace, this, updateManager.getTick(),
                                rotation, collisionManager);
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

            for (Object object : objectList) {
                object.update(this, input);
            }
        }
    }

    public void update() {
        if (death) {
            respawn();
        }
    }

    public void setInitData(PlayerInitialData data) {
        this.id = data.name();
        this.playerModel = data.playerModel();
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
            worldX = 100;
            worldY = 100;
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

    public String getDirection() {
        return direction;
    }
}
