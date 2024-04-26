package shared;

import server.entity.PlayerServerSide;

import java.io.IOException;

public record ServerOutputToClient(int x, int y, String direction, String directionFace, boolean walking, String weapon) {
    public static ServerOutputToClient parseFromString(String line) throws IOException {
        try {
            String[] parts = line.split(Constants.protocolPlayerVariableSplit);
            return new ServerOutputToClient(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), parts[2], parts[3],
                    Boolean.parseBoolean(parts[4]), parts[5]);
        } catch (Exception e) {
            throw new IOException("corrupted input\n" + e);
        }
    }

    public static ServerOutputToClient getFromPlayerData(PlayerServerSide player) {
        return new ServerOutputToClient(player.getWorldX(), player.getWorldY(), player.getDirection(), player.getDirectionFace(),
                player.isWalking(), player.getWeaponName());
    }

    public String toString() {
        String split = Constants.protocolPlayerVariableSplit;
        return x + split + y + split + direction + split + directionFace + split + walking + split + weapon;
    }
}
