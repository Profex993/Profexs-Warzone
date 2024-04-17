package server.entity;

import server.ServerUpdateManager;
import shared.Constants;
import shared.PlayerInputToServer;
import shared.weapon.Weapon_Core;
import shared.weapon.Weapon_Sks;

import java.awt.*;

public class PlayerServerSide {
    private final ServerUpdateManager updateManager;
    private String id, playerModel;
    private int worldX = 0;
    private int worldY = 0;
    private int walkCounter;
    private int idleCounter;
    private int walkAnimNum = 1;
    private double mouseX = 0, mouseY = 0;
    private String direction = "down", directionFace;
    private boolean shootLock = true, shooting = false;
    private final Rectangle solidArea;
    private Weapon_Core weapon = Weapon_Sks.getServerSideWeapon();

    public PlayerServerSide(ServerUpdateManager updateManager) {
        this.updateManager = updateManager;
        solidArea = new Rectangle(worldX, worldY, Constants.playerWidth, Constants.playerHeight);
    }

    public void updateFromPlayerInput(PlayerInputToServer input) {
        mouseX = input.mouseX();
        mouseY = input.mouseY();

        if (input.up()) {
            worldY -= Constants.playerSpeed;
            walkCounter++;
            idleCounter = 0;
        } else if (input.down()) {
            worldY += Constants.playerSpeed;
            walkCounter++;
            idleCounter = 0;
        } else if (input.left()) {
            worldX -= Constants.playerSpeed;
            walkCounter++;
            idleCounter = 0;
        } else if (input.right()) {
            worldX += Constants.playerSpeed;
            walkCounter++;
            idleCounter = 0;
        } else {
            idleCounter++;
        }

        solidArea.x = worldX;
        solidArea.y = worldY;

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

        if (idleCounter == 10) {
            walkAnimNum = 2;
        }

        if (walkCounter > 20) {
            walkCounter = 0;
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

        double angle = Math.atan2(input.mouseY() - (input.screenY() + (double) 40 / 2), input.mouseX() - (input.screenX() + (double) 40 / 2));
        if (angle >= -Math.PI / 4 && angle < Math.PI / 4) {
            directionFace = "right";
        } else if (angle >= Math.PI / 4 && angle < 3 * Math.PI / 4) {
            directionFace = "down";
        } else if (angle >= -3 * Math.PI / 4 && angle < -Math.PI / 4) {
            directionFace = "up";
        } else {
            directionFace = "left";
        }

        if (input.leftCLick() && shootLock) {
            shooting = true;
            shootLock = false;
            weapon.shoot(updateManager.getProjectileList(), worldX, worldY, directionFace, (int) mouseX, (int) mouseY, id,
                    input.screenX(), input.screenY());
        } else if (!input.leftCLick() && !shootLock) {
            shootLock = true;
            shooting = false;
        }
    }

    public void setInitData(String id, String playerModel) {
        this.id = id;
        this.playerModel = playerModel;
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

    public String getDirection() {
        return direction;
    }

    public String getDirectionFace() {
        return directionFace;
    }

    public int getWalkAnimNum() {
        return walkAnimNum;
    }

    public double getMouseX() {
        return mouseX;
    }

    public double getMouseY() {
        return mouseY;
    }

    public boolean isShooting() {
        return shooting;
    }

    public Rectangle getSolidArea() {
        return solidArea;
    }
}
