package server;

import shared.object.MapObject_Weapon_AK;
import shared.object.MapObject_Weapon_SKS;
import shared.weapon.Weapon_PM;
import shared.weapon.weaponClasses.Weapon;

/**
 * abstract class containing constants for easy change
 */
public abstract class ConstantsServer {
    public static final int[] MAPS = {0}; //array of maps, each number represents a map, 1 - crossfire
    public static final int MATCH_TIME = 600, PLAYER_SPEED = 4, PROJECTILE_SPEED = 40, OBJECT_TRIGGER_AREA_OFFSET = 45,
            MATCH_OVER_TIME = 20, WEAPON_ITEM_DESPAWN_DELAY = 30, PROJECTILE_SIZE = 4, PLAYER_RESPAWN_TIME = 600;

    public static final Class<? extends Weapon> DEFAULT_WEAPON = Weapon_PM.class;

    //names of weapons that can spawn on the map
    public static final String[] SPAWN_WEAPON_NAMES = {MapObject_Weapon_AK.class.getSimpleName(), MapObject_Weapon_SKS.class.getSimpleName()};
}
