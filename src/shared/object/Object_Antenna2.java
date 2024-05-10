package shared.object;

import shared.object.objectClasses.Object;

import java.awt.*;

public class Object_Antenna2 extends Object {
    public Object_Antenna2(int worldX, int worldY, int width, int height) {
        super(worldX, worldY, width, height, false, "object/antenna2.png",
                new Rectangle(worldX, worldY, width, height), false);
    }
}
