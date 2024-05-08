package shared.object;

import shared.object.objectClasses.Object;

import java.awt.*;

@SuppressWarnings("unused")
public class Object_Test extends Object {

    public Object_Test(int worldX, int worldY) {
        super(worldX, worldY, 100, 100, false, "tiles/blackTile.png",
                new Rectangle(worldX, worldY, 100, 100));
    }
}
