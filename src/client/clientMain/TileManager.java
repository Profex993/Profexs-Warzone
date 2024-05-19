package client.clientMain;

import client.entity.MainPlayer;
import shared.ConstantsShared;
import shared.MapGenerator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;

/**
 * class for drawing map
 */
public class TileManager {
    private final MainPlayer player;
    private final String[] tiles;
    private final BufferedImage[] tileImages;
    private final int[][] mapTileNumber;
    private final int maxWorldColum = ConstantsShared.MAX_WORLD_WIDTH, maxWorldRow = ConstantsShared.MAX_WORLD_HEIGHT;

    /**
     * constructor initializes images of tiles, tiles are from MapGenerator
     * @param mainPlayer MainPlayer for determining position of drawing
     */
    public TileManager(MainPlayer mainPlayer) {
        this.player = mainPlayer;
        tiles = MapGenerator.getTileSetImagePath();
        tileImages = new BufferedImage[tiles.length];
        mapTileNumber = new int[maxWorldColum][maxWorldRow];
        try {
            getTileImg();
        } catch (IOException e) {
            ClientMain.showErrorWindowAndExit("can not load resources", e);
        }
    }

    /**
     * load images of tiles
     * @throws IOException if image cant be loaded
     */
    private void getTileImg() throws IOException {
        for (int i = 0; i < tiles.length; i++) {
            tileImages[i] = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(tiles[i])));
        }
    }

    /**
     * load map from file reader generated in MapGenerator
     * @param mapNum int representing map
     * @throws IOException if map cant be found
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

    /**
     * draw map
     * @param g2 Graphics2D
     */
    public void draw(Graphics2D g2) {
        int playerWorldX = player.getWorldX();
        int playerWorldY = player.getWorldY();
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < maxWorldColum && worldRow < maxWorldRow) {
            int tileNum = mapTileNumber[worldCol][worldRow];
            int size = ConstantsShared.TILE_SIZE;
            int worldX = worldCol * size;
            int worldY = worldRow * size;
            int screenX = worldX - playerWorldX + player.getScreenX();
            int screenY = worldY - playerWorldY + player.getScreenY();
            if ((worldX + size > playerWorldX - player.getScreenX() && worldX - size < playerWorldX + player.getScreenX() &&
                    worldY + size > playerWorldY - player.getScreenY() && worldY - size < playerWorldY + player.getScreenY())
                    && tileNum != 0) {
                g2.drawImage(tileImages[tileNum], screenX, screenY, size, size, null);
            }
            worldCol++;

            if (worldCol == maxWorldColum) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
