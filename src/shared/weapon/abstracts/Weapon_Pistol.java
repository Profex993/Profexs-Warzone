package shared.weapon.abstracts;

import java.awt.*;

public abstract class Weapon_Pistol extends Weapon {
    public Weapon_Pistol(String name, int damage, boolean automatic, int desiredWidth, int desiredHeight,
                         boolean removeMagReloading, int fireDelay, int magazineSize, int reloadDelay, boolean sameReloadSound) {
        super(name, damage, automatic, desiredWidth, desiredHeight, removeMagReloading, fireDelay, magazineSize, reloadDelay, sameReloadSound);
    }

    public void draw(Graphics2D g2, String direction, int screenX, int screenY, int targetX, int targetY, int tick) {
        switch (direction) {
            case "down" -> {
                screenX += 8;
                screenY += 5;
            }
            case "right" -> screenY += 20;
            case "left" -> {
                screenX += 35;
                screenY -= 10;
            }
            case "up" -> {
                screenX += 30;
                screenY += 5;
            }
        }

        drawCommon(g2, screenX, screenY, targetX, targetY, screenX, screenY, direction, tick);
    }
}
