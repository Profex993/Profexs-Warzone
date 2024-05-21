package shared.object;

import shared.object.objectClasses.MapObject_Weapon;
import shared.weapon.Weapon_Port13;

@SuppressWarnings("unused")
public class MapObject_Weapon_Port13 extends MapObject_Weapon {

    public MapObject_Weapon_Port13(int worldX, int worldY) {
        super(worldX, worldY, 60, 30, "weapons/port13Right.png", Weapon_Port13.class);
    }
}
