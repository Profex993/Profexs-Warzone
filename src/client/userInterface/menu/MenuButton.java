package client.userInterface.menu;

import client.clientMain.MouseHandler;

import java.awt.*;

public class MenuButton implements MenuComponent {
    private final Rectangle triggerArea;
    private final String text;
    private final Font font;
    private final MouseHandler mouseHandler;
    private final Runnable onCLick;

    public MenuButton(int x, int y, String text, Font font, MouseHandler mouseHandler, Runnable onCLick) {
        this.font = font;
        this.text = text;
        int width = (int) (text.length() * font.getSize() / 1.75);
        this.triggerArea = new Rectangle(x, y, width, (int) (font.getSize() * 1.25));
        this.mouseHandler = mouseHandler;
        this.onCLick = onCLick;
    }

    public void update() {
        if (mouseHandler.leftClick && mouseHandler.getCursorRect().intersects(triggerArea)) {
            onCLick.run();
        }
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.white);
        g2.setFont(font);
        g2.fill(triggerArea);
        g2.setColor(Color.black);
        g2.drawString(text, triggerArea.x, triggerArea.y + font.getSize());
    }
}
