package shared.packets;

import shared.ConstantsShared;

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

    public static Packet_RemoveObject getFromObject(int hash) {
        return new Packet_RemoveObject(hash);
    }

    public String toString() {
        return head + ConstantsShared.protocolLineSplit + objectHash;
    }
}
