package shared.object;

import shared.object.objectClasses.Object;

import java.awt.*;

public class Object_Btr2 extends Object {
    public Object_Btr2(int worldX, int worldY) {
        super(worldX, worldY, 200, 150, false, "object/btrDestroyed.png",
                new Rectangle(worldX, worldY, 200, 150), false);
    }
}
