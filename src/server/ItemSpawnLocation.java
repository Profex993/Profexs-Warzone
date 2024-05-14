package server;

import shared.MapGenerator;
import shared.object.objectClasses.Object;

import java.util.ArrayList;

public class ItemSpawnLocation {
    private final int x, y;
    private int spawnDelay;
    private Object object;

    public ItemSpawnLocation(int x, int y) {
        this.x = x;
        this.y = y;
        spawnDelay = ConstantsServer.WEAPON_ITEM_DESPAWN_DELAY;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public void setObjectAndResetDelay(Object object) {
        this.object = object;
        spawnDelay = ConstantsServer.WEAPON_ITEM_DESPAWN_DELAY;
    }

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

    public Object getObject() {
        return object;
    }
}
