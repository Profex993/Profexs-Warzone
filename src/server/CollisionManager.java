package server;

import server.entity.PlayerServerSide;
import server.entity.ProjectileServerSide;
import shared.ConstantsShared;
import shared.MapGenerator;
import shared.object.objectClasses.Object;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class CollisionManager {
    private final int maxWorldColum = ConstantsShared.MAX_WORLD_WIDTH;
    private final int maxWorldRow = ConstantsShared.MAX_WORLD_HEIGHT;
    private final int[][] mapTileNumber;
    private final boolean[] tiles;
    private final ArrayList<Object> objectList;

    public CollisionManager(ArrayList<Object> objectList) {
        this.objectList = objectList;
        mapTileNumber = new int[maxWorldColum][maxWorldRow];
        tiles = MapGenerator.getTileSetCollision();
    }

    public void loadMap(int mapNum) throws IOException {
        BufferedReader br = MapGenerator.getMap(mapNum);

        int col = 0;
        int row = 0;
        while (col < maxWorldColum && row < maxWorldRow) {
            String line = br.readLine();

            while (col < maxWorldColum) {
                String[] numbers = line.split(" ");
                int number = Integer.parseInt(numbers[col]);
                mapTileNumber[col][row] = number;
                col++;
            }
            if (col == maxWorldColum) {
                col = 0;
                row++;
            }
        }
        br.close();
    }

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
                    if (tiles[mapTileNumber[leftCol][topRow]] || tiles[mapTileNumber[rightCol][topRow]]) {
                        return false;
                    }
                }
                case "down" -> {
                    bottomRow = (bottomY + ConstantsServer.PLAYER_SPEED) / tileSize;
                    if (tiles[mapTileNumber[leftCol][bottomRow]] || tiles[mapTileNumber[rightCol][bottomRow]]) {
                        return false;
                    }
                }
                case "left" -> {
                    leftCol = (leftX - ConstantsServer.PLAYER_SPEED) / tileSize;
                    if (tiles[mapTileNumber[leftCol][topRow]] || tiles[mapTileNumber[leftCol][bottomRow]]) {
                        return false;
                    }
                }
                case "right" -> {
                    rightCol = (rightX + ConstantsServer.PLAYER_SPEED) / tileSize;
                    if (tiles[mapTileNumber[rightCol][topRow]] || tiles[mapTileNumber[rightCol][bottomRow]]) {
                        return false;
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public boolean checkTileProjectile(Rectangle solidArea) {
        int tileSize = ConstantsShared.TILE_SIZE;
        int tileX = solidArea.x / tileSize;
        int tileY = solidArea.y / tileSize;
        if (tileX >= 0 && tileX < mapTileNumber.length && tileY >= 0 && tileY < mapTileNumber[0].length) {
            return tiles[mapTileNumber[tileX][tileY]];
        }
        return false;
    }

    public boolean checkObject(PlayerServerSide player) {
        for (Object object : objectList) {
            if (object.isCollision()) {
                Rectangle modifiedPlayerSolidArea = new Rectangle(player.getWorldX(), player.getWorldY(),
                        ConstantsShared.PLAYER_WIDTH, ConstantsShared.PLAYER_HEIGHT);
                switch (player.getDirection()) {
                    case "up" -> modifiedPlayerSolidArea.y -= ConstantsServer.PLAYER_SPEED;
                    case "down" -> modifiedPlayerSolidArea.y += ConstantsServer.PLAYER_SPEED;
                    case "left" -> modifiedPlayerSolidArea.x -= ConstantsServer.PLAYER_SPEED;
                    case "right" -> modifiedPlayerSolidArea.x += ConstantsServer.PLAYER_SPEED;
                }
                if (modifiedPlayerSolidArea.intersects(object.getSolidArea())) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkObjectProjectile(ProjectileServerSide projectile) {
        for (Object object : objectList) {
            if (object.isCollision() && object.getSolidArea().intersects(projectile.getSolidArea())) {
                return true;
            }
        }
        return false;
    }
}
