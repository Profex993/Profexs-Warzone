package client.clientMain;

import client.entity.PlayerMain;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

public class TileManager {
    private final PlayerMain player;
    private final Tile[] tiles;
    private final int[][] mapTileNumber;
    private final int maxWorldColum = 50, maxWorldRow = 50, size;

    public TileManager(PlayerMain playerMain) throws IOException {
        this.player = playerMain;
        tiles = new Tile[70];
        this.size = (GamePanel.screenWidth / 32);
        mapTileNumber = new int[maxWorldColum][maxWorldRow];
        getTileImg();
        loadMap();
    }

    private record Tile(BufferedImage image, boolean collision) {
    }

    private void getTileImg() throws IOException {
        tiles[0] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/blackTile.png"))), false); //void
        tiles[1] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/blackTile.png"))), false); //testVoid
        tiles[2] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/grassTile.png"))), false); //grass
        tiles[3] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/mountain.png"))), true); //borderMountain
        tiles[4] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/grayTile.png"))), false);  //concreteFloor
        tiles[5] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/brownTile.png"))), false); //dirt
        tiles[6] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/woodTile.png"))), false); //woodFloor
        tiles[7] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/borderTile2.png"))), true); //barbedWireHor
        tiles[8] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/grayTile2.png"))), false); //road/concrete
        tiles[9] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/grayTileDestroyed.png"))), false); //rubbish
        tiles[10] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/metalTile.png"))), false); //metal
        tiles[11] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/borderTile3.png"))), true); //barbedWireVer
        tiles[12] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/borderTile4.png"))), true); //barbedWireCon

        tiles[13] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/wallLeft.png"))), true); //concreteWallLeft
        tiles[14] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/wallRight.png"))), true); //concreteWallRight
        tiles[15] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/wallDown.png"))), true); //concreteWallDown
        tiles[16] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/wallUp.png"))), true); //concreteWallUp

        tiles[17] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/wallDownRightCorner.png"))), true);
        tiles[18] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/wallDownLeftCorner.png"))), true);
        tiles[19] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/wallUpRightCorner.png"))), true);
        tiles[20] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/wallUpLeftCorner.png"))), true);

        tiles[21] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/wallUpLeftCorner2.png"))), true);
        tiles[22] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/wallUpRightCorner2.png"))), true);
        tiles[23] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/wallDownLeftCorner2.png"))), true);
        tiles[24] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/wallDownRightCorner2.png"))), true);

        tiles[25] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/grayTile2.png"))), true);
        tiles[26] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/wallBottom.png"))), true);
        tiles[27] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/wallMid.png"))), true);

        tiles[28] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/wallUpLeft.png"))), true);
        tiles[29] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/wallUpRight.png"))), true);
        tiles[30] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/wallRight2.png"))), true);
        tiles[31] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/wallLeft2.png"))), true);
        tiles[32] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/grassTile2.png"))), false);
        tiles[33] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/wallDown.png"))), true);
        tiles[34] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/window.png"))), true);
        tiles[35] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/woodTile2.png"))), true);
        tiles[36] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/rail.png"))), false);
        tiles[37] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/railDestroyed.png"))), false);
        tiles[38] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/rail2.png"))), false);
        tiles[39] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/railDown.png"))), false);
        tiles[40] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/rail2Down.png"))), false);

        tiles[41] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/fence1.png"))), true);
        tiles[42] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/fence2.png"))), true);
        tiles[43] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/fence3.png"))), true);
        tiles[44] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/fence4.png"))), true);
        tiles[45] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/fence5.png"))), true);
        tiles[46] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/fence6.png"))), true);

        tiles[47] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/swampWater.png"))), true);
        tiles[48] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/swampWater2.png"))), true);
        tiles[49] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/swampWater3.png"))), true);
        tiles[50] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/brownTile3.png"))), false);

        tiles[51] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/woodTile3.png"))), true);
        tiles[52] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/woodTile4.png"))), true);
        tiles[53] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/woodTile5.png"))), true);
        tiles[54] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/woodTile6.png"))), true);
        tiles[55] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/woodTile7.png"))), true);
        tiles[56] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/woodTile8.png"))), true);
        tiles[57] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/woodTile9.png"))), true);
        tiles[58] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/woodTile10.png"))), true);
        tiles[59] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/woodTile11.png"))), true);
        tiles[60] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/woodTile12.png"))), true);
        tiles[61] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/woodTile13.png"))), true);
        tiles[62] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/woodTile14.png"))), true);
        tiles[63] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/woodTile15.png"))), true);
        tiles[64] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/window2.png"))), true);
        tiles[65] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/woodTile16.png"))), true);
        tiles[66] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/woodTile17.png"))), true);
        tiles[67] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/grassTile3.png"))), true);
        tiles[69] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/greenWall.png"))), true);
        tiles[68] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Tiles/floor.png"))), false);
    }

    private void loadMap() throws IOException {
        String filePath = "clientGameData/maps/map_crossfire.txt";

        BufferedReader br = new BufferedReader(new FileReader(filePath));

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

    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < maxWorldColum && worldRow < maxWorldRow) {
            int tileNum = mapTileNumber[worldCol][worldRow];
            int worldX = worldCol * size;
            int worldY = worldRow * size;
            int screenX = worldX - player.getWorldX() + player.getScreenX();
            int screenY = worldY - player.getWorldY() + player.getScreenY();
            if ((worldX + size > player.getWorldX() - player.getScreenX() && worldX - size < player.getWorldX() + player.getScreenX() &&
                    worldY + size > player.getWorldY() - player.getScreenY() && worldY - size < worldY + player.getScreenY()) && tileNum != 0) {
                g2.drawImage(tiles[tileNum].image(), screenX, screenY, size, size, null);
            }
            worldCol++;

            if (worldCol == maxWorldColum) {
                worldCol = 0;
                worldRow++;
            }
        }
    }

//    public Tile[] getTiles() {
//        return tiles;
//    }
//
//    public int[][] getMapTileNumber() {
//        return mapTileNumber;
//    }
}
