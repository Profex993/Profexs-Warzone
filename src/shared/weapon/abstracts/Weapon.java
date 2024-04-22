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
    private boolean blastTrigger;
    private final boolean removeMagReloading, automatic;
    private int blastTick;
    private boolean drawBlast;

    public Weapon(String name, int damage, boolean automatic, int desiredWidth, int desiredHeight, boolean removeMagReloading, int fireDelay, int magazineSize, int reloadDelay) {
        super(name, damage, automatic, desiredWidth, desiredHeight, fireDelay, magazineSize, reloadDelay);
        this.removeMagReloading = removeMagReloading;
        this.automatic = automatic;
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
            if (currentMagazineSize == 0) {
                SoundManager.playSound(soundReloadEmpty);
            } else {
                SoundManager.playSound(soundReload);
            }
        }
        super.triggerReload(currentTick);
    }

    public void draw(Graphics2D g2, String direction, int screenX, int screenY, int targetX, int targetY, int tick) {

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
        int mouseX;
        int mouseY = targetY - 30;
        if (direction.equals("down")) {
            mouseX = targetX - 15;
        } else {
            mouseX = targetX - 30;
        }
        BufferedImage img = getImage(direction);
        rotation = Math.atan2(mouseY - screenY, mouseX - screenX);
        AffineTransform transform = new AffineTransform();
        transform.translate(weaponX, weaponY);
        transform.rotate(rotation, 0, (double) img.getHeight() / 2);

        double scaleX = desiredWidth / (double) img.getWidth();
        double scaleY = desiredHeight / (double) img.getHeight();

        transform.scale(scaleX, scaleY);

        g2.drawImage(img, transform, null);

        if (blastTrigger && !(this instanceof Weapon_Pistol)) {

            AffineTransform transform2 = getBlastImgRotation(weaponX, weaponY, direction);

            if (!automatic) {
                g2.drawImage(blastImage, transform2, null);
            } else {
                if (drawBlast) {
                    g2.drawImage(blastImage, transform2, null);
                    drawBlast = false;
                } else {
                    drawBlast = true;
                }
            }

            if (tick > blastTick) {
                blastTrigger = false;
            }
        }
    }

    private AffineTransform getBlastImgRotation(int weaponX, int weaponY, String direction) {
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
