package shared;

import server.entity.PlayerServerSide;

import java.io.IOException;

public record ServerOutputToClient(int x, int y, String directionFace, boolean walking, String weapon) {
    public static ServerOutputToClient parseFromString(String line) throws IOException {
        try {
            String[] parts = line.split(Constants.protocolPlayerVariableSplit);
            return new ServerOutputToClient(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), parts[2],
                    Boolean.parseBoolean(parts[3]), parts[4]);
        } catch (Exception e) {
            throw new IOException("corrupted input\n" + e);
        }
    }

    public static ServerOutputToClient getFromPlayerData(PlayerServerSide player) {
        return new ServerOutputToClient(player.getWorldX(), player.getWorldY(), player.getDirectionFace(),
                player.isWalking(), player.getWeaponName());
    }

    public String toString() {
        String split = Constants.protocolPlayerVariableSplit;
        return x + split + y + split + directionFace + split + walking + split + weapon;
    }
}
