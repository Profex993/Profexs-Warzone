package client.clientMain;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener {
    public boolean leftClick = false;

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftClick = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftClick = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public Rectangle getCursorRect() {
        return new Rectangle((int) getX() - 10, (int) getY() - 10, 20, 20);
    }

    public double getX() {
        return MouseInfo.getPointerInfo().getLocation().getX();
    }

    public double getY() {
        return MouseInfo.getPointerInfo().getLocation().getY();
    }
}
