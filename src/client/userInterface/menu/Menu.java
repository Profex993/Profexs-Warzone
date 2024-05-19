package client.userInterface.menu;

import client.clientMain.*;
import client.enums.GameStateClient;

import java.awt.*;
import java.util.ArrayList;

/**
 * class for pause menu
 */
public class Menu {
    private final ArrayList<MenuComponent> components = new ArrayList<>();

    /**
     * constructor initializes components of the menu
     * @param mouseHandler MouseHandler for updating components
     * @param core GameCore for accessing client components
     */
    public Menu(MouseHandler mouseHandler, GameCore core) {
        components.add(new MenuButton(20, 100, "Return to game", ConstantsClient.FONT_25, mouseHandler,
                () -> core.setGameState(GameStateClient.GAME)));
        components.add(new MenuButton(20, 150, "Exit", ConstantsClient.FONT_25, mouseHandler,
                () -> {
                    ClientMain.closeSocket();
                    System.exit(0);
                }));
    }

    /**
     * draw menu
     * @param g2 Graphics2D
     */
    public void draw(Graphics2D g2) {
        g2.setColor(ConstantsClient.TRANSPARENT_COLOR);
        g2.fillRect(0, 0, GamePanel.screenWidth, GamePanel.screenHeight);
        g2.setColor(Color.white);
        g2.setFont(ConstantsClient.FONT_50);
        g2.drawString("Menu", 20, 50);
        for (MenuComponent component : components) {
            component.draw(g2);
        }
    }

    /**
     * update menu each tick
     */
    public void update() {
        for (MenuComponent component : components) {
            component.update();
        }
    }
}
