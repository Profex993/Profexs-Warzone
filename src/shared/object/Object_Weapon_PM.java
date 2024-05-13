package shared.object;

import shared.object.objectClasses.Object_Weapon;
import shared.weapon.Weapon_PM;

@SuppressWarnings("unused")
public class Object_Weapon_PM extends Object_Weapon {
    public Object_Weapon_PM(int worldX, int worldY) {
        super(worldX, worldY, 60, 30, "weapons/makarovPMRight.png", Weapon_PM.class);
    }
}
