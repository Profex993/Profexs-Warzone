package shared.objects.Object_Weapon;

import server.ServerCore;
import server.entity.PlayerServerSide;
import shared.objects.Object;
import shared.weapon.abstracts.Weapon;

public class Object_Weapon extends Object {
    private final Class<? extends Weapon> weaponClass;
    public Object_Weapon(int worldX, int worldY, int width, int height, String imagePath, Class<? extends Weapon> weaponClass) {
        super(worldX, worldY, width, height, true, imagePath);
        this.weaponClass = weaponClass;
    }

    @Override
    public void executeServerSide(PlayerServerSide player, ServerCore core) {
        player.changeWeapon(weaponClass);
        core.removeObject(this);
    }
}
