package shared.object;

import shared.object.objectClasses.MapObject;

import java.awt.*;

public class MapObject_Bricks extends MapObject {
    public MapObject_Bricks(int worldX, int worldY, int width, int height) {
        super(worldX, worldY, width, height, false, "object/bricks.png",
                new Rectangle(worldX, worldY, width, height), true);
    }
}
