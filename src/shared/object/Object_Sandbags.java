package shared.object;

import shared.object.objectClasses.Object;

import java.awt.*;

public class Object_Sandbags extends Object {
    public Object_Sandbags(int worldX, int worldY, int width, int height) {
        super(worldX, worldY, width, height, false, "object/sandBags.png", new Rectangle(worldX, worldY, width, height - 30),
                true);
    }
}
