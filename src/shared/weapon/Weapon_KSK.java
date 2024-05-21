package shared.weapon;

import shared.object.MapObject_Weapon_KSK;
import shared.weapon.weaponClasses.Weapon_Core;
import shared.weapon.weaponClasses.Weapon_Rifle;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

@SuppressWarnings("unused")
public class Weapon_KSK extends Weapon_Rifle {
    public static final String name = "KSK";
    public Weapon_KSK() {
        super(name, 15, false, 80, 30, false,
                0, 10, 680, true);
    }

    protected void getRes() {
        super.getRes();
        try {
            topImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("weapons/kskTop.png")));
            leftImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("weapons/kskLeft.png")));
            rightImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("weapons/kskRight.png")));
            this.soundFire = getClass().getClassLoader().getResource("sound/weapons/kskFire.wav");
            this.soundReload = getClass().getClassLoader().getResource("sound/weapons/kskReload.wav");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static Weapon_Core getServerSideWeapon() {
        return new Weapon_Core(name, 15, false, 80, 30, 0, 10, 4 * 120,
                MapObject_Weapon_KSK.class);
    }
}
