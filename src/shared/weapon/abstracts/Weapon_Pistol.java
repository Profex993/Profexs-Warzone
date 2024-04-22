package shared.weapon.abstracts;

import java.awt.*;

public abstract class Weapon_Pistol extends Weapon {
    public Weapon_Pistol(String name, int damage, boolean automatic, int desiredWidth, int desiredHeight, boolean removeMagReloading, int fireDelay, int magazineSize, int reloadDelay) {
        super(name, damage, automatic, desiredWidth, desiredHeight, removeMagReloading, fireDelay, magazineSize, reloadDelay);
    }

    public void draw(Graphics2D g2, String direction, int screenX, int screenY, int targetX, int targetY, int tick) {
        int weaponX = 0, weaponY = 0;
        switch (direction) {
            case "down" -> {
                weaponX = screenX + 8;
                weaponY = screenY + 5;
            }
            case "right" -> {
                weaponX = screenX;
                weaponY = screenY + 20;
            }
            case "left" -> {
                weaponX = screenX + 35;
                weaponY = screenY - 10;
            }
            case "up" -> {
                weaponX = screenX + 30;
                weaponY = screenY + 5;
            }
        }

        drawCommon(g2, weaponX, weaponY, targetX, targetY, screenX, screenY, direction, tick);
    }
}
