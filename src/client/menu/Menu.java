package client.menu;

import client.clientMain.ClientMain;
import client.clientMain.GameCore;
import client.clientMain.GamePanel;
import client.clientMain.MouseHandler;

import java.awt.*;
import java.util.ArrayList;

public class Menu {
    private final ArrayList<MenuComponent> components = new ArrayList<>();
    private final Font pauseFont = new Font("arial", Font.BOLD, 50);

    public Menu(MouseHandler mouseHandler) {
        components.add(new MenuButton(20, 100, "Return to game", new Font("arial", Font.PLAIN, 20), mouseHandler,
                () -> {
                    GameCore.changeGameState(0);
                    System.out.println("test");
                }));
        components.add(new MenuButton(20, 130, "Exit", new Font("arial", Font.PLAIN, 20), mouseHandler,
                () -> {
                    ClientMain.closeSocket();
                    System.exit(0);
                }));
    }

    public void draw(Graphics2D g2) {
        g2.setColor(new Color(100, 100, 100, 128));
        g2.fillRect(0, 0, GamePanel.screenWidth, GamePanel.screenHeight);
        g2.setColor(Color.white);
        g2.setFont(pauseFont);
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
