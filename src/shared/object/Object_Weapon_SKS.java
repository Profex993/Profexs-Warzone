package shared.object;

import shared.object.objectClasses.Object_Weapon;
import shared.weapon.Weapon_SKS;

@SuppressWarnings("unused")
public class Object_Weapon_SKS extends Object_Weapon {
    public Object_Weapon_SKS(int worldX, int worldY) {
        super(worldX, worldY, 120, 30, "weapons/sksRight.png", Weapon_SKS.class);
    }
}
