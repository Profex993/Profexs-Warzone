package server;

import shared.MapGenerator;

import java.util.ArrayList;

/**
 * location where player can spawn
 * @param x x coordinate int
 * @param y y coordinate int
 */
public record SpawnLocation(int x, int y) {

    /**
     * makes ArrayList of SpawnLocations for current map
     * @param mapNumber int of current map
     * @return return ArrayList of SpawnLocation
     */
    public static ArrayList<SpawnLocation> getPlayerSpawnLocationList(int mapNumber) {
        try {
            ArrayList<SpawnLocation> out = new ArrayList<>();
            String[] array = MapGenerator.getPlayerSpawnLocations(mapNumber).readLine().split(" ");

            for (String string : array) {
                String[] location = string.split(",");
                out.add(new SpawnLocation(Integer.parseInt(location[0]), Integer.parseInt(location[1])));
            }

            return out;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
