package server;

import server.entity.PlayerServerSide;
import server.entity.ProjectileServerSide;
import shared.ConstantsShared;
import shared.MapGenerator;

import java.io.BufferedReader;
import java.io.IOException;

public class CollisionManager {
    private final int maxWorldColum = ConstantsShared.maxWorldWidth;
    private final int maxWorldRow = ConstantsShared.maxWorldHeight;
    private final int[][] mapTileNumber;
    private final boolean[] tiles;

    public CollisionManager() {
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

        int tileSize = ConstantsShared.tileSize;
        int leftCol = leftX / tileSize;
        int rightCol = rightX / tileSize;
        int topRow = topY / tileSize;
        int bottomRow = bottomY / tileSize;

        try {
            switch (player.getDirection()) {
                case "up" -> {
                    topRow = (topY - ConstantsShared.playerSpeed) / tileSize;
                    if (tiles[mapTileNumber[leftCol][topRow]] || tiles[mapTileNumber[rightCol][topRow]]) {
                        return false;
                    }
                }
                case "down" -> {
                    bottomRow = (bottomY + ConstantsShared.playerSpeed) / tileSize;
                    if (tiles[mapTileNumber[leftCol][bottomRow]] || tiles[mapTileNumber[rightCol][bottomRow]]) {
                        return false;
                    }
                }
                case "left" -> {
                    leftCol = (leftX - ConstantsShared.playerSpeed) / tileSize;
                    if (tiles[mapTileNumber[leftCol][topRow]] || tiles[mapTileNumber[leftCol][bottomRow]]) {
                        return false;
                    }
                }
                case "right" -> {
                    rightCol = (rightX + ConstantsShared.playerSpeed) / tileSize;
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

    public boolean checkTileProjectile(ProjectileServerSide projectile) {
        int tileSize = ConstantsShared.tileSize;
        int tileX = projectile.getSolidArea().x / tileSize;
        int tileY = projectile.getSolidArea().y / tileSize;
        if (tileX >= 0 && tileX < mapTileNumber.length && tileY >= 0 && tileY < mapTileNumber[0].length) {
            return tiles[mapTileNumber[tileX][tileY]];
        }
        return false;
    }
}