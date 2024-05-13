package shared.packets;

import server.entity.PlayerServerSide;
import shared.ConstantsShared;

import java.io.IOException;

public record Packet_ServerOutputToClient(int x, int y, String directionFace, boolean walking, String weapon, int health,
                                          boolean death, String killedBy, int kills, int deaths) {

    public static Packet_ServerOutputToClient parseFromString(String line) throws IOException {
        try {
            String[] parts = line.split(ConstantsShared.PROTOCOL_VARIABLE_SPLIT);
            return new Packet_ServerOutputToClient(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), parts[2],
                    Boolean.parseBoolean(parts[3]), parts[4], Integer.parseInt(parts[5]), Boolean.parseBoolean(parts[6]), parts[7],
                    Integer.parseInt(parts[8]), Integer.parseInt(parts[9]));
        } catch (Exception e) {
            throw new IOException("corrupted input\n" + e);
        }
    }

    public static Packet_ServerOutputToClient getFromPlayerData(PlayerServerSide player) {
        return new Packet_ServerOutputToClient(player.getWorldX(), player.getWorldY(), player.getDirectionFace(),
                player.isWalking(), player.getWeaponName(), player.getHealth(), player.isDeath(), player.getKilledBy(),
                player.getKills(), player.getDeaths());
    }

    public String toString() {
        String split = ConstantsShared.PROTOCOL_VARIABLE_SPLIT;
        return x + split + y + split + directionFace + split + walking + split + weapon + split + health + split + death + split + killedBy
                + split + kills + split + deaths;
    }
}
