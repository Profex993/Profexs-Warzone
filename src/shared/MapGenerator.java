package shared;

import shared.objects.Object;
import shared.objects.Object_Weapon.Object_Weapon_AK;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public abstract class MapGenerator {
    private static final Tile[] tiles;

    public static BufferedReader getMap(int mapNum) throws FileNotFoundException {
        String filePath = switch (mapNum) {
            case 0 -> "maps/map_crossfire.txt";
            default -> "";
        };

        return new BufferedReader(new FileReader(filePath));
    }

    public static ArrayList<Object> getMapObjects(int mapNum) {
        ArrayList<Object> list = new ArrayList<>();
        switch (mapNum) {
            case 0 -> {
                list.add(new Object_Weapon_AK(200, 200));
                list.add(new Object_Weapon_AK(200, 200));
            }
        }

        return list;
    }

    private record Tile(String path, boolean collision) {
    }

    static {
        tiles = new Tile[70];

        tiles[0] = new Tile("tiles/blackTile.png", false);
        tiles[1] = new Tile("tiles/blackTile.png", false);
        tiles[2] = new Tile("tiles/grassTile.png", false);
        tiles[3] = new Tile("tiles/mountain.png", true);
        tiles[4] = new Tile("tiles/grayTile.png", false);
        tiles[5] = new Tile("tiles/brownTile.png", false);
        tiles[6] = new Tile("tiles/woodTile.png", false);
        tiles[7] = new Tile("tiles/borderTile2.png", true);
        tiles[8] = new Tile("tiles/grayTile2.png", false);
        tiles[9] = new Tile("tiles/grayTileDestroyed.png", false);
        tiles[10] = new Tile("tiles/metalTile.png", false);
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
    }

    public static String[] getTileSetImagePath() {
        String[] out = new String[tiles.length];
        for (int i = 0; i < tiles.length; i++) {
            out[i] = tiles[i].path();
        }
        return out;
    }

    public static boolean[] getTileSetCollision() {
        boolean[] out = new boolean[tiles.length];
        for (int i = 0; i < tiles.length; i++) {
            out[i] = tiles[i].collision();
        }
        return out;
    }
}
