package shared.weapon.weaponClasses;

import java.awt.*;
import java.awt.geom.AffineTransform;

public abstract class Weapon_Rifle extends Weapon {

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
    public Weapon_Rifle(String name, int damage, boolean automatic, int desiredWidth, int desiredHeight, boolean removeMagReloading,
                        int fireDelay, int magazineSize, int reloadDelay, boolean sameReloadSound) {
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
                    screenY += 10;
                } else {
                    screenY += 12;
                }
            }
            case "right" -> {
                if (drawBlast && blastTrigger) {
                    screenX += 8;
                } else {
                    screenX += 10;
                }
                screenY += 20;
            }
            case "left" -> {
                if (drawBlast && blastTrigger) {
                    screenX += 38;
                } else {
                    screenX += 35;
                }
                screenY += 20;
            }
            case "up" -> {
                screenX += 30;
                if (drawBlast && blastTrigger) {
                    screenY += 28;
                } else {
                    screenY += 25;
                }
            }
        }

        drawCommon(g2, screenX, screenY, direction, tick, rotation);

        if (blastTrigger && drawBlast) {
            AffineTransform transform2 = getBlastImgRotation(screenX, screenY, direction, rotation);
            g2.drawImage(blastImage, transform2, null);
        }
    }

    /**
     * method for getting AffineTransform of blast
     * @param weaponX int x location of weapon
     * @param weaponY int y location of weapon
     * @param direction String representing direction
     * @param rotation double rotation of weapon
     * @return AffineTransform of blast
     */
    private AffineTransform getBlastImgRotation(int weaponX, int weaponY, String direction, double rotation) {
        int blastX = weaponX;
        int blastY = weaponY;
        int xBarrel = 0;
        int yBarrel = 0;

        double blastOffsetX = Math.cos(rotation) * (desiredWidth - 2);
        double blastOffsetY = Math.sin(rotation) * (desiredWidth - 1);

        switch (direction) {
            case "left", "right" -> yBarrel = -1;
            case "up" -> {
                xBarrel = -2;
                yBarrel = 6;
            }
            case "down" -> {
                xBarrel = 2;
                yBarrel = 7;
            }
        }

        blastX += (int) blastOffsetX;
        blastY += (int) blastOffsetY;

        AffineTransform transform2 = new AffineTransform();
        transform2.translate(blastX + xBarrel, blastY + yBarrel);
        transform2.rotate(rotation, 0, 8);
        return transform2;
    }
}
