package shared.object;

import shared.object.objectClasses.MapObject_Weapon;
import shared.weapon.Weapon_ZSS;

@SuppressWarnings("unused")
public class MapObject_Weapon_ZSS extends MapObject_Weapon {

    public MapObject_Weapon_ZSS(int worldX, int worldY) {
        super(worldX, worldY, 90, 30, "weapons/vzzRight.png", Weapon_ZSS.class);
    }
}
