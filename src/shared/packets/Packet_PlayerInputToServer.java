package shared.packets;

import client.clientMain.KeyHandler;
import client.clientMain.MouseHandler;
import shared.ConstantsShared;

import java.io.IOException;

/**
 * packet containing information about main player from client to server
 * @param up boolean is player walking up
 * @param down boolean is player walking down
 * @param left boolean is player walking left
 * @param right boolean is player walking right
 * @param mouseX double mouse position x
 * @param mouseY double mouse position y
 * @param screenX int screen position of player
 * @param screenY int screen position of player
 * @param leftCLick boolean if player left clicks
 * @param reload boolean if player reloads
 * @param rightClick if player right clicks
 */
public record Packet_PlayerInputToServer(boolean up, boolean down, boolean left, boolean right, double mouseX, double mouseY,
                                         int screenX, int screenY, boolean leftCLick, boolean reload, boolean rightClick) {
    public static Packet_PlayerInputToServer parseString(String line) throws IOException {
        try {
            String[] parts = line.split(ConstantsShared.PROTOCOL_VARIABLE_SPLIT);
            return new Packet_PlayerInputToServer(Boolean.parseBoolean(parts[0]), Boolean.parseBoolean(parts[1]), Boolean.parseBoolean(parts[2]),
                    Boolean.parseBoolean(parts[3]), Double.parseDouble(parts[4]), Double.parseDouble(parts[5]), Integer.parseInt(parts[6]),
                    Integer.parseInt(parts[7]), Boolean.parseBoolean(parts[8]), Boolean.parseBoolean(parts[9]), Boolean.parseBoolean(parts[10]));
        } catch (Exception e) {
            throw new IOException("corrupted input\n" + e);
        }
    }

    public static Packet_PlayerInputToServer getFromPlayerInput(KeyHandler keyHandler, MouseHandler mouseHandler, int screenX, int screenY) {
        return new Packet_PlayerInputToServer(keyHandler.up, keyHandler.down, keyHandler.left, keyHandler.right,
                mouseHandler.getX(), mouseHandler.getY(), screenX, screenY, mouseHandler.isShooting(), keyHandler.reload, mouseHandler.rightClick);
    }

    public String toString() {
        String split = ConstantsShared.PROTOCOL_VARIABLE_SPLIT;
        return up + split + down + split + left + split + right + split + mouseX + split + mouseY + split + screenX + split
                + screenY + split + leftCLick + split + reload + split + rightClick;
    }
}
