package shared.weapon.abstracts;

import client.clientMain.SoundManager;
import shared.weapon.Weapon_Core;

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
    protected double rotation;
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

    public void triggerBlast(int currentTick) {
        if ((!automatic || currentFireLock < currentTick) && currentMagazineSize > 0 && !reloading) {
            SoundManager.playSound(soundFire);
            currentFireLock = currentTick + fireDelay;
            currentMagazineSize--;

            this.blastTick = currentTick + 16;
            this.blastTrigger = true;
        }
    }

    @Override
    public void triggerReload(int currentTick) {
        if (!reloading && currentMagazineSize < magazineSize) {
            if (currentMagazineSize == 0 && !sameReloadSound) {
                SoundManager.playSound(soundReloadEmpty);
            } else {
                SoundManager.playSound(soundReload);
            }
        }
        super.triggerReload(currentTick);
    }

    public void draw(Graphics2D g2, String direction, int screenX, int screenY, int targetX, int targetY, int tick) {
        if (automatic && blastTrigger) {
            drawBlast = !drawBlast;
        }
    }

    protected void getRes() {
        try {
            blastImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("prefab/muzzleBlast.png")));
        } catch (IOException e) {
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

    protected void drawCommon(Graphics2D g2, int weaponX, int weaponY, int targetX, int targetY, int screenX, int screenY, String direction, int tick) {
        targetY -= 30;
        if (direction.equals("down")) {
            targetX -= 15;
        } else {
            targetX -= 30;
        }
        BufferedImage img = getImage(direction);
        rotation = Math.atan2(targetY - screenY, targetX - screenX);
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
