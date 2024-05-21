package shared.weapon;

import shared.object.MapObject_Weapon_MP;
import shared.weapon.weaponClasses.Weapon_Core;
import shared.weapon.weaponClasses.Weapon_Rifle;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

@SuppressWarnings("unused")
public class Weapon_DB extends Weapon_Rifle {
    public static final String name = "DB";

    public Weapon_DB() {
        super(name, 30, false, 100, 30, true, 0, 2, 240,
                true);
    }

    @Override
    protected void getRes() {
        super.getRes();
        try {
            topImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("weapons/doubleBarrelTop.png")));
            leftImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("weapons/doubleBarrelLeft.png")));
            rightImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("weapons/doubleBarrelRight.png")));
            leftEmptyImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("weapons/doubleBarrelLeftEmpty.png")));
            rightEmptyImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("weapons/doubleBarrelRightEmpty.png")));
            this.soundFire = getClass().getClassLoader().getResource("sound/weapons/dbFire.wav");
            this.soundReload = getClass().getClassLoader().getResource("sound/weapons/dbReload.wav");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static Weapon_Core getServerSideWeapon() {
        return new Weapon_Core(name, 30, false, 80, 30, 0, 2, 240,
                MapObject_Weapon_MP.class);
    }
}
