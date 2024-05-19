package server;

import shared.MapGenerator;
import shared.object.objectClasses.MapObject;

import java.util.ArrayList;

/**
 * location where items can spawn
 */
public class ItemSpawnLocation {
    private final int x, y;
    private int spawnDelay;
    private MapObject mapObject;

    /**
     * constructor sets delay
     * @param x x coordinate int
     * @param y y coordinate int
     */
    public ItemSpawnLocation(int x, int y) {
        this.x = x;
        this.y = y;
        spawnDelay = ConstantsServer.WEAPON_ITEM_DESPAWN_DELAY;
    }

    public void setObject(MapObject mapObject) {
        this.mapObject = mapObject;
    }

    public void setObjectAndResetDelay(MapObject mapObject) {
        this.mapObject = mapObject;
        spawnDelay = ConstantsServer.WEAPON_ITEM_DESPAWN_DELAY;
    }

    /**
     * create an Arraylist of ItemSpawnLocation
     * @param mapNumber int number of current map
     * @return Arraylist of ItemSpawnLocation
     */
    public static ArrayList<ItemSpawnLocation> getPlayerSpawnLocationList(int mapNumber) {
        try {
            ArrayList<ItemSpawnLocation> out = new ArrayList<>();
            String[] array = MapGenerator.getItemSpawnLocations(mapNumber).readLine().split(" ");

            for (String string : array) {
                String[] location = string.split(",");
                out.add(new ItemSpawnLocation(Integer.parseInt(location[0]), Integer.parseInt(location[1])));
            }

            return out;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void decreaseDelay() {
        spawnDelay--;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpawnDelay() {
        return spawnDelay;
    }

    public MapObject getObject() {
        return mapObject;
    }
}
