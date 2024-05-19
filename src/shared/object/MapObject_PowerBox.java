package shared.object;

import shared.object.objectClasses.MapObject;

import java.awt.*;

public class MapObject_PowerBox extends MapObject {
    public MapObject_PowerBox(int worldX, int worldY, int width, int height) {
        super(worldX, worldY, width, height, false, "object/powerBox.png", new Rectangle(worldX, worldY, width, height),
                true);
    }
}
