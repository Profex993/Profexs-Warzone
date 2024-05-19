package shared.object.objectClasses;

import server.ConstantsServer;
import server.ServerCore;
import server.entity.PlayerServerSide;
import shared.ObjectGenerator;
import shared.weapon.weaponClasses.Weapon;

/**
 * map object for weapon
 */
public abstract class MapObject_Weapon extends MapObject {
    private final Class<? extends Weapon> weaponClass;
    private int despawnTime = ConstantsServer.WEAPON_ITEM_DESPAWN_DELAY;

    /**
     * @param worldX int world x location
     * @param worldY int world y location
     * @param width int width
     * @param height int height
     * @param imagePath String of file path to image
     * @param weaponClass class of weapon to be placed on map
     */
    public MapObject_Weapon(int worldX, int worldY, int width, int height, String imagePath, Class<? extends Weapon> weaponClass) {
        super(worldX, worldY, width, height, true, imagePath, true);
        this.weaponClass = weaponClass;
    }

    /**
     * @param player PlayerServerSide
     * @param core ServerCore for accessing components
     */
    @Override
    public void executeServerSide(PlayerServerSide player, ServerCore core) {
        String playerWeapon = "Weapon_" + player.getWeaponName();
        if (!playerWeapon.equals(weaponClass.getSimpleName())) {
            try {
                core.addObject(ObjectGenerator.getObjectByName(player.getWeaponAssociatedMapObject().getSimpleName(),
                        player.getWorldX(), player.getWorldY()));

            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            player.changeWeapon(weaponClass);
            core.removeObject(this);
        }
    }

    @Override
    public void updatePerSecond(ServerCore core) {
        despawnTime--;
        if (despawnTime == 0) {
            core.removeObject(this);
        }
    }
}
