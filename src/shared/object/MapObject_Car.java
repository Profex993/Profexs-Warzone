package shared.object;

import shared.object.objectClasses.MapObject;

import java.awt.*;

public class MapObject_Car extends MapObject {
    public MapObject_Car(int worldX, int worldY) {
        super(worldX, worldY, 300, 150, false, "object/car.png",
                new Rectangle(worldX, worldY + 50, 300, 100), false);
    }
}
