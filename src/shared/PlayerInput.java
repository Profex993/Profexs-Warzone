package shared;

import server.PlayerServerSide;

public record PlayerInput(String id, int x, int y, String direction, String directionFace, int walkAnimNum) {

    public static PlayerInput parseFromString(String line) {
        String[] parts = line.split(Constants.protocolPlayerVariableSplit);
        return new PlayerInput(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), parts[3], parts[4],
                Integer.parseInt(parts[5]));
    }

    public static PlayerInput getFromPlayerData(PlayerServerSide player) {
        return new PlayerInput(player.getId(), player.getWorldX(), player.getWorldY(), player.getDirection(), player.getDirectionFace(),
                player.getWalkAnimNum());
    }

    public String getString() {
        String split = Constants.protocolPlayerVariableSplit;
        return id + split + x + split + y + split + direction + split + directionFace + split + walkAnimNum;
    }
}
