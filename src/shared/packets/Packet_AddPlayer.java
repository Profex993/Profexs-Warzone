package shared.packets;

import shared.ConstantsShared;

import java.io.IOException;

public record Packet_AddPlayer(String name, String playerModel, boolean isNew) {
    public final static String head = "addPlayer";
    public static Packet_AddPlayer parseString(String line) throws IOException {
        try {
            String[] parts = line.split(ConstantsShared.PROTOCOL_VARIABLE_SPLIT);
            return new Packet_AddPlayer(parts[0], parts[1], Boolean.parseBoolean(parts[2]));
        } catch (Exception e) {
            throw new IOException("corrupted input\n" + e);
        }
    }

    public String toString() {
        String split = ConstantsShared.PROTOCOL_VARIABLE_SPLIT;
        return head + ConstantsShared.PROTOCOL_LINE_SPLIT + name + split + playerModel + split + isNew;
    }
}
