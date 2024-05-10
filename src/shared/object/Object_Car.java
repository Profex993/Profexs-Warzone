package shared.object;

import shared.object.objectClasses.Object;

import java.awt.*;

public class Object_Car extends Object {
    public Object_Car(int worldX, int worldY) {
        super(worldX, worldY, 300, 150, false, "object/car.png",
                new Rectangle(worldX, worldY + 50, 300, 100), false);
    }
}
