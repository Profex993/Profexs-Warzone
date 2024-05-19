package client.clientMain;

import client.enums.GameStateClient;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * class for handling keyboard input
 */
public class KeyHandler implements KeyListener {
    public boolean up, down, left, right, reload, leaderBoard;
    private final GameCore core;
    private GameStateClient tempGameState;
    public KeyHandler(GameCore core) {
        this.core = core;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * check what key was pressed
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (core.getGameState() == GameStateClient.GAME) {
            if (e.getKeyCode() == KeyEvent.VK_W) {
                up = true;
            } else if (e.getKeyCode() == KeyEvent.VK_S) {
                down = true;
            } else if (e.getKeyCode() == KeyEvent.VK_A) {
                left = true;
            } else if (e.getKeyCode() == KeyEvent.VK_D) {
                right = true;
            } else if (e.getKeyCode() == KeyEvent.VK_R) {
                reload = true;
            } else if (e.getKeyCode() == KeyEvent.VK_T) {
                leaderBoard = true;
            }
        }

        //opening menu
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if (core.getGameState() == GameStateClient.GAME || core.getGameState() == GameStateClient.MATCH_END) {
                tempGameState = core.getGameState();
                core.setGameState(GameStateClient.PAUSED);
            } else {
                core.setGameState(tempGameState);
            }
        }
    }

    /**
     * check what key was released
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            up = false;
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            down = false;
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            left = false;
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            right = false;
        } else if (e.getKeyCode() == KeyEvent.VK_R) {
            reload = false;
        } else if (e.getKeyCode() == KeyEvent.VK_T) {
            leaderBoard = false;
        }
    }
}
