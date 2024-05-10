package shared.object;

import shared.object.objectClasses.Object;

import java.awt.*;

public class Object_Btr extends Object {
    public Object_Btr(int worldX, int worldY) {
        super(worldX, worldY, 550, 150, false, "object/btr.png",
                new Rectangle(worldX + 30, worldY + 35, 520, 115), false);
    }
}
