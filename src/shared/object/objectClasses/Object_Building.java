package shared.object.objectClasses;

import java.awt.*;

public abstract class Object_Building extends Object {
    public Object_Building(int worldX, int worldY, int width, int height, String imagePath, Rectangle solidArea) {
        super(worldX, worldY, width, height, false, imagePath, solidArea, false);
    }
}
