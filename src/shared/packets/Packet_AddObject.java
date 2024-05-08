package shared.packets;

import shared.ConstantsShared;
import shared.object.objectClasses.Object;

import java.io.IOException;

public record Packet_AddObject(String name, int x, int y) {
    public final static String head = "addObject";

    public static Packet_AddObject parseString(String line) throws IOException {
        try {
            String[] parts = line.split(ConstantsShared.protocolVariableSplit);
            return new Packet_AddObject(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
        } catch (Exception e) {
            throw new IOException("corrupted input\n" + e);
        }
    }

    public static Packet_AddObject getFromObject(Object object) {
        return new Packet_AddObject(object.getClass().getSimpleName(), object.getWorldX(), object.getWorldY());
    }

    public String toString() {
        String split = ConstantsShared.protocolVariableSplit;
        return head + ConstantsShared.protocolLineSplit + name + split + x + split + y;
    }
}
