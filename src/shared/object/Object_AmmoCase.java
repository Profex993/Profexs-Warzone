package shared.object;

import shared.object.objectClasses.Object;

import java.awt.*;

public class Object_AmmoCase extends Object {
    public Object_AmmoCase(int worldX, int worldY) {
        super(worldX, worldY, 60, 48, false, "object/ammoCreate.png",
                new Rectangle(worldX, worldY, 60, 48), true);
    }
}
