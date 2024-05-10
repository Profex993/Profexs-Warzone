package shared.object;

import shared.object.objectClasses.Object;

import java.awt.*;

public class Object_Bricks extends Object {
    public Object_Bricks(int worldX, int worldY, int width, int height) {
        super(worldX, worldY, width, height, false, "object/bricks.png",
                new Rectangle(worldX, worldY, width, height), true);
    }
}
