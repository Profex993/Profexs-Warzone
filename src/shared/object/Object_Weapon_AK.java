package shared.object;

import shared.object.objectClasses.Object_Weapon;
import shared.weapon.Weapon_AK;

public class Object_Weapon_AK extends Object_Weapon {
    public Object_Weapon_AK(int worldX, int worldY) {
        super(worldX, worldY, 80, 30, "weapons/ak74Right.png", Weapon_AK.class);
    }
}
