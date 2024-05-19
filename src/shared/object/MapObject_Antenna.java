package shared.object;

import shared.object.objectClasses.MapObject;

import java.awt.*;

public class MapObject_Antenna extends MapObject {
    public MapObject_Antenna(int worldX, int worldY, int width, int height) {
        super(worldX, worldY, width, height, false, "object/antenna.png",
                new Rectangle(worldX, worldY, width, height), false);
    }
}
