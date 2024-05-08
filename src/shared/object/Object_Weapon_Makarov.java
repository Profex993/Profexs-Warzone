package shared.object;

import shared.object.objectClasses.Object_Weapon;
import shared.weapon.Weapon_Makarov;

@SuppressWarnings("unused")
public class Object_Weapon_Makarov extends Object_Weapon {
    public Object_Weapon_Makarov(int worldX, int worldY) {
        super(worldX, worldY, 60, 30, "weapons/makarovPMRight.png", Weapon_Makarov.class);
    }
}
