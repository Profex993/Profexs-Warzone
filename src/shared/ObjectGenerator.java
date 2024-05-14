package shared;

import shared.object.objectClasses.Object;

import java.lang.reflect.Constructor;

public class ObjectGenerator {
    private static final String OBJECT_PACKAGE = "shared.object";

    public static Object getObjectByName(String name, int x, int y) throws Exception {
        Class<?> objectClass = Class.forName(OBJECT_PACKAGE + "." + name);
        Constructor<?> constructor = objectClass.getDeclaredConstructor(int.class, int.class);

        return (Object) constructor.newInstance(x, y);
    }
}
