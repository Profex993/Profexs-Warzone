package server;

import shared.object.Object_Weapon_AK;
import shared.object.Object_Weapon_SKS;
import shared.weapon.Weapon_PM;
import shared.weapon.weaponClasses.Weapon;

public class ConstantsServer {
    public static final int[] MAPS = {0};
    public static final int MATCH_TIME = 600, PLAYER_SPEED = 4, PROJECTILE_SPEED = 40, OBJECT_TRIGGER_AREA_OFFSET = 45,
            MATCH_OVER_TIME = 20, WEAPON_ITEM_DESPAWN_DELAY = 30;
    public static final Class<? extends Weapon> DEFAULT_WEAPON = Weapon_PM.class;
    public static final String[] SPAWN_WEAPON_NAMES = {Object_Weapon_AK.class.getSimpleName(), Object_Weapon_SKS.class.getSimpleName()};
}
