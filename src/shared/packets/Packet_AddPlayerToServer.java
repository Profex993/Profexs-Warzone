package shared.packets;

import shared.ConstantsShared;

import java.io.IOException;

public record Packet_AddPlayerToServer(String name, String playerModel) {
    public static Packet_AddPlayer parseString(String line) throws IOException {
        try {
            String[] parts = line.split(ConstantsShared.PROTOCOL_VARIABLE_SPLIT);
            return new Packet_AddPlayer(parts[0], parts[1]);
        } catch (Exception e) {
            throw new IOException("corrupted input\n" + e);
        }
    }

    public String toString() {
        String split = ConstantsShared.PROTOCOL_VARIABLE_SPLIT;
        return name + split + playerModel;
    }
}
