package shared.weapon;

import shared.weapon.weaponClasses.Weapon_Core;
import shared.weapon.weaponClasses.Weapon_Rifle;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

@SuppressWarnings("unused")
public class Weapon_Sks extends Weapon_Rifle {
    public static final String name = "Sks";
    public Weapon_Sks() {
        super(name, 20, false, 80, 30, false,
                0, 10, 360, true);
    }

    protected void getRes() {
        super.getRes();
        try {
            topImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("weapons/sksTop.png")));
            leftImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("weapons/sksLeft.png")));
            rightImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("weapons/sksRight.png")));
            this.soundFire = getClass().getClassLoader().getResource("sound/weapons/sksFire.wav");
            this.soundReload = getClass().getClassLoader().getResource("sound/weapons/sksReload.wav");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static Weapon_Core getServerSideWeapon() {
        return new Weapon_Core(name, 20, false, 80, 30, 0, 10, 4 * 120);
    }
}
