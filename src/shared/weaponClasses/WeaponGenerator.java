package shared.weaponClasses;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public abstract class WeaponGenerator {
    private static final String WEAPON_PACKAGE = "shared.weapon";

    public static Weapon getWeaponByName(String name) throws Exception {
        String className = WEAPON_PACKAGE + ".Weapon_" + name;
        Class<?> weaponClass = Class.forName(className);
        Constructor<?> constructor = weaponClass.getDeclaredConstructor();

        return (Weapon) constructor.newInstance();
    }

    public static Weapon_Core getServerSideWeapon(Class<? extends Weapon> weaponClass) throws Exception {
        String methodName = "getServerSideWeapon";
        Method method = weaponClass.getMethod(methodName);

        return (Weapon_Core) method.invoke(null);
    }
}
