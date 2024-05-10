package shared.object;

import shared.object.objectClasses.Object;

import java.awt.*;

public class Object_PowerBox extends Object {
    public Object_PowerBox(int worldX, int worldY, int width, int height) {
        super(worldX, worldY, width, height, false, "object/powerBox.png", new Rectangle(worldX, worldY, width, height),
                true);
    }
}
