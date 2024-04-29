package shared.packets;

import server.entity.PlayerServerSide;
import shared.ConstantsShared;

import java.io.IOException;

public record ServerOutputToClient(int x, int y, String directionFace, boolean walking, String weapon, int health, boolean death) {
    public static ServerOutputToClient parseFromString(String line) throws IOException {
        try {
            String[] parts = line.split(ConstantsShared.protocolPlayerVariableSplit);
            return new ServerOutputToClient(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), parts[2],
                    Boolean.parseBoolean(parts[3]), parts[4] , Integer.parseInt(parts[5]), Boolean.parseBoolean(parts[6]));
        } catch (Exception e) {
            throw new IOException("corrupted input\n" + e);
        }
    }

    public static ServerOutputToClient getFromPlayerData(PlayerServerSide player) {
        return new ServerOutputToClient(player.getWorldX(), player.getWorldY(), player.getDirectionFace(),
                player.isWalking(), player.getWeaponName(), player.getHealth(), player.isDeath());
    }

    public String toString() {
        String split = ConstantsShared.protocolPlayerVariableSplit;
        return x + split + y + split + directionFace + split + walking + split + weapon + split + health + split + death;
    }
}
