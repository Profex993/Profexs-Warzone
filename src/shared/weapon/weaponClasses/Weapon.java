package shared.weapon.weaponClasses;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

/**
 * class for weapons, individual weapon must extend Weapon_Pistol or Weapon_Rifle depending on how the weapon appears
 * individual weapon class also has to be placed into shared.weapon package, and named Weapon_*name* so it can be generated by WeaponGenerator
 * individual class also has to have public static method named getServerSideWeapon returning object of Weapon_Core
 * which has to be filled with the same data as same original weapon constructor
 * and static String name which has to be the name of the weapon after the underscore sign, like Weapon_AK -> name = AK
 * individual also has to override getRes method and add images of weapon (leftEmpty image is image of weapon without magazine),
 * the method should also super the old method for loading the blast image
 */
public abstract class Weapon extends Weapon_Core {
    protected BufferedImage topImage, leftImage, rightImage, leftEmptyImage, rightEmptyImage, blastImage;
    protected URL soundFire, soundReload, soundReloadEmpty;
    protected boolean blastTrigger;
    private final boolean removeMagReloading, sameReloadSound;
    private int blastTick;
    protected boolean drawBlast;

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
    public Weapon(String name, int damage, boolean automatic, int desiredWidth, int desiredHeight, boolean removeMagReloading,
                  int fireDelay, int magazineSize, int reloadDelay, boolean sameReloadSound) {
        super(name, damage, automatic, desiredWidth, desiredHeight, fireDelay, magazineSize, reloadDelay);
        this.removeMagReloading = removeMagReloading;
        this.sameReloadSound = sameReloadSound;
        drawBlast = !automatic;
        getRes();
    }

    /**
     * method trigger blast animation and if automatic, set lock
     * @param currentTick int UpdateManager tick
     * @return returns true if blast was triggered
     */
    public boolean triggerBlast(int currentTick) {
        if ((!automatic || currentFireLock < currentTick) && currentMagazineSize > 0 && !reloading) {
            currentFireLock = currentTick + fireDelay;
            currentMagazineSize--;

            this.blastTick = currentTick + 16;
            this.blastTrigger = true;
            return true;
        }
        return false;
    }

    /**
     * get sound of reload
     * @return URL of reload sound
     */
    public URL getReloadSound() {
        if (currentMagazineSize == 0 && !sameReloadSound) {
            return soundReloadEmpty;
        } else {
            return soundReload;
        }
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
        if (automatic && blastTrigger) {
            drawBlast = !drawBlast;
        }
    }

    /**
     * load resources of weapon
     */
    protected void getRes() {
        try {
            blastImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("prefab/muzzleBlast.png")));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * get current weapon image
     * @param direction direction to determine what image to pick
     * @return BufferedImage of weapon
     */
    protected BufferedImage getImage(String direction) {
        switch (direction) {
            case "up", "down" -> {
                return topImage;
            }
            case "left" -> {
                if (removeMagReloading) {
                    if (!reloading) {
                        return leftImage;
                    } else {
                        return leftEmptyImage;
                    }
                } else {
                    return leftImage;
                }
            }
            case "right" -> {
                if (removeMagReloading) {
                    if (!reloading) {
                        return rightImage;
                    } else {
                        return rightEmptyImage;
                    }
                } else {
                    return rightImage;
                }
            }
            default -> {
                return null;
            }
        }
    }

    /**
     * method for drawing which is used over
     * @param g2 Graphics
     * @param weaponX int where weapon is located x
     * @param weaponY int where weapon is located y
     * @param direction String representing direction where player is looking
     * @param tick int UpdateManager tick
     * @param rotation double rotation where player is looking
     */
    protected void drawCommon(Graphics2D g2, int weaponX, int weaponY, String direction, int tick, double rotation) {
        BufferedImage img = getImage(direction);
        if (img != null) {
            AffineTransform transform = new AffineTransform();
            transform.translate(weaponX, weaponY);
            transform.rotate(rotation, 0, (double) img.getHeight() / 2);
            transform.scale(desiredWidth / (double) img.getWidth(), desiredHeight / (double) img.getHeight());

            g2.drawImage(img, transform, null);

            if (blastTrigger && tick > blastTick) {
                blastTrigger = false;
            }
        }
    }

    public BufferedImage getRightImage() {
        return rightImage;
    }

    public URL getFireSound() {
        return soundFire;
    }

    public int getMagazineSize() {
        return magazineSize;
    }

    public int getCurrentMagazineSize() {
        return currentMagazineSize;
    }
}
