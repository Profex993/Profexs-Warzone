package server;

import server.entity.PlayerServerSide;
import server.entity.ProjectileServerSide;
import shared.ConstantsShared;
import shared.MapGenerator;
import shared.object.objectClasses.MapObject;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * class for checking collision
 */
public class CollisionManager {
    private final int maxWorldColum = ConstantsShared.MAX_WORLD_WIDTH;
    private final int maxWorldRow = ConstantsShared.MAX_WORLD_HEIGHT;
    private final int[][] mapNumbers;
    private final boolean[] tiles;
    private final ArrayList<MapObject> mapObjectList;

    /**
     *
     * @param mapObjectList ArrayList of MapObjects
     */
    public CollisionManager(ArrayList<MapObject> mapObjectList) {
        this.mapObjectList = mapObjectList;
        mapNumbers = new int[maxWorldColum][maxWorldRow];
        tiles = MapGenerator.getTileSetCollision();
    }

    /**
     * load the map from map file
     * @param mapNum current map number
     * @throws IOException if map cant be read
     */
    public void loadMap(int mapNum) throws IOException {
        BufferedReader br = MapGenerator.getMap(mapNum);

        int col = 0;
        int row = 0;
        while (col < maxWorldColum && row < maxWorldRow) {
            String line = br.readLine();

            while (col < maxWorldColum) {
                String[] numbers = line.split(" ");
                int number = Integer.parseInt(numbers[col]);
                mapNumbers[col][row] = number;
                col++;
            }
            if (col == maxWorldColum) {
                col = 0;
                row++;
            }
        }
        br.close();
    }

    /**
     * check collision with player and tile
     * @param player PlayerServerSide to check collision with
     * @return returns false if collision
     */
    public boolean checkTile(PlayerServerSide player) {
        int leftX = player.getSolidArea().x + 4;
        int rightX = player.getSolidArea().x + player.getSolidArea().width - 4;
        int topY = player.getSolidArea().y + 26;
        int bottomY = player.getSolidArea().y + player.getSolidArea().height;

        int tileSize = ConstantsShared.TILE_SIZE;
        int leftCol = leftX / tileSize;
        int rightCol = rightX / tileSize;
        int topRow = topY / tileSize;
        int bottomRow = bottomY / tileSize;

        try {
            switch (player.getDirection()) {
                case "up" -> {
                    topRow = (topY - ConstantsServer.PLAYER_SPEED) / tileSize;
                    if (tiles[mapNumbers[leftCol][topRow]] || tiles[mapNumbers[rightCol][topRow]]) {
                        return false;
                    }
                }
                case "down" -> {
                    bottomRow = (bottomY + ConstantsServer.PLAYER_SPEED) / tileSize;
                    if (tiles[mapNumbers[leftCol][bottomRow]] || tiles[mapNumbers[rightCol][bottomRow]]) {
                        return false;
                    }
                }
                case "left" -> {
                    leftCol = (leftX - ConstantsServer.PLAYER_SPEED) / tileSize;
                    if (tiles[mapNumbers[leftCol][topRow]] || tiles[mapNumbers[leftCol][bottomRow]]) {
                        return false;
                    }
                }
                case "right" -> {
                    rightCol = (rightX + ConstantsServer.PLAYER_SPEED) / tileSize;
                    if (tiles[mapNumbers[rightCol][topRow]] || tiles[mapNumbers[rightCol][bottomRow]]) {
                        return false;
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    /**
     * check collision with tile and projectile
     * @param solidArea rectangle solid area of ProjectileServerSide
     * @return true if collision
     */
    public boolean checkTileProjectile(Rectangle solidArea) {
        int tileSize = ConstantsShared.TILE_SIZE;
        int tileX = solidArea.x / tileSize;
        int tileY = solidArea.y / tileSize;
        if (tileX >= 0 && tileX < mapNumbers.length && tileY >= 0 && tileY < mapNumbers[0].length) {
            return tiles[mapNumbers[tileX][tileY]];
        }
        return false;
    }

    /**
     * check collision with player and object on map
     * @param player PlayerServerSide to check collision with
     * @return return false if collision
     */
    public boolean checkObject(PlayerServerSide player) {
        for (MapObject mapObject : mapObjectList) {
            if (mapObject.isCollision()) {
                Rectangle modifiedPlayerSolidArea = new Rectangle(player.getWorldX(), player.getWorldY(),
                        ConstantsShared.PLAYER_WIDTH, ConstantsShared.PLAYER_HEIGHT);
                switch (player.getDirection()) {
                    case "up" -> modifiedPlayerSolidArea.y -= ConstantsServer.PLAYER_SPEED;
                    case "down" -> modifiedPlayerSolidArea.y += ConstantsServer.PLAYER_SPEED;
                    case "left" -> modifiedPlayerSolidArea.x -= ConstantsServer.PLAYER_SPEED;
                    case "right" -> modifiedPlayerSolidArea.x += ConstantsServer.PLAYER_SPEED;
                }
                if (modifiedPlayerSolidArea.intersects(mapObject.getSolidArea())) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * check collision with projectile and object
     * @param projectile ProjectileServerSide to check collision with
     * @return returns true if collision
     */
    public boolean checkObjectProjectile(ProjectileServerSide projectile) {
        for (MapObject mapObject : mapObjectList) {
            if (mapObject.isCollision() && mapObject.getSolidArea().intersects(projectile.getSolidArea())) {
                return true;
            }
        }
        return false;
    }
}
