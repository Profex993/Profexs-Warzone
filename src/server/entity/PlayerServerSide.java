package server.entity;

import server.*;
import server.enums.ServerMatchState;
import shared.ConstantsShared;
import shared.ObjectGenerator;
import shared.object.objectClasses.MapObject;
import shared.object.objectClasses.MapObject_Weapon;
import shared.packets.Packet_AddPlayerToServer;
import shared.packets.Packet_PlayerInputToServer;
import shared.weapon.weaponClasses.Weapon;
import shared.weapon.weaponClasses.WeaponGenerator;
import shared.weapon.weaponClasses.Weapon_Core;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * server side player class
 */
public class PlayerServerSide {
    private final ServerUpdateManager updateManager;
    private final CollisionManager collisionManager;
    private final ArrayList<MapObject> mapObjectList;
    private final ArrayList<SpawnLocation> spawnLocations;
    private final Random random;
    private final ServerCore core;
    private String name, playerModel;
    private int worldX, worldY, health, kills, deaths, respawnDelay;
    private double rotation = 0;
    private String direction = "down", directionFace, killedBy = "";
    private boolean shootLock = true, shooting = false, reloadTrigger = false, walking, death, interactTrigger = true;
    private final Rectangle solidArea;
    private Weapon_Core weapon;

    /**
     *
     * @param updateManager ServerUpdateManager for setting delays and getting projectileList
     * @param collisionManager CollisionManager for checking collisions
     * @param mapObjectList Arraylist of MapObjects for checking interactions
     * @param core ServerCore for checking match state, adding objects on map
     * @param spawnLocations Arraylist of playerSpawnLocations for respawning
     * @param random Random for random generations
     */
    public PlayerServerSide(ServerUpdateManager updateManager, CollisionManager collisionManager, ArrayList<MapObject> mapObjectList,
                            ServerCore core, ArrayList<SpawnLocation> spawnLocations, Random random) {
        this.updateManager = updateManager;
        solidArea = new Rectangle(worldX, worldY, ConstantsShared.PLAYER_WIDTH, ConstantsShared.PLAYER_HEIGHT);
        this.collisionManager = collisionManager;
        this.mapObjectList = mapObjectList;
        this.core = core;
        this.spawnLocations = spawnLocations;
        this.random = random;

        respawn();
    }

    /**
     * update player from input from client
     * @param input Package_PlayerInputToServer from client
     */
    public void updateFromPlayerInput(Packet_PlayerInputToServer input) {
        if (!death) {
            //player cant move at the end of the match
            if (core.getMatchState() == ServerMatchState.MATCH) {
                //mouse rotation
                rotation = Math.atan2(input.mouseY() - (input.screenY() + (double) solidArea.width / 2),
                        input.mouseX() - (input.screenX() + (double) solidArea.width / 2));
                if (rotation >= -Math.PI / 4 && rotation < Math.PI / 4) {
                    directionFace = "right";
                } else if (rotation >= Math.PI / 4 && rotation < 3 * Math.PI / 4) {
                    directionFace = "down";
                } else if (rotation >= -3 * Math.PI / 4 && rotation < -Math.PI / 4) {
                    directionFace = "up";
                } else {
                    directionFace = "left";
                }

                if (input.up()) {
                    direction = "up";
                } else if (input.down()) {
                    direction = "down";
                } else if (input.left()) {
                    direction = "left";
                } else if (input.right()) {
                    direction = "right";
                }

                //check if collision
                if (collisionManager.checkTile(this) && collisionManager.checkObject(this)) {
                    if (input.up()) {
                        worldY -= ConstantsServer.PLAYER_SPEED;
                        walking = true;
                    } else if (input.down()) {
                        worldY += ConstantsServer.PLAYER_SPEED;
                        walking = true;
                    } else if (input.left()) {
                        worldX -= ConstantsServer.PLAYER_SPEED;
                        walking = true;
                    } else if (input.right()) {
                        worldX += ConstantsServer.PLAYER_SPEED;
                        walking = true;
                    } else {
                        walking = false;
                    }
                } else {
                    walking = false;
                }

                //update solid area
                solidArea.x = worldX;
                solidArea.y = worldY;

                //weapon
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

                //object interacting
                if (input.rightClick() && interactTrigger) {
                    for (int i = 0; i < mapObjectList.size(); i++) {
                        mapObjectList.get(i).tryInteracting(this, input, core);
                        interactTrigger = false;
                    }
                } else if (!input.rightClick() && !interactTrigger) {
                    interactTrigger = true;
                }
            }
        }
    }

    /**
     * update each tick
     */
    public void update() {
        // check if enough time has past since death
        if (death && core.getMatchState() == ServerMatchState.MATCH) {
            respawn();
        }
    }

    /**
     * sets players name and player model
     * @param data Packet_AddPlayerToServer
     */
    public void setInitialData(Packet_AddPlayerToServer data) {
        this.name = data.name();
        this.playerModel = data.playerModel();
    }

    /**
     * change weapon of player
     * @param weaponClass Class which extends weapon to change it to
     */
    public void changeWeapon(Class<? extends Weapon> weaponClass) {
        try {
            this.weapon = WeaponGenerator.getServerSideWeapon(weaponClass);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * handle damage
     * @param remove damage int
     * @param enemyPlayer player who dealt damage
     */
    public void removeHealth(int remove, PlayerServerSide enemyPlayer) {
        health -= remove;
        if (health <= 0 && !death) {
            death();
            enemyPlayer.addKill();
            killedBy = enemyPlayer.getName();
        }
    }

    /**
     * process player death
     */
    private void death() {
        health = 0;
        death = true;
        deaths++;

        //set time until respawn
        respawnDelay = updateManager.getTick() + ConstantsServer.PLAYER_RESPAWN_TIME;

        //drop players weapon
        try {
            core.addObject(ObjectGenerator.getObjectByName(weapon.getAssociatedWeaponMapObject().getSimpleName(), worldX, worldY));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * respawn player after enough of time
     */
    private void respawn() {
        if (updateManager.getTick() >= respawnDelay) {
            health = 100;
            death = false;
            killedBy = "";
            SpawnLocation location = spawnLocations.get(random.nextInt(spawnLocations.size()));
            worldX = location.x();
            worldY = location.y();
            changeWeapon(ConstantsServer.DEFAULT_WEAPON);
        }
    }

    public void resetPlayer() {
        deaths = 0;
        kills = 0;
        respawn();
    }

    public boolean isWalking() {
        // prevent walking after match is over
        if (core.getMatchState() == ServerMatchState.MATCH && !death) {
            return walking;
        } else {
            return false;
        }
    }

    public boolean isShooting() {
        // prevent shooting after match is over
        if (core.getMatchState() == ServerMatchState.MATCH && !death) {
            return shooting;
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

    public Rectangle getSolidArea() {
        return solidArea;
    }

    public boolean isWeaponReloading() {
        return reloadTrigger;
    }

    public String getWeaponName() {
        return weapon.getName();
    }

    public Class<? extends MapObject_Weapon> getWeaponAssociatedMapObject() {
        return weapon.getAssociatedWeaponMapObject();
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
