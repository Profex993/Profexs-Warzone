package shared;

public record PlayerInput(int x, int y, String id) {

    public static PlayerInput parseFromString(String line) {
        String[] parts = line.split(" ");
        return new PlayerInput(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), parts[2]);
    }
}
