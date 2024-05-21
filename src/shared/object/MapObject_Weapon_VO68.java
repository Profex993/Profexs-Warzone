package shared.object;

import shared.object.objectClasses.MapObject_Weapon;
import shared.weapon.Weapon_VO68;

public class MapObject_Weapon_VO68 extends MapObject_Weapon {

    public MapObject_Weapon_VO68(int worldX, int worldY) {
        super(worldX, worldY, 80, 30, "weapons/vo68Right.png", Weapon_VO68.class);
    }
}
