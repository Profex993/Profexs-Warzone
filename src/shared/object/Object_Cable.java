package shared.object;

import shared.object.objectClasses.Object;

import java.awt.*;

public class Object_Cable extends Object {
    public Object_Cable(int worldX, int worldY, int width, int height) {
        super(worldX, worldY, width, height, false, "object/cableObject.png",
                new Rectangle(worldX, worldY, width, height), true);
    }
}
