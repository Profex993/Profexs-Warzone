package shared.weapon;

import shared.weapon.weaponClasses.Weapon_Core;
import shared.weapon.weaponClasses.Weapon_Pistol;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

@SuppressWarnings("unused")
public class Weapon_PM extends Weapon_Pistol {
    public static final String name = "PM";

    public Weapon_PM() {
        super(name, 5, false, 60, 30, false,
                0, 8, 250, false);
    }

    @Override
    protected void getRes() {
        super.getRes();
        try {
            topImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("weapons/makarovPMTop.png")));
            rightImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("weapons/makarovPMRight.png")));
            leftImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("weapons/makarovPMLeft.png")));
            this.soundFire = getClass().getClassLoader().getResource("sound/weapons/pmFire.wav");
            this.soundReload = getClass().getClassLoader().getResource("sound/weapons/pmReload.wav");
            this.soundReloadEmpty = getClass().getClassLoader().getResource("sound/weapons/pmReloadEmpty.wav");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static Weapon_Core getServerSideWeapon() {
        return new Weapon_Core(name, 5, false, 60, 30, 0, 8, 360);
    }
}
