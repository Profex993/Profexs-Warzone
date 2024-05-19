package shared.object;

import shared.object.objectClasses.MapObject_Building;

import java.awt.*;

public class MapObject_Building_Bunker extends MapObject_Building {
    public MapObject_Building_Bunker(int worldX, int worldY) {
        super(worldX, worldY, 1000, 600, "object/bunker.png", new Rectangle(worldX, worldY, 1000, 600));
    }
}
