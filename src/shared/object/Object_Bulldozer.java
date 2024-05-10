package shared.object;

import shared.object.objectClasses.Object;

import java.awt.*;

public class Object_Bulldozer extends Object {
    public Object_Bulldozer(int worldX, int worldY) {
        super(worldX, worldY, 200, 150, false, "object/bulldozer.png",
                new Rectangle(worldX, worldY + 35, 200, 115), false);
    }
}
