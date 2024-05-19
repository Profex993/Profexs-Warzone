package shared.object.objectClasses;

import java.awt.*;

/**
 * map object for buildings
 */
public abstract class MapObject_Building extends MapObject {
    public MapObject_Building(int worldX, int worldY, int width, int height, String imagePath, Rectangle solidArea) {
        super(worldX, worldY, width, height, false, imagePath, solidArea, false);
    }
}
