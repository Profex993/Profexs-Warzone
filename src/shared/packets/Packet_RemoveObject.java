package shared.packets;

import shared.ConstantsShared;
import shared.object.objectClasses.MapObject;

import java.io.IOException;

public record Packet_RemoveObject(int objectHash) {
    public final static String head = "removeObject";

    public static Packet_RemoveObject parseString(String line) throws IOException {
        try {
            return new Packet_RemoveObject(Integer.parseInt(line));
        } catch (Exception e) {
            throw new IOException("corrupted input\n" + e);
        }
    }

    public static Packet_RemoveObject getFromObject(MapObject mapObject) {
        return new Packet_RemoveObject(mapObject.hashCode());
    }

    public String toString() {
        return head + ConstantsShared.PROTOCOL_LINE_SPLIT + objectHash;
    }
}
