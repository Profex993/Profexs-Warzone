package shared.weapon;

import server.entity.PlayerServerSide;
import server.entity.ProjectileServerSide;

import java.util.ArrayList;

public class Weapon_Core {
    private final String name;
    private final int damage;
    protected final int fireDelay, reloadDelay, magazineSize;
    protected int currentFireLock = 0, currentReloadLock = 0, currentMagazineSize;
    protected final int desiredWidth, desiredHeight;
    protected final boolean automatic;
    protected boolean reloading = false;

    public Weapon_Core(String name, int damage, boolean automatic, int desiredWidth, int desiredHeight, int fireDelay, int magazineSize, int reloadDelay) {
        this.damage = damage;
        this.name = name;
        this.fireDelay = fireDelay;
        this.automatic = automatic;
        this.desiredWidth = desiredWidth;
        this.desiredHeight = desiredHeight;
        this.magazineSize = magazineSize;
        this.currentMagazineSize = magazineSize;
        this.reloadDelay = reloadDelay;
    }

    public void shoot(ArrayList<ProjectileServerSide> projectileList, int worldX, int worldY, String direction,
                      PlayerServerSide player, int currentTick, double rotation) {

        if ((!automatic || currentFireLock < currentTick) && currentMagazineSize > 0 && !reloading) {
            currentFireLock = currentTick + fireDelay;
            currentMagazineSize--;

            switch (direction) {
                case "down" -> {
                    worldX += 10;
                    worldY += 12;
                }
                case "right" -> {
                    worldX += 5;
                    worldY += 24;
                }
                case "left" -> {
                    worldX += 35;
                    worldY += 24;
                }
                case "up" -> {
                    worldX += 30;
                    worldY += 25;
                }
            }

//            mouseY -= 30;
//            if (direction.equals("down")) {
//                mouseX -= 15;
//            } else {
//                mouseX -= 30;
//            }
            projectileList.add(new ProjectileServerSide(rotation, worldX, worldY, player, damage));
        }
    }

    public void reload(int currentTick) {
        if (currentTick > currentReloadLock) {
            currentMagazineSize = magazineSize;
            reloading = false;
        }
    }

    public boolean triggerReload(int currentTick) {
        if (!reloading && currentMagazineSize < magazineSize) {
            reloading = true;
            currentReloadLock = currentTick + reloadDelay;
            return true;
        }
        return false;
    }

    public boolean isAutomatic() {
        return automatic;
    }

    public boolean isReloading() {
        return reloading;
    }

    public String getName() {
        return name;
    }
}
