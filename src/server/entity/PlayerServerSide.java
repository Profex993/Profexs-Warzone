package server.entity;

import server.CollisionManager;
import server.ServerCore;
import server.ServerUpdateManager;
import server.enums.ServerMatchState;
import shared.ConstantsShared;
import shared.ObjectGenerator;
import shared.object.objectClasses.Object;
import shared.packets.Packet_AddPlayer;
import shared.packets.Packet_PlayerInputToServer;
import shared.weapon.Weapon_PM;
import shared.weapon.Weapon_SKS;
import shared.weapon.weaponClasses.Weapon;
import shared.weapon.weaponClasses.WeaponGenerator;
import shared.weapon.weaponClasses.Weapon_Core;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class PlayerServerSide {
    private final ServerUpdateManager updateManager;
    private final CollisionManager collisionManager;
    private final ArrayList<Object> objectList;
    private final ServerCore core;
    private String name, playerModel;
    private int worldX = 100, worldY = 500, health = 100, kills, deaths, respawnDelay;
    private double rotation = 0;
    private String direction = "down", directionFace, killedBy = "";
    private boolean shootLock = true, shooting = false, reloadTrigger = false, walking, death, interactTrigger = true;
    private final Rectangle solidArea;
    private Weapon_Core weapon = Weapon_SKS.getServerSideWeapon();

    public PlayerServerSide(ServerUpdateManager updateManager, CollisionManager collisionManager, ArrayList<Object> objectList,
                            ServerCore core) {
        this.updateManager = updateManager;
        solidArea = new Rectangle(worldX, worldY, ConstantsShared.PLAYER_WIDTH, ConstantsShared.PLAYER_HEIGHT);
        this.collisionManager = collisionManager;
        this.objectList = objectList;
        this.core = core;
        try {
            collisionManager.loadMap(core.getMAP_NUMBER());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateFromPlayerInput(Packet_PlayerInputToServer input) {
        if (!death) {
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

            if (core.getMatchState() == ServerMatchState.MATCH) {
                if (input.up()) {
                    direction = "up";
                } else if (input.down()) {
                    direction = "down";
                } else if (input.left()) {
                    direction = "left";
                } else if (input.right()) {
                    direction = "right";
                }

                if (collisionManager.checkTile(this) && collisionManager.checkObject(this)) {
                    if (input.up()) {
                        worldY -= ConstantsShared.PLAYER_SPEED;
                        walking = true;
                    } else if (input.down()) {
                        worldY += ConstantsShared.PLAYER_SPEED;
                        walking = true;
                    } else if (input.left()) {
                        worldX -= ConstantsShared.PLAYER_SPEED;
                        walking = true;
                    } else if (input.right()) {
                        worldX += ConstantsShared.PLAYER_SPEED;
                        walking = true;
                    } else {
                        walking = false;
                    }
                } else {
                    walking = false;
                }

                solidArea.x = worldX;
                solidArea.y = worldY;

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

                if (input.rightClick() && interactTrigger) {
                    for (int i = 0; i < objectList.size(); i++) {
                        objectList.get(i).tryInteracting(this, input, core);
                        interactTrigger = false;
                    }
                } else if (!input.rightClick() && !interactTrigger) {
                    interactTrigger = true;
                }
            }
        }
    }

    public void update() {
        if (death && core.getMatchState() == ServerMatchState.MATCH) {
            respawn();
        }
    }

    public void setInitData(Packet_AddPlayer data) {
        this.name = data.name();
        this.playerModel = data.playerModel();
    }

    public void changeWeapon(Class<? extends Weapon> weaponClass) {
        try {
            this.weapon = WeaponGenerator.getServerSideWeapon(weaponClass);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void removeHealth(int remove, PlayerServerSide enemyPlayer) {
        health -= remove;
        if (health <= 0 && !death) {
            death();
            enemyPlayer.addKill();
            killedBy = enemyPlayer.getName();
        }
    }

    private void death() {
        health = 0;
        death = true;
        deaths++;
        respawnDelay = updateManager.getTick() + 600;
        try {
            core.addObject(ObjectGenerator.getObjectByWeapon("Object_Weapon_" + weapon.getName(), worldX, worldY));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void respawn() {
        if (updateManager.getTick() >= respawnDelay) {
            health = 100;
            death = false;
            killedBy = "";
            worldX = 500;
            worldY = 500;
            changeWeapon(Weapon_PM.class);
        }
    }

    public void resetPlayer() {
        deaths = 0;
        kills = 0;
        respawn();
    }

    public boolean isWalking() {
        // prevent walking after match is over
        if (core.getMatchState() == ServerMatchState.MATCH) {
            return walking;
        } else {
            return false;
        }
    }

    public void addKill() {
        kills++;
    }

    public String getName() {
        return name;
    }

    public String getPlayerModel() {
        return playerModel;
    }

    @Override
    public String toString() {
        return "id: " + name;
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
