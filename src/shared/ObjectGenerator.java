package shared;

import shared.object.objectClasses.MapObject;

import java.lang.reflect.Constructor;

/**
 * class for generating MapObjects from shared.object package
 */
public abstract class ObjectGenerator {
    private static final String OBJECT_PACKAGE = "shared.object";

    /**
     * generates map objects from shared.object package
     * @param name name of the object class, this name equals to Object.getClass().getSimpleName()
     * @param x x coordinate
     * @param y y coordinate
     * @return Object
     * @throws Exception if object cant be found
     */
    public static MapObject getObjectByName(String name, int x, int y) throws Exception {
        Class<?> objectClass = Class.forName(OBJECT_PACKAGE + "." + name);
        Constructor<?> constructor = objectClass.getDeclaredConstructor(int.class, int.class);

        return (MapObject) constructor.newInstance(x, y);
    }
}
