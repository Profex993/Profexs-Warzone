package shared.weapon.weaponClasses;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public abstract class Weapon extends Weapon_Core {
    protected BufferedImage topImage, leftImage, rightImage, leftEmptyImage, rightEmptyImage, blastImage;
    protected URL soundFire, soundReload, soundReloadEmpty;
    protected boolean blastTrigger;
    private final boolean removeMagReloading, sameReloadSound;
    private int blastTick;
    protected boolean drawBlast;

    public Weapon(String name, int damage, boolean automatic, int desiredWidth, int desiredHeight, boolean removeMagReloading,
                  int fireDelay, int magazineSize, int reloadDelay, boolean sameReloadSound) {
        super(name, damage, automatic, desiredWidth, desiredHeight, fireDelay, magazineSize, reloadDelay);
        this.removeMagReloading = removeMagReloading;
        this.sameReloadSound = sameReloadSound;
        drawBlast = !automatic;
        getRes();
    }

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

    public URL getFireSound() {
        return soundFire;
    }

    public URL getReloadSound() {
        if (currentMagazineSize == 0 && !sameReloadSound) {
            return soundReloadEmpty;
        } else {
            return soundReload;
        }
    }

    public void draw(Graphics2D g2, String direction, int tick, double rotation, int screenX, int screenY) {
        if (automatic && blastTrigger) {
            drawBlast = !drawBlast;
        }
    }

    protected void getRes() {
        try {
            blastImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("prefab/muzzleBlast.png")));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

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

    public int getMagazineSize() {
        return magazineSize;
    }

    public int getCurrentMagazineSize() {
        return currentMagazineSize;
    }
}
