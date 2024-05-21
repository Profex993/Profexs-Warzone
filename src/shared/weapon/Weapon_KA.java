package shared.weapon;

import shared.object.MapObject_Weapon_KA;
import shared.weapon.weaponClasses.Weapon_Core;
import shared.weapon.weaponClasses.Weapon_Rifle;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

@SuppressWarnings("unused")
public class Weapon_KA extends Weapon_Rifle {
    public static final String name = "KA";

    public Weapon_KA() {
        super(name, 10, true, 80, 30, true, 12,
                30, 220, false);
    }

    @Override
    protected void getRes() {
        super.getRes();
        try {
            topImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("weapons/ka74Top.png")));
            leftImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("weapons/ka74Left.png")));
            rightImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("weapons/ka74Right.png")));
            leftEmptyImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("weapons/ka74LeftEmpty.png")));
            rightEmptyImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("weapons/ka74RightEmpty.png")));
            this.soundFire = getClass().getClassLoader().getResource("sound/weapons/kaFire.wav");
            this.soundReload = getClass().getClassLoader().getResource("sound/weapons/kaReload.wav");
            this.soundReloadEmpty = getClass().getClassLoader().getResource("sound/weapons/kaReloadEmpty.wav");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static Weapon_Core getServerSideWeapon() {
        return new Weapon_Core(name, 10, true, 120, 30,
                12, 30, 220, MapObject_Weapon_KA.class);
    }
}
