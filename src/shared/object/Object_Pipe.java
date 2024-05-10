package shared.object;

import shared.object.objectClasses.Object;

import java.awt.*;

public class Object_Pipe extends Object {
    public Object_Pipe(int worldX, int worldY, int width, int height) {
        super(worldX, worldY, width, height, false, "object/pipes.png", new Rectangle(worldX, worldY, width, height),
                false);
    }
}
