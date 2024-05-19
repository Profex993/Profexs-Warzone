package shared.object;

import shared.object.objectClasses.MapObject_Building;

import java.awt.*;

public class MapObject_Building_4 extends MapObject_Building {
    public MapObject_Building_4(int worldX, int worldY, int width, int height) {
        super(worldX, worldY, width, height, "object/building4.png", new Rectangle(worldX, worldY, width, height));
    }
}
