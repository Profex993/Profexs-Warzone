package shared.object;

import shared.object.objectClasses.MapObject_Weapon;
import shared.weapon.Weapon_DB;

public class MapObject_Weapon_DB extends MapObject_Weapon {

    public MapObject_Weapon_DB(int worldX, int worldY) {
        super(worldX, worldY, 100, 30, "weapons/doubleBarrelRight.png", Weapon_DB.class);
    }
}
