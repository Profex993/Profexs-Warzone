package shared;

import server.PlayerServerSide;

public record ServerOutputToClient(int x, int y, String direction, String directionFace, int walkAnimNum) {
    public static ServerOutputToClient parseFromString(String line) {
        String[] parts = line.split(Constants.protocolPlayerVariableSplit);
        return new ServerOutputToClient(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), parts[2], parts[3],
                Integer.parseInt(parts[4]));
    }

    public static ServerOutputToClient getFromPlayerData(PlayerServerSide player) {
        return new ServerOutputToClient(player.getWorldX(), player.getWorldY(), player.getDirection(), player.getDirectionFace(),
                player.getWalkAnimNum());
    }

    public String toString() {
        String split = Constants.protocolPlayerVariableSplit;
        return x + split + y + split + direction + split + directionFace + split + walkAnimNum;
    }
}
