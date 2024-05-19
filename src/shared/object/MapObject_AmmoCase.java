package shared.object;

import shared.object.objectClasses.MapObject;

import java.awt.*;

public class MapObject_AmmoCase extends MapObject {
    public MapObject_AmmoCase(int worldX, int worldY) {
        super(worldX, worldY, 60, 48, false, "object/ammoCreate.png",
                new Rectangle(worldX, worldY, 60, 48), true);
    }
}
