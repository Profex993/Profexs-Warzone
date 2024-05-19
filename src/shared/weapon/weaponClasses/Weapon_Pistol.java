package shared.weapon.weaponClasses;

import java.awt.*;

public abstract class Weapon_Pistol extends Weapon {

    /**
     *
     * @param name String name, must be same as static String name
     * @param damage int damage
     * @param automatic boolean if weapon is automatic
     * @param desiredWidth int of width of the weapon
     * @param desiredHeight int of height of the weapon
     * @param removeMagReloading boolean if weapon removes magazine while reloading
     * @param fireDelay int of delay between shots if weapon is automatic
     * @param magazineSize int size of magazine
     * @param reloadDelay int of delay when reloading
     * @param sameReloadSound boolean if reload sound when weapons is empty is the same
     */
    public Weapon_Pistol(String name, int damage, boolean automatic, int desiredWidth, int desiredHeight,
                         boolean removeMagReloading, int fireDelay, int magazineSize, int reloadDelay, boolean sameReloadSound) {
        super(name, damage, automatic, desiredWidth, desiredHeight, removeMagReloading, fireDelay, magazineSize, reloadDelay, sameReloadSound);
    }

    /**
     * method for drawing
     * @param g2 Graphics
     * @param direction String representing direction of where is player looking
     * @param tick int of UpdateManager tick
     * @param rotation double rotation of where player is looking
     * @param screenX int of player screen location x
     * @param screenY int of player screen location y
     */
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
