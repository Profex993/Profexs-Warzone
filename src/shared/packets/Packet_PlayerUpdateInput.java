package shared.packets;

import server.entity.PlayerServerSide;
import shared.ConstantsShared;

import java.io.IOException;

public record Packet_PlayerUpdateInput(String id, int x, int y, String directionFace, boolean walking, double rotation,
                                       boolean shooting, boolean reloading, String weapon, boolean death, int kills, int deaths) {
    public final static String head = "updatePlayer";

    public static Packet_PlayerUpdateInput parseFromString(String line) throws IOException {
        try {
            String[] parts = line.split(ConstantsShared.PROTOCOL_VARIABLE_SPLIT);
            return new Packet_PlayerUpdateInput(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), parts[3],
                    Boolean.parseBoolean(parts[4]), Double.parseDouble(parts[5]), Boolean.parseBoolean(parts[6]),
                    Boolean.parseBoolean(parts[7]), parts[8], Boolean.parseBoolean(parts[9]),
                    Integer.parseInt(parts[10]), Integer.parseInt(parts[11]));
        } catch (Exception e) {
            throw new IOException("corrupted input\n" + e);
        }
    }

    public static Packet_PlayerUpdateInput getFromPlayerData(PlayerServerSide player) {
        return new Packet_PlayerUpdateInput(player.getName(), player.getWorldX(), player.getWorldY(), player.getDirectionFace(),
                player.isWalking(), player.getRotation(), player.isShooting(), player.isWeaponReloading(),
                player.getWeaponName(), player.isDeath(), player.getKills(), player.getDeaths());
    }

    public String toString() {
        String split = ConstantsShared.PROTOCOL_VARIABLE_SPLIT;
        return head + ConstantsShared.PROTOCOL_LINE_SPLIT + id + split + x + split + y + split + directionFace + split + walking + split + rotation + split +
                shooting + split + reloading + split + weapon + split + death + split + kills + split + deaths;
    }
}
