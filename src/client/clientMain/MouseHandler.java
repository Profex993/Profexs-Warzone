package client.clientMain;

import client.enums.GameStateClient;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * class for handling mouse input
 */
public class MouseHandler implements MouseListener {
    public boolean leftClick = false, rightClick = false;
    private final GameCore core;

    /**
     * @param core for checking game state
     */
    public MouseHandler(GameCore core) {
        this.core = core;
    }

    /**
     * method for checking which key was pressed
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftClick = true;
        }
        if (e.getButton() == MouseEvent.BUTTON3) {
            rightClick = true;
        }
    }

    /**
     * method for checking which key was released
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftClick = false;
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            rightClick = false;
        }
    }

    /**
     * get small rectangle around cursor
     * @return Rectangle
     */
    public Rectangle getCursorRect() {
        return new Rectangle((int) getX() - 10, (int) getY() - 10, 20, 20);
    }

    public double getX() {
        return MouseInfo.getPointerInfo().getLocation().getX();
    }

    public double getY() {
        return MouseInfo.getPointerInfo().getLocation().getY();
    }

    /**
     * method for checking if player is shooting, player can shoot if core gameState is GameStateClient.GAME
     * @return returns true if player can shoot
     */
    public boolean isShooting() {
        if (core.getGameState() == GameStateClient.GAME) {
            return leftClick;
        } else {
            return false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }
}
