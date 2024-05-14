package server;

import shared.MapGenerator;

import java.util.ArrayList;

public record SpawnLocation(int x, int y) {

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
