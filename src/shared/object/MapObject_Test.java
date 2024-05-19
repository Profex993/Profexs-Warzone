package shared.object;

import shared.object.objectClasses.MapObject;

import java.awt.*;

@SuppressWarnings("unused")
public class MapObject_Test extends MapObject {

    public MapObject_Test(int worldX, int worldY) {
        super(worldX, worldY, 100, 100, false, "tiles/blackTile.png",
                new Rectangle(worldX, worldY, 100, 100), false);
    }
}
