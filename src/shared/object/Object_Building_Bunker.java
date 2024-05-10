package shared.object;

import shared.object.objectClasses.Object_Building;

import java.awt.*;

public class Object_Building_Bunker extends Object_Building {
    public Object_Building_Bunker(int worldX, int worldY) {
        super(worldX, worldY, 1000, 600, "object/bunker.png", new Rectangle(worldX, worldY, 1000, 600));
    }
}
