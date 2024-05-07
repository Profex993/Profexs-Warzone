package shared.packets;

import shared.ConstantsShared;

import java.io.IOException;

public record Packet_AddPlayer(String name, String playerModel) {
    public final static String head = "addPlayer";
    public static Packet_AddPlayer parseString(String line) throws IOException {
        try {
            String[] parts = line.split(ConstantsShared.protocolVariableSplit);
            return new Packet_AddPlayer(parts[0], parts[1]);
        } catch (Exception e) {
            throw new IOException("corrupted input\n" + e);
        }
    }

    public String toString() {
        String split = ConstantsShared.protocolVariableSplit;
        return head + ConstantsShared.protocolLineSplit + name + split + playerModel;
    }
}
