package shared.object;

import shared.object.objectClasses.MapObject;

import java.awt.*;

public class MapObject_Cable extends MapObject {
    public MapObject_Cable(int worldX, int worldY, int width, int height) {
        super(worldX, worldY, width, height, false, "object/cableObject.png",
                new Rectangle(worldX, worldY, width, height), true);
    }
}
