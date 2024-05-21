package shared.object;

import shared.object.objectClasses.MapObject_Weapon;
import shared.weapon.Weapon_MP;

@SuppressWarnings("unused")
public class MapObject_Weapon_MP extends MapObject_Weapon {
    public MapObject_Weapon_MP(int worldX, int worldY) {
        super(worldX, worldY, 60, 30, "weapons/mpRight.png", Weapon_MP.class);
    }
}
