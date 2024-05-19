package shared.weapon.weaponClasses;

import server.CollisionManager;
import server.entity.PlayerServerSide;
import server.entity.ProjectileServerSide;
import shared.object.objectClasses.MapObject_Weapon;

import java.util.ArrayList;

/**
 * core of the weapon containing main components that are needed in server
 * this solution saves up memory on server side
 */
public class Weapon_Core {
    private final String name;
    private final int damage;
    protected final int fireDelay, reloadDelay, magazineSize;
    protected int currentFireLock = 0, currentReloadLock = 0, currentMagazineSize;
    protected final int desiredWidth, desiredHeight;
    protected final boolean automatic;
    protected boolean reloading = false;
    private final Class<? extends MapObject_Weapon> associatedWeaponMapObject;

    /**
     * constructor for creating individual weapon
     * @param name String name of the weapon, this name must be same as the name of the class after the underscore sign
     * @param damage ind damage
     * @param automatic boolean is automatic
     * @param desiredWidth int representing width of the weapon
     * @param desiredHeight int representing height of the weapon
     * @param fireDelay delay between shooting, if weapon is not automatic, can be set to 0, in tick
     * @param magazineSize size of magazine
     * @param reloadDelay delay for reloading, in tick
     */
    public Weapon_Core(String name, int damage, boolean automatic, int desiredWidth, int desiredHeight, int fireDelay, int magazineSize,
                       int reloadDelay) {
        this.damage = damage;
        this.name = name;
        this.fireDelay = fireDelay;
        this.automatic = automatic;
        this.desiredWidth = desiredWidth;
        this.desiredHeight = desiredHeight;
        this.magazineSize = magazineSize;
        this.currentMagazineSize = magazineSize;
        this.reloadDelay = reloadDelay;
        this.associatedWeaponMapObject = null;
    }

    /**
     * constructor for getServerSideWeapon
     * @param name String name, must be same as static String name
     * @param damage ind damage
     * @param automatic boolean is automatic
     * @param desiredWidth int representing width of the weapon
     * @param desiredHeight int representing height of the weapon
     * @param fireDelay delay between shooting, if weapon is not automatic, can be set to 0, in tick
     * @param magazineSize size of magazine
     * @param reloadDelay delay for reloading, in tick
     * @param associatedWeaponMapObject class of map object representing the weapon
     */
    public Weapon_Core(String name, int damage, boolean automatic, int desiredWidth, int desiredHeight, int fireDelay, int magazineSize,
                       int reloadDelay, Class<? extends MapObject_Weapon> associatedWeaponMapObject) {
        this.damage = damage;
        this.name = name;
        this.fireDelay = fireDelay;
        this.automatic = automatic;
        this.desiredWidth = desiredWidth;
        this.desiredHeight = desiredHeight;
        this.magazineSize = magazineSize;
        this.currentMagazineSize = magazineSize;
        this.reloadDelay = reloadDelay;
        this.associatedWeaponMapObject = associatedWeaponMapObject;
    }

    /**
     * method for shooting
     * @param projectileList Arraylist of ProjectileServerSide to add the projectile to
     * @param worldX int representing current world location x of the player
     * @param worldY int representing current world location y of the player
     * @param direction String of direction player is looking at
     * @param player PlayerServerSide who is shooting
     * @param currentTick int of current ServerUpdateManager tick for fire delay
     * @param rotation double mouse rotation
     * @param collisionManager CollisionManager to pass for projectile
     */
    public void shoot(ArrayList<ProjectileServerSide> projectileList, int worldX, int worldY, String direction,
                      PlayerServerSide player, int currentTick, double rotation, CollisionManager collisionManager) {
        // check if weapon can shoot
        if ((!automatic || currentFireLock < currentTick) && currentMagazineSize > 0 && !reloading) {
            currentFireLock = currentTick + fireDelay;
            currentMagazineSize--;

            // calculate position of the weapon
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

            projectileList.add(new ProjectileServerSide(rotation, worldX, worldY, player, damage, collisionManager));
        }
    }

    /**
     * method use to trigger reload and block weapon from shooting for set delay time
     * @param currentTick int ServerUpdateManager tick to set lock
     * @return true if reload was triggered
     */
    public boolean triggerReload(int currentTick) {
        if (!reloading && currentMagazineSize < magazineSize) {
            reloading = true;
            currentReloadLock = currentTick + reloadDelay;
            return true;
        }
        return false;
    }

    /**
     * when reload is triggered, this method is used to determine when reload is over
     * @param currentTick int ServerUpdateManager to determine time since reload was triggered
     */
    public void reload(int currentTick) {
        if (currentTick > currentReloadLock) {
            currentMagazineSize = magazineSize;
            reloading = false;
        }
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

    public Class<? extends MapObject_Weapon> getAssociatedWeaponMapObject() {
        return associatedWeaponMapObject;
    }
}
