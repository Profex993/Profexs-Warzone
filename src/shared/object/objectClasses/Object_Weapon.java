package shared.object.objectClasses;

import server.ServerCore;
import server.entity.PlayerServerSide;
import shared.ObjectGenerator;
import shared.weapon.weaponClasses.Weapon;

public class Object_Weapon extends Object {
    private final Class<? extends Weapon> weaponClass;
    public Object_Weapon(int worldX, int worldY, int width, int height, String imagePath, Class<? extends Weapon> weaponClass) {
        super(worldX, worldY, width, height, true, imagePath);
        this.weaponClass = weaponClass;
    }

    @Override
    public void executeServerSide(PlayerServerSide player, ServerCore core) {
        try {
            core.addObject(ObjectGenerator.getObjectByWeapon("Object_Weapon_" + player.getWeaponName(),
                    player.getWorldX(), player.getWorldY()));

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        player.changeWeapon(weaponClass);
        core.removeObject(this);
    }
}
