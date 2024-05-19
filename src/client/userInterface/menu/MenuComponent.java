package client.userInterface.menu;

import java.awt.*;

/**
 * menu components
 */
public interface MenuComponent {
    /**
     * draw components
     * @param g2 Graphics2D
     */
    void draw(Graphics2D g2);

    /**
     * update component each tick
     */
    void update();
}
