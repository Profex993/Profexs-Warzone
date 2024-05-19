package shared.object;

import shared.object.objectClasses.MapObject;

import java.awt.*;

public class MapObject_Bulldozer extends MapObject {
    public MapObject_Bulldozer(int worldX, int worldY) {
        super(worldX, worldY, 200, 150, false, "object/bulldozer.png",
                new Rectangle(worldX, worldY + 35, 200, 115), false);
    }
}
