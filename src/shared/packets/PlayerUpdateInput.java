package shared.packets;

import server.entity.PlayerServerSide;
import shared.ConstantsShared;

import java.io.IOException;

public record PlayerUpdateInput(String id, int x, int y, String directionFace, boolean walking, double mouseX, double mouseY,
                                boolean shooting, boolean reloading, String weapon, boolean death) {

    public static PlayerUpdateInput parseFromString(String line) throws IOException {
        try {
            String[] parts = line.split(ConstantsShared.protocolPlayerVariableSplit);
            return new PlayerUpdateInput(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), parts[3],
                    Boolean.parseBoolean(parts[4]), Double.parseDouble(parts[5]), Double.parseDouble(parts[6]),
                    Boolean.parseBoolean(parts[7]), Boolean.parseBoolean(parts[8]), parts[9], Boolean.parseBoolean(parts[10]));
        } catch (Exception e) {
            throw new IOException("corrupted input\n" + e);
        }
    }

    public static PlayerUpdateInput getFromPlayerData(PlayerServerSide player) {
        return new PlayerUpdateInput(player.getId(), player.getWorldX(), player.getWorldY(), player.getDirectionFace(),
                player.isWalking(), player.getMouseX(), player.getMouseY(), player.isShooting(), player.isWeaponReloading(),
                player.getWeaponName(), player.isDeath());
    }

    public String getString() {
        String split = ConstantsShared.protocolPlayerVariableSplit;
        return id + split + x + split + y + split + directionFace + split + walking + split + mouseX + split + mouseY
                + split + shooting + split + reloading + split + weapon + split + death;
    }
}
