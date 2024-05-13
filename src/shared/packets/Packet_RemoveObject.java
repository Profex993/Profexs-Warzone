package shared.packets;

import shared.ConstantsShared;
import shared.object.objectClasses.Object;

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

    public static Packet_RemoveObject getFromObject(Object object) {
        return new Packet_RemoveObject(object.hashCode());
    }

    public String toString() {
        return head + ConstantsShared.PROTOCOL_LINE_SPLIT + objectHash;
    }
}
