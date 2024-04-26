package shared;

import java.awt.*;

public abstract class Constants {
    public static final int playerSpeed = 4, projectileSpeed = 40, playerWidth = 40, playerHeight = 52;
    public static final String protocolPlayerLineEnd = ";";
    public static final String protocolPlayerVariableSplit = ",";
    public static final Font font25 = new Font("arial", Font.BOLD, 25),
            font10 = new Font("arial", Font.BOLD, 10);
    public static final Color transparentColor = new Color(25, 25, 25, 150);
}
