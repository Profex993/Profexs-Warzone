package client.clientMain;

import client.entity.MainPlayer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

public class TileManager {
    private final MainPlayer player;
    private final Tile[] tiles;
    private final int[][] mapTileNumber;
    private final int maxWorldColum = 50, maxWorldRow = 50, size;

    public TileManager(MainPlayer mainPlayer) throws IOException {
        this.player = mainPlayer;
        tiles = new Tile[70];
        this.size = 48;
        mapTileNumber = new int[maxWorldColum][maxWorldRow];
        getTileImg();
        loadMap();
    }

    private record Tile(BufferedImage image, boolean collision) {
    }

    private void getTileImg() throws IOException {
        tiles[0] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/blackTile.png"))), false); //void
        tiles[1] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/blackTile.png"))), false); //testVoid
        tiles[2] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/grassTile.png"))), false); //grass
        tiles[3] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/mountain.png"))), true); //borderMountain
        tiles[4] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/grayTile.png"))), false);  //concreteFloor
        tiles[5] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/brownTile.png"))), false); //dirt
        tiles[6] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/woodTile.png"))), false); //woodFloor
        tiles[7] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/borderTile2.png"))), true); //barbedWireHor
        tiles[8] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/grayTile2.png"))), false); //road/concrete
        tiles[9] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/grayTileDestroyed.png"))), false); //rubbish
        tiles[10] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/metalTile.png"))), false); //metal
        tiles[11] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/borderTile3.png"))), true); //barbedWireVer
        tiles[12] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/borderTile4.png"))), true); //barbedWireCon

        tiles[13] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/wallLeft.png"))), true); //concreteWallLeft
        tiles[14] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/wallRight.png"))), true); //concreteWallRight
        tiles[15] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/wallDown.png"))), true); //concreteWallDown
        tiles[16] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/wallUp.png"))), true); //concreteWallUp

        tiles[17] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/wallDownRightCorner.png"))), true);
        tiles[18] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/wallDownLeftCorner.png"))), true);
        tiles[19] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/wallUpRightCorner.png"))), true);
        tiles[20] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/wallUpLeftCorner.png"))), true);

        tiles[21] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/wallUpLeftCorner2.png"))), true);
        tiles[22] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/wallUpRightCorner2.png"))), true);
        tiles[23] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/wallDownLeftCorner2.png"))), true);
        tiles[24] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/wallDownRightCorner2.png"))), true);

        tiles[25] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/grayTile2.png"))), true);
        tiles[26] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/wallBottom.png"))), true);
        tiles[27] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/wallMid.png"))), true);

        tiles[28] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/wallUpLeft.png"))), true);
        tiles[29] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/wallUpRight.png"))), true);
        tiles[30] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/wallRight2.png"))), true);
        tiles[31] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/wallLeft2.png"))), true);
        tiles[32] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/grassTile2.png"))), false);
        tiles[33] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/wallDown.png"))), true);
        tiles[34] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/window.png"))), true);
        tiles[35] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/woodTile2.png"))), true);
        tiles[36] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/rail.png"))), false);
        tiles[37] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/railDestroyed.png"))), false);
        tiles[38] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/rail2.png"))), false);
        tiles[39] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/railDown.png"))), false);
        tiles[40] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/rail2Down.png"))), false);

        tiles[41] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/fence1.png"))), true);
        tiles[42] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/fence2.png"))), true);
        tiles[43] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/fence3.png"))), true);
        tiles[44] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/fence4.png"))), true);
        tiles[45] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/fence5.png"))), true);
        tiles[46] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/fence6.png"))), true);

        tiles[47] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/swampWater.png"))), true);
        tiles[48] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/swampWater2.png"))), true);
        tiles[49] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/swampWater3.png"))), true);
        tiles[50] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/brownTile3.png"))), false);

        tiles[51] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/woodTile3.png"))), true);
        tiles[52] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/woodTile4.png"))), true);
        tiles[53] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/woodTile5.png"))), true);
        tiles[54] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/woodTile6.png"))), true);
        tiles[55] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/woodTile7.png"))), true);
        tiles[56] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/woodTile8.png"))), true);
        tiles[57] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/woodTile9.png"))), true);
        tiles[58] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/woodTile10.png"))), true);
        tiles[59] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/woodTile11.png"))), true);
        tiles[60] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/woodTile12.png"))), true);
        tiles[61] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/woodTile13.png"))), true);
        tiles[62] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/woodTile14.png"))), true);
        tiles[63] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/woodTile15.png"))), true);
        tiles[64] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/window2.png"))), true);
        tiles[65] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/woodTile16.png"))), true);
        tiles[66] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/woodTile17.png"))), true);
        tiles[67] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/grassTile3.png"))), true);
        tiles[69] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/greenWall.png"))), true);
        tiles[68] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/floor.png"))), false);
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
        int playerWorldX = player.getWorldX();
        int playerWorldY = player.getWorldY();
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < maxWorldColum && worldRow < maxWorldRow) {
            int tileNum = mapTileNumber[worldCol][worldRow];
            int worldX = worldCol * size;
            int worldY = worldRow * size;
            int screenX = worldX - playerWorldX + player.getScreenX();
            int screenY = worldY - playerWorldY + player.getScreenY();
            if ((worldX + size > playerWorldX - player.getScreenX() && worldX - size < playerWorldX + player.getScreenX() &&
                    worldY + size > playerWorldY - player.getScreenY() && worldY - size < playerWorldY + player.getScreenY())
                    && tileNum != 0) {
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
