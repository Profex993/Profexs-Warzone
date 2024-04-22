package shared.weapon.abstracts;

import shared.weapon.Weapon_AK;
import shared.weapon.Weapon_Core;
import shared.weapon.Weapon_Makarov;
import shared.weapon.Weapon_Sks;

public abstract class WeaponGenerator {

    public static Weapon getWeaponByName(String name) {
        return switch (name) {
            case Weapon_AK.name -> new Weapon_AK();
            case Weapon_Makarov.name -> new Weapon_Makarov();
            case Weapon_Sks.name -> new Weapon_Sks();
            default -> null;
        };
    }

    public static Weapon_Core getServerSideWeapon(Class<? extends Weapon> weaponClass) {
        if (weaponClass == Weapon_AK.class) {
            return Weapon_AK.getServerSideWeapon();
        } else if (weaponClass == Weapon_Makarov.class) {
            return Weapon_Makarov.getServerSideWeapon();
        } else if (weaponClass == Weapon_Sks.class) {
            return Weapon_Sks.getServerSideWeapon();
        } else {
            return null;
        }
    }
}
