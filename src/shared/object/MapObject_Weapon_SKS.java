package shared.object;

import shared.object.objectClasses.MapObject_Weapon;
import shared.weapon.Weapon_SKS;

@SuppressWarnings("unused")
public class MapObject_Weapon_SKS extends MapObject_Weapon {
    public MapObject_Weapon_SKS(int worldX, int worldY) {
        super(worldX, worldY, 120, 30, "weapons/sksRight.png", Weapon_SKS.class);
    }
}
