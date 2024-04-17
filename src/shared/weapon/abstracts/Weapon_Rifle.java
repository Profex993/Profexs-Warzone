package shared.weapon.abstracts;

import java.awt.*;

public abstract class Weapon_Rifle extends Weapon {
    public Weapon_Rifle(int damage, boolean automatic, int desiredWidth, int desiredHeight, boolean removeMagReloading, boolean blastEachShot) {
        super(damage, automatic, desiredWidth, desiredHeight, removeMagReloading, blastEachShot);
    }

    public void draw(Graphics2D g2, String direction, int screenX, int screenY, int targetX, int targetY, int tick) {
        int weaponX = 0, weaponY = 0;
        switch (direction) {
            case "down" -> {
                weaponX = screenX + 8;
                weaponY = screenY + 12;
            }
            case "right" -> {
                weaponX = screenX + 10;
                weaponY = screenY + 20;
            }
            case "left" -> {
                weaponX = screenX + 35;
                weaponY = screenY + 20;
            }
            case "up" -> {
                weaponX = screenX + 30;
                weaponY = screenY + 25;
            }
        }

        drawCommon(g2, weaponX, weaponY, targetX, targetY, screenX, screenY, direction, tick);
    }
}
