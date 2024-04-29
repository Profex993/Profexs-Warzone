package client.userInterface;

import client.clientMain.Constants;
import client.entity.MainPlayer;

import java.awt.*;

public class GameUI {
    private final int screenWidth, screenHeight;
    private final MainPlayer player;

    public GameUI(MainPlayer player, int screenWidth, int screenHeight) {
        this.player = player;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.red);
        g2.setFont(Constants.font25);
        g2.drawString(String.valueOf(player.getHealth()), 30, screenHeight - 60);
        g2.fillRect(80, screenHeight - 75, player.getHealth(), 15);

        if (player.getWeapon() != null) {
            g2.setColor(Constants.transparentColor);
            g2.fillRect(screenWidth - 250, screenHeight - 140, 230, 120);
            g2.setColor(Color.red);
            g2.drawString(player.getWeapon().getName(), screenWidth - 230, screenHeight - 120);
            g2.drawString(player.getWeapon().getCurrentMagazineSize() + "/" + player.getWeapon().getMagazineSize(), screenWidth - 230, screenHeight - 90);
            g2.drawImage(player.getWeapon().getRightImage(), screenWidth - 230, screenHeight - 70, 180, 45, null);
        }
    }
}
