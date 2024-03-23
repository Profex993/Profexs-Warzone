package dataFormats;

public record PlayerInput(int x, int y) {

    public static PlayerInput parseFromString(String line) {
        String[] parts = line.split(" ");
        return new PlayerInput(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
    }
}
