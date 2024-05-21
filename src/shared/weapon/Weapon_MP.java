package shared.weapon;

import shared.object.MapObject_Weapon_MP;
import shared.weapon.weaponClasses.Weapon_Core;
import shared.weapon.weaponClasses.Weapon_Pistol;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

@SuppressWarnings("unused")
public class Weapon_MP extends Weapon_Pistol {
    public static final String name = "MP";

    public Weapon_MP() {
        super(name, 12, false, 60, 30, false,
                0, 8, 250, false);
    }

    @Override
    protected void getRes() {
        super.getRes();
        try {
            topImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("weapons/mpTop.png")));
            rightImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("weapons/mpRight.png")));
            leftImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("weapons/mpLeft.png")));
            this.soundFire = getClass().getClassLoader().getResource("sound/weapons/mpFire.wav");
            this.soundReload = getClass().getClassLoader().getResource("sound/weapons/mpReload.wav");
            this.soundReloadEmpty = getClass().getClassLoader().getResource("sound/weapons/mpReloadEmpty.wav");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static Weapon_Core getServerSideWeapon() {
        return new Weapon_Core(name, 12, false, 60, 30, 0, 8, 250,
                MapObject_Weapon_MP.class);
    }
}
