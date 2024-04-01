package server;

import shared.PlayerInput;

public class PlayerData {
    private final String id;
    private final String playerModel;
    private int x = 0, y = 0;
    private String direction, directionFace;
    private boolean walking = false;

    public PlayerData(String id, String playerModel) {
        this.id = id;
        this.playerModel = playerModel;
    }

    public String serverOutput() {
        return id + "," + x + "," + y + "," + direction + "," + directionFace + "," + walking;
    }

    public void updateFromPlayerInput(PlayerInput playerInput) {
        this.x = playerInput.x();
        this.y = playerInput.y();
        this.direction = playerInput.direction();
        this.directionFace = playerInput.directionFace();
        this.walking = playerInput.walking();
    }

    public String getId() {
        return id;
    }

    public String getPlayerModel() {
        return playerModel;
    }

    @Override
    public String toString() {
        return "id: " + id;
    }
}
