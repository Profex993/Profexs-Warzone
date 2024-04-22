package shared;

import client.clientMain.KeyHandler;
import client.clientMain.MouseHandler;

public record PlayerInputToServer(boolean up, boolean down, boolean left, boolean right, double mouseX, double mouseY,
                                  int screenX, int screenY, boolean leftCLick, boolean reload) {
    public String toString() {
        return up + "," + down + "," + left + "," + right + "," + mouseX + "," + mouseY + "," + screenX + "," + screenY + "," + leftCLick
                + "," + reload;
    }

    public static PlayerInputToServer parseString(String line) {
        String[] parts = line.split(Constants.protocolPlayerVariableSplit);
        return new PlayerInputToServer(Boolean.parseBoolean(parts[0]), Boolean.parseBoolean(parts[1]), Boolean.parseBoolean(parts[2]),
                Boolean.parseBoolean(parts[3]), Double.parseDouble(parts[4]), Double.parseDouble(parts[5]), Integer.parseInt(parts[6]),
                Integer.parseInt(parts[7]), Boolean.parseBoolean(parts[8]), Boolean.parseBoolean(parts[9]));
    }

    public static PlayerInputToServer getFromPlayerInput(KeyHandler keyHandler, MouseHandler mouseHandler, int screenX, int screenY) {
        return new PlayerInputToServer(keyHandler.up, keyHandler.down, keyHandler.left, keyHandler.right,
                mouseHandler.getX(), mouseHandler.getY(), screenX, screenY, mouseHandler.leftClick, keyHandler.reload);
    }
}
