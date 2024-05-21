package shared.object;

import shared.object.objectClasses.MapObject_Weapon;
import shared.weapon.Weapon_KSK;

@SuppressWarnings("unused")
public class MapObject_Weapon_KSK extends MapObject_Weapon {
    public MapObject_Weapon_KSK(int worldX, int worldY) {
        super(worldX, worldY, 120, 30, "weapons/kskRight.png", Weapon_KSK.class);
    }
}
