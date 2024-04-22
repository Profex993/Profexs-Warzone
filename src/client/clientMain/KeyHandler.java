package client.clientMain;

import client.enums.GameState;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean up, down, left, right, reload;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
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
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if (GameCore.gameState == GameState.GAME.intValue) {
                GameCore.changeGameState(GameState.PAUSED.intValue);
            } else {
                GameCore.changeGameState(GameState.GAME.intValue);
            }
        }
    }

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
        }
    }
}
