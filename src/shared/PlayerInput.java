package shared;

public record PlayerInput(String id, int x, int y, String direction, String directionFace, boolean walking) {

    public static PlayerInput parseFromString(String line) {
        String[] parts = line.split(Constants.protocolPlayerVariableSplit);
        return new PlayerInput(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), parts[3], parts[4], Boolean.parseBoolean(parts[5]));
    }
}
