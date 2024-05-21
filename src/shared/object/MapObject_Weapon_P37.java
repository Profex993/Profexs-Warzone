package shared.object;

import shared.object.objectClasses.MapObject_Weapon;
import shared.weapon.Weapon_P37;

public class MapObject_Weapon_P37 extends MapObject_Weapon {

    public MapObject_Weapon_P37(int worldX, int worldY) {
        super(worldX, worldY, 120, 30, "weapons/p36Right.png", Weapon_P37.class);
    }
}
