package shared.object;

import shared.object.objectClasses.MapObject;

import java.awt.*;

public class MapObject_Antenna2 extends MapObject {
    public MapObject_Antenna2(int worldX, int worldY, int width, int height) {
        super(worldX, worldY, width, height, false, "object/antenna2.png",
                new Rectangle(worldX, worldY, width, height), false);
    }
}
