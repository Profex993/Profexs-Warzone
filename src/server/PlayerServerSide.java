package server;

import shared.Constants;
import shared.PlayerInputToServer;

public class PlayerServerSide {
    private final String id;
    private final String playerModel;
    private int worldX = 0, worldY = 0, walkCounter, idleCounter, walkAnimNum = 1;
    private String direction = "down", directionFace;

    public PlayerServerSide(String id, String playerModel) {
        this.id = id;
        this.playerModel = playerModel;
    }

    public void updateFromPlayerInput(PlayerInputToServer input) {
        if (input.up()) {
            System.out.println("up");
            worldY -= Constants.playerSpeed;
            walkCounter++;
            idleCounter = 0;
        } else if (input.down()) {
            System.out.println("down");
            worldY += Constants.playerSpeed;
            walkCounter++;
            idleCounter = 0;
        } else if (input.left()) {
            System.out.println("left");
            worldX -= Constants.playerSpeed;
            walkCounter++;
            idleCounter = 0;
        } else if (input.right()) {
            System.out.println("right");
            worldX += Constants.playerSpeed;
            walkCounter++;
            idleCounter = 0;
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
}
