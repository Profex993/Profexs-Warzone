package shared;

import shared.object.*;
import shared.object.objectClasses.MapObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * class for generating map from file
 */
public abstract class MapGenerator {
    private static final Tile[] tiles;

    /**
     * get BufferedReader of map file
     * @param mapNum int representing map
     * @return returns BufferedReader of map file
     * @throws FileNotFoundException if file cant be found
     */
    public static BufferedReader getMap(int mapNum) throws FileNotFoundException {
        String filePath = switch (mapNum) {
            case 0 -> "maps/crossfire/map_crossfire.txt";
            default -> "";
        };

        return new BufferedReader(new FileReader(filePath));
    }

    /**
     * get BufferedReader of file containing map spawn locations on current map
     * @param mapNumber int representing map
     * @return BufferedReader of file containing map locations
     * @throws FileNotFoundException if file cant be found
     */
    public static BufferedReader getPlayerSpawnLocations(int mapNumber) throws FileNotFoundException {
        String filePath = switch (mapNumber) {
            case 0 -> "maps/crossfire/playerSpawn_crossfire.txt";
            default -> "";
        };

        return new BufferedReader(new FileReader(filePath));
    }

    /**
     * get BufferedReader of file containing map item spawn locations on current map
     * @param mapNumber int representing map
     * @return BufferedReader of file containing map locations
     * @throws FileNotFoundException if file cant be found
     */
    public static BufferedReader getItemSpawnLocations(int mapNumber) throws FileNotFoundException {
        String filePath = switch (mapNumber) {
            case 0 -> "maps/crossfire/itemSpawn_crossfire.txt";
            default -> "";
        };

        return new BufferedReader(new FileReader(filePath));
    }

    /**
     * get Arraylist of mapObjects on current map
     * @param mapNum int representing map
     * @return ArrayList of MapObject
     */
    public static ArrayList<MapObject> getMapObjects(int mapNum) {
        ArrayList<MapObject> list = new ArrayList<>();
        switch (mapNum) {
            case 0 -> {
                list.add(new MapObject_Building_3(2050, 1730, 300, 600));
                list.add(new MapObject_Building_1(48, 1604, 600, 300));
                list.add(new MapObject_Building_2(648, 1600, 300, 300));
                list.add(new MapObject_Building_4(1100, 1400, 700, 500));
                list.add(new MapObject_Building_Bunker(700, -200));
                list.add(new MapObject_Btr(992, 684));
                list.add(new MapObject_Btr(1416, 864));
                list.add(new MapObject_Bulldozer(2060, 1400));
                list.add(new MapObject_Sandbags(1116, 1964, 200, 60));
                list.add(new MapObject_Car(284, 1964));
                list.add(new MapObject_Pipe(1716, 72, 200, 80));
                list.add(new MapObject_Bricks(1984, 96, 168, 80));
                list.add(new MapObject_Sandbags(1700, 324, 200, 60));
                list.add(new MapObject_AmmoCase(1700, 272));
                list.add(new MapObject_Antenna2(570, -500, 100, 800));
                list.add(new MapObject_PowerBox2(548, 100, 60, 100));
                list.add(new MapObject_PowerBox(485, 100, 60, 100));
                list.add(new MapObject_Sandbags(516, 324, 200, 60));
                list.add(new MapObject_Cable(52, 2120, 120, 100));
                list.add(new MapObject_Cable(52, 2230, 120, 100));
                list.add(new MapObject_AmmoCase(1316, 1964));
            }
        }

        return list;
    }

    /**
     * record for tiles
     * @param path String path to image
     * @param collision boolean true if player cant walk through tile
     */
    private record Tile(String path, boolean collision) {
    }

    /**
     * returns set of tile image paths
     * @return String[] of image paths
     */
    public static String[] getTileSetImagePath() {
        String[] out = new String[tiles.length];
        for (int i = 0; i < tiles.length; i++) {
            out[i] = tiles[i].path();
        }
        return out;
    }

    /**
     * returns set of tile collision
     * @return boolean[] of collisions
     */
    public static boolean[] getTileSetCollision() {
        boolean[] out = new boolean[tiles.length];
        for (int i = 0; i < tiles.length; i++) {
            out[i] = tiles[i].collision();
        }
        return out;
    }

    // initialize tiles
    static {
        tiles = new Tile[74];

        tiles[0] = new Tile("tiles/blackTile.png", false);
        tiles[1] = new Tile("tiles/blackTile.png", false);
        tiles[2] = new Tile("tiles/grassTile.png", false);
        tiles[3] = new Tile("tiles/mountain.png", true);
        tiles[4] = new Tile("tiles/grayTile.png", false);
        tiles[5] = new Tile("tiles/brownTile.png", false);
        tiles[6] = new Tile("tiles/woodTile.png", false);
        tiles[7] = new Tile("tiles/borderTile2.png", true);
        tiles[8] = new Tile("tiles/grayTile2.png", false);
        tiles[9] = new Tile("tiles/grayTileDestroyed.png", true);
        tiles[10] = new Tile("tiles/metalTile.png", true);
        tiles[11] = new Tile("tiles/borderTile3.png", true);
        tiles[12] = new Tile("tiles/borderTile4.png", true);
        tiles[13] = new Tile("tiles/wallLeft.png", true);
        tiles[14] = new Tile("tiles/wallRight.png", true);
        tiles[15] = new Tile("tiles/wallDown.png", true);
        tiles[16] = new Tile("tiles/wallUp.png", true);
        tiles[17] = new Tile("tiles/wallDownRightCorner.png", true);
        tiles[18] = new Tile("tiles/wallDownLeftCorner.png", true);
        tiles[19] = new Tile("tiles/wallUpRightCorner.png", true);
        tiles[20] = new Tile("tiles/wallUpLeftCorner.png", true);
        tiles[21] = new Tile("tiles/wallUpLeftCorner2.png", true);
        tiles[22] = new Tile("tiles/wallUpRightCorner2.png", true);
        tiles[23] = new Tile("tiles/wallDownLeftCorner2.png", true);
        tiles[24] = new Tile("tiles/wallDownRightCorner2.png", true);
        tiles[25] = new Tile("tiles/grayTile2.png", true);
        tiles[26] = new Tile("tiles/wallBottom.png", true);
        tiles[27] = new Tile("tiles/wallMid.png", true);
        tiles[28] = new Tile("tiles/wallUpLeft.png", true);
        tiles[29] = new Tile("tiles/wallUpRight.png", true);
        tiles[30] = new Tile("tiles/wallRight2.png", true);
        tiles[31] = new Tile("tiles/wallLeft2.png", true);
        tiles[32] = new Tile("tiles/grassTile2.png", false);
        tiles[33] = new Tile("tiles/wallDown.png", true);
        tiles[34] = new Tile("tiles/window.png", true);
        tiles[35] = new Tile("tiles/woodTile2.png", true);
        tiles[36] = new Tile("tiles/rail.png", false);
        tiles[37] = new Tile("tiles/railDestroyed.png", false);
        tiles[38] = new Tile("tiles/rail2.png", false);
        tiles[39] = new Tile("tiles/railDown.png", false);
        tiles[40] = new Tile("tiles/rail2Down.png", false);
        tiles[41] = new Tile("tiles/fence1.png", true);
        tiles[42] = new Tile("tiles/fence2.png", true);
        tiles[43] = new Tile("tiles/fence3.png", true);
        tiles[44] = new Tile("tiles/fence4.png", true);
        tiles[45] = new Tile("tiles/fence5.png", true);
        tiles[46] = new Tile("tiles/fence6.png", true);
        tiles[47] = new Tile("tiles/swampWater.png", true);
        tiles[48] = new Tile("tiles/swampWater2.png", true);
        tiles[49] = new Tile("tiles/swampWater3.png", true);
        tiles[50] = new Tile("tiles/brownTile3.png", false);
        tiles[51] = new Tile("tiles/woodTile3.png", true);
        tiles[52] = new Tile("tiles/woodTile4.png", true);
        tiles[53] = new Tile("tiles/woodTile5.png", true);
        tiles[54] = new Tile("tiles/woodTile6.png", true);
        tiles[55] = new Tile("tiles/woodTile7.png", true);
        tiles[56] = new Tile("tiles/woodTile8.png", true);
        tiles[57] = new Tile("tiles/woodTile9.png", true);
        tiles[58] = new Tile("tiles/woodTile10.png", true);
        tiles[59] = new Tile("tiles/woodTile11.png", true);
        tiles[60] = new Tile("tiles/woodTile12.png", true);
        tiles[61] = new Tile("tiles/woodTile13.png", true);
        tiles[62] = new Tile("tiles/woodTile14.png", true);
        tiles[63] = new Tile("tiles/woodTile15.png", true);
        tiles[64] = new Tile("tiles/window2.png", true);
        tiles[65] = new Tile("tiles/woodTile16.png", true);
        tiles[66] = new Tile("tiles/woodTile17.png", true);
        tiles[67] = new Tile("tiles/grassTile3.png", true);
        tiles[68] = new Tile("tiles/floor.png", false);
        tiles[69] = new Tile("tiles/greenWall.png", true);
        tiles[70] = new Tile("tiles/heliportBase.png", false);
        tiles[71] = new Tile("tiles/heliportVertical.png", false);
        tiles[72] = new Tile("tiles/heliportHorizontal.png", false);
        tiles[73] = new Tile("tiles/heliportVertical2.png", false);
    }
}
