package shared.object;

import shared.object.objectClasses.MapObject_Weapon;
import shared.weapon.Weapon_KA;

@SuppressWarnings("unused")
public class MapObject_Weapon_KA extends MapObject_Weapon {
    public MapObject_Weapon_KA(int worldX, int worldY) {
        super(worldX, worldY, 80, 30, "weapons/ka74Right.png", Weapon_KA.class);
    }
}
