package shared;

import server.entity.PlayerServerSide;

import java.io.IOException;

public record PlayerInput(String id, int x, int y, String directionFace, boolean walking,
                          double mouseX, double mouseY, boolean shooting, boolean reloading, String weapon) {

    public static PlayerInput parseFromString(String line) throws IOException {
        try {
            String[] parts = line.split(Constants.protocolPlayerVariableSplit);
            return new PlayerInput(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), parts[3],
                    Boolean.parseBoolean(parts[4]), Double.parseDouble(parts[5]), Double.parseDouble(parts[6]),
                    Boolean.parseBoolean(parts[7]), Boolean.parseBoolean(parts[8]), parts[9]);
        } catch (Exception e) {
            throw new IOException("corrupted input\n" + e);
        }
    }

    public static PlayerInput getFromPlayerData(PlayerServerSide player) {
        return new PlayerInput(player.getId(), player.getWorldX(), player.getWorldY(), player.getDirectionFace(),
                player.isWalking(), player.getMouseX(), player.getMouseY(), player.isShooting(), player.isWeaponReloading(), player.getWeaponName());
    }

    public String getString() {
        String split = Constants.protocolPlayerVariableSplit;
        return id + split + x + split + y + split + directionFace + split + walking + split + mouseX + split + mouseY
                + split + shooting + split + reloading + split + weapon;
    }
}
