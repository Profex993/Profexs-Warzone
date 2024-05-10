package shared.object;

import shared.object.objectClasses.Object_Building;

import java.awt.*;

public class Object_Building_2 extends Object_Building {
    public Object_Building_2(int worldX, int worldY, int width, int height) {
        super(worldX, worldY, width, height, "object/building2.png", new Rectangle(worldX, worldY, width, height));
    }
}
