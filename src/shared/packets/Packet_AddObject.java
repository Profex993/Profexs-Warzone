package shared.packets;

import shared.ConstantsShared;
import shared.object.objectClasses.MapObject;

import java.io.IOException;

/**
 * packet for adding object
 * @param name name of object class equivalent of object.getClass().getSimpleName()
 * @param x int x coordinate
 * @param y int y coordinate
 */
public record Packet_AddObject(String name, int x, int y) {
    public final static String head = "addObject";

    public static Packet_AddObject parseString(String line) throws IOException {
        try {
            String[] parts = line.split(ConstantsShared.PROTOCOL_VARIABLE_SPLIT);
            return new Packet_AddObject(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
        } catch (Exception e) {
            throw new IOException("corrupted input\n" + e);
        }
    }

    public static Packet_AddObject getFromObject(MapObject mapObject) {
        return new Packet_AddObject(mapObject.getClass().getSimpleName(), mapObject.getWorldX(), mapObject.getWorldY());
    }

    public String toString() {
        String split = ConstantsShared.PROTOCOL_VARIABLE_SPLIT;
        return head + ConstantsShared.PROTOCOL_LINE_SPLIT + name + split + x + split + y;
    }
}
