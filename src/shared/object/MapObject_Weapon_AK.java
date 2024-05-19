package shared.object;

import shared.object.objectClasses.MapObject_Weapon;
import shared.weapon.Weapon_AK;

@SuppressWarnings("unused")
public class MapObject_Weapon_AK extends MapObject_Weapon {
    public MapObject_Weapon_AK(int worldX, int worldY) {
        super(worldX, worldY, 80, 30, "weapons/ak74Right.png", Weapon_AK.class);
    }
}
