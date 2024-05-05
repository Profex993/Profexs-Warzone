package shared.packets;

import shared.ConstantsShared;

import java.io.IOException;

public record PlayerInitialData(String name, String playerModel) {
    public static PlayerInitialData parseString(String line) throws IOException {
        try {
            String[] parts = line.split(ConstantsShared.protocolPlayerVariableSplit);
            return new PlayerInitialData(parts[0], parts[1]);
        } catch (Exception e) {
            throw new IOException("corrupted input\n" + e);
        }
    }

    public String toString() {
        String split = ConstantsShared.protocolPlayerVariableSplit;
        return name + split + playerModel;
    }
}
