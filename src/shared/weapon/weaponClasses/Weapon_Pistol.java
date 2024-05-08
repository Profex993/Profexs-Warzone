package shared.weapon.weaponClasses;

import java.awt.*;

public abstract class Weapon_Pistol extends Weapon {
    public Weapon_Pistol(String name, int damage, boolean automatic, int desiredWidth, int desiredHeight,
                         boolean removeMagReloading, int fireDelay, int magazineSize, int reloadDelay, boolean sameReloadSound) {
        super(name, damage, automatic, desiredWidth, desiredHeight, removeMagReloading, fireDelay, magazineSize, reloadDelay, sameReloadSound);
    }

    public void draw(Graphics2D g2, String direction, int tick, double rotation, int screenX, int screenY) {
        super.draw(g2, direction, tick, rotation, screenX, screenY);
        switch (direction) {
            case "down" -> {
                screenX += 8;
                if (drawBlast && blastTrigger) {
                    screenY += 3;
                } else {
                    screenY += 5;
                }
            }
            case "right" -> {
                if (drawBlast && blastTrigger) {
                    screenX -= 2;
                }
                screenY += 20;
            }
            case "left" -> {
                if (drawBlast && blastTrigger) {
                    screenX += 37;
                } else {
                    screenX += 35;
                }
                screenY -= 10;
            }
            case "up" -> {
                if (drawBlast && blastTrigger) {
                    screenY += 7;
                } else {
                    screenY += 5;
                }
                screenX += 30;
            }
        }

        drawCommon(g2, screenX, screenY, direction, tick, rotation);
    }
}
