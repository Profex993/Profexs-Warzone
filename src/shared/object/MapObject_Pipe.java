package shared.object;

import shared.object.objectClasses.MapObject;

import java.awt.*;

public class MapObject_Pipe extends MapObject {
    public MapObject_Pipe(int worldX, int worldY, int width, int height) {
        super(worldX, worldY, width, height, false, "object/pipes.png", new Rectangle(worldX, worldY, width, height),
                false);
    }
}
