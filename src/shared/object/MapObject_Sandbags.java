package shared.object;

import shared.object.objectClasses.MapObject;

import java.awt.*;

public class MapObject_Sandbags extends MapObject {
    public MapObject_Sandbags(int worldX, int worldY, int width, int height) {
        super(worldX, worldY, width, height, false, "object/sandBags.png", new Rectangle(worldX, worldY, width, height - 30),
                true);
    }
}
