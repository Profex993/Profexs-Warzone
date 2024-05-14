package shared.object.objectClasses;

import server.ConstantsServer;
import server.ServerCore;
import server.entity.PlayerServerSide;
import shared.ObjectGenerator;
import shared.weapon.weaponClasses.Weapon;

public abstract class Object_Weapon extends Object {
    private final Class<? extends Weapon> weaponClass;
    private int despawnTime = ConstantsServer.WEAPON_ITEM_DESPAWN_DELAY;

    public Object_Weapon(int worldX, int worldY, int width, int height, String imagePath, Class<? extends Weapon> weaponClass) {
        super(worldX, worldY, width, height, true, imagePath, true);
        this.weaponClass = weaponClass;
    }

    @Override
    public void executeServerSide(PlayerServerSide player, ServerCore core) {
        String playerWeapon = "Weapon_" + player.getWeaponName();
        if (!playerWeapon.equals(weaponClass.getSimpleName())) {
            try {
                core.addObject(ObjectGenerator.getObjectByName("Object_Weapon_" + player.getWeaponName(),
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
