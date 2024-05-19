package shared.object;

import shared.object.objectClasses.MapObject_Weapon;
import shared.weapon.Weapon_PM;

@SuppressWarnings("unused")
public class MapObject_Weapon_PM extends MapObject_Weapon {
    public MapObject_Weapon_PM(int worldX, int worldY) {
        super(worldX, worldY, 60, 30, "weapons/makarovPMRight.png", Weapon_PM.class);
    }
}
