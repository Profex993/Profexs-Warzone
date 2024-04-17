package shared;

import server.entity.PlayerServerSide;

public record PlayerInput(String id, int x, int y, String direction, String directionFace, int walkAnimNum,
                          double mouseX, double mouseY, boolean shooting) {

    public static PlayerInput parseFromString(String line) {
        String[] parts = line.split(Constants.protocolPlayerVariableSplit);
        return new PlayerInput(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), parts[3], parts[4],
                Integer.parseInt(parts[5]), Double.parseDouble(parts[6]), Double.parseDouble(parts[7]), Boolean.parseBoolean(parts[8]));
    }

    public static PlayerInput getFromPlayerData(PlayerServerSide player) {
        return new PlayerInput(player.getId(), player.getWorldX(), player.getWorldY(), player.getDirection(), player.getDirectionFace(),
                player.getWalkAnimNum(), player.getMouseX(), player.getMouseY(), player.isShooting());
    }

    public String getString() {
        String split = Constants.protocolPlayerVariableSplit;
        return id + split + x + split + y + split + direction + split + directionFace + split + walkAnimNum + split + mouseX + split + mouseY
                + split + shooting;
    }
}
