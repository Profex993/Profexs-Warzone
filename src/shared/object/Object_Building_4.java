package shared.object;

import shared.object.objectClasses.Object_Building;

import java.awt.*;

public class Object_Building_4 extends Object_Building {
    public Object_Building_4(int worldX, int worldY, int width, int height) {
        super(worldX, worldY, width, height, "object/building4.png", new Rectangle(worldX, worldY, width, height));
    }
}
