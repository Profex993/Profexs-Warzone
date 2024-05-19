package shared.object;

import shared.object.objectClasses.MapObject;

import java.awt.*;

public class MapObject_Btr2 extends MapObject {
    public MapObject_Btr2(int worldX, int worldY) {
        super(worldX, worldY, 200, 150, false, "object/btrDestroyed.png",
                new Rectangle(worldX, worldY, 200, 150), false);
    }
}
