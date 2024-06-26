package shared.packets;

import shared.ConstantsShared;

import java.io.IOException;

/**
 * packet to change clients map
 * @param mapNumber int representing map
 */
public record Packet_ChangeMap(int mapNumber) {
    public final static String head = "changeMap";

    public static Packet_ChangeMap parseString(String line) throws IOException {
        try {
            return new Packet_ChangeMap(Integer.parseInt(line));
        } catch (Exception e) {
            throw new IOException("corrupted input\n" + e);
        }
    }

    public String toString() {
        return head + ConstantsShared.PROTOCOL_LINE_SPLIT + mapNumber;
    }
}
