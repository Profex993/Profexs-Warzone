package shared.weapon.weaponClasses;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * class for generating weapons from shared.weapon package
 */
public abstract class WeaponGenerator {
    private static final String WEAPON_PACKAGE = "shared.weapon";

    /**
     * get weapon from name
     * @param name String name which must equal weapon.getClass().getSimpleName() where Weapon_ is removed
     * @return returns object of weapon
     * @throws Exception if weapon cant be found
     */
    public static Weapon getWeaponByName(String name) throws Exception {
        Class<?> weaponClass = Class.forName(WEAPON_PACKAGE + ".Weapon_" + name);
        Constructor<?> constructor = weaponClass.getDeclaredConstructor();

        return (Weapon) constructor.newInstance();
    }

    /**
     * get server side weapon from weaponClass
     * @param weaponClass Class<? extends Weapon> of the weapon
     * @return returns object of Weapon_Core
     * @throws Exception if weapon cant be found
     */
    public static Weapon_Core getServerSideWeapon(Class<? extends Weapon> weaponClass) throws Exception {
        String methodName = "getServerSideWeapon";
        Method method = weaponClass.getMethod(methodName);

        return (Weapon_Core) method.invoke(null);
    }
}
