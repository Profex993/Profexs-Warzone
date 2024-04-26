package client.userInterface.menu;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class PlayerModelMenu {
    private BufferedImage walk1Down, walk2Down, walk3Down;

    public void draw(Graphics2D g2, int walkAnimNum) {
        g2.drawImage(getImage(walkAnimNum), 50, 20, 300, 400, null);
    }

    public void setPlayerImage(String dir) {
        try {
            walk1Down = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("entity/" + dir + "/player3Walk1.png")));
            walk2Down = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("entity/" + dir + "/player3Walk2.png")));
            walk3Down = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("entity/" + dir + "/player3Walk3.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private BufferedImage getImage(int walkAnimNum) {
        if (walkAnimNum == 1) {
            return walk1Down;
        } else if (walkAnimNum == 2) {
            return walk2Down;
        } else if (walkAnimNum == 3) {
            return walk3Down;
        } else {
            return walk2Down;
        }
    }
}
