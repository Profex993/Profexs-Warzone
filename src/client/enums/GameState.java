package client.enums;

public enum GameState {
    GAME(0),
    PAUSED(1);

    public final int intValue;

    GameState(int intValue) {
        this.intValue = intValue;
    }
}
