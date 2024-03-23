package server;

public class PlayerData {
    private final String id;
    private int x = 0, y = 0;

    public PlayerData(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
