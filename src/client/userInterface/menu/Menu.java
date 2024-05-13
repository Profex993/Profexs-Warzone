package client.userInterface.menu;

import client.clientMain.*;
import client.enums.GameStateClient;

import java.awt.*;
import java.util.ArrayList;

public class Menu {
    private final ArrayList<MenuComponent> components = new ArrayList<>();

    public Menu(MouseHandler mouseHandler, GameCore core) {
        components.add(new MenuButton(20, 100, "Return to game", Constants.font25, mouseHandler,
                () -> core.setGameState(GameStateClient.GAME)));
        components.add(new MenuButton(20, 150, "Exit", Constants.font25, mouseHandler,
                () -> {
                    ClientMain.closeSocket();
                    System.exit(0);
                }));
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Constants.transparentColor);
        g2.fillRect(0, 0, GamePanel.screenWidth, GamePanel.screenHeight);
        g2.setColor(Color.white);
        g2.setFont(Constants.font50);
        g2.drawString("Menu", 20, 50);
        for (MenuComponent component : components) {
            component.draw(g2);
        }
    }

    public void update() {
        for (MenuComponent component : components) {
            component.update();
        }
    }
}
