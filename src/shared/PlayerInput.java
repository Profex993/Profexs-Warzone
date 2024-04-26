package shared;

import server.entity.PlayerServerSide;

import java.io.IOException;

public record PlayerInput(String id, int x, int y, String direction, String directionFace, int walkAnimNum,
                          double mouseX, double mouseY, boolean shooting, boolean reloading, String weapon) {

    public static PlayerInput parseFromString(String line) throws IOException {
        try {
            String[] parts = line.split(Constants.protocolPlayerVariableSplit);
            return new PlayerInput(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), parts[3], parts[4],
                    Integer.parseInt(parts[5]), Double.parseDouble(parts[6]), Double.parseDouble(parts[7]), Boolean.parseBoolean(parts[8]),
                    Boolean.parseBoolean(parts[9]), parts[10]);
        } catch (Exception e) {
            throw new IOException("corrupted input\n" + e);
        }
    }

    public static PlayerInput getFromPlayerData(PlayerServerSide player) {
        return new PlayerInput(player.getId(), player.getWorldX(), player.getWorldY(), player.getDirection(), player.getDirectionFace(),
                player.getWalkAnimNum(), player.getMouseX(), player.getMouseY(), player.isShooting(), player.isWeaponReloading(), player.getWeaponName());
    }

    public String getString() {
        String split = Constants.protocolPlayerVariableSplit;
        return id + split + x + split + y + split + direction + split + directionFace + split + walkAnimNum + split + mouseX + split + mouseY
                + split + shooting + split + reloading + split + weapon;
    }
}
