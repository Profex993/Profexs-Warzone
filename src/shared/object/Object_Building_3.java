package shared.object;

import shared.object.objectClasses.Object_Building;

import java.awt.*;

public class Object_Building_3 extends Object_Building {
    public Object_Building_3(int worldX, int worldY, int width, int height) {
        super(worldX, worldY, width, height, "object/building3.png", new Rectangle(worldX, worldY, width, height));
    }
}
