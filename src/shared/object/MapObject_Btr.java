package shared.object;

import shared.object.objectClasses.MapObject;

import java.awt.*;

public class MapObject_Btr extends MapObject {
    public MapObject_Btr(int worldX, int worldY) {
        super(worldX, worldY, 550, 150, false, "object/btr.png",
                new Rectangle(worldX + 30, worldY + 35, 520, 115), false);
    }
}
