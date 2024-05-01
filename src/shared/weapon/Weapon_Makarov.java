package shared.weapon;

import shared.weapon.abstracts.Weapon_Pistol;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Weapon_Makarov extends Weapon_Pistol {
    public static final String name = "Makarov";
    public Weapon_Makarov() {
        super("Makarov", 5, false, 60, 30, false,
                0, 8, 360, false);
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
            throw new RuntimeException(e);
        }
    }

    public static Weapon_Core getServerSideWeapon() {
        return new Weapon_Core("Makarov", 5, false, 60, 30, 0, 8, 360);
    }
}
