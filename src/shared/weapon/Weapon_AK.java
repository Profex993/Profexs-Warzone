package shared.weapon;

import shared.weapon.weaponClasses.Weapon_Core;
import shared.weapon.weaponClasses.Weapon_Rifle;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Weapon_AK extends Weapon_Rifle {
    public static final String name = "AK";
    public Weapon_AK() {
        super(name, 20, true, 80, 30, true, 12,
                30, 220, false);
    }

    @Override
    protected void getRes() {
        super.getRes();
        try {
            topImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("weapons/ak74Top.png")));
            leftImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("weapons/ak74Left.png")));
            rightImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("weapons/ak74Right.png")));
            leftEmptyImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("weapons/ak74LeftEmpty.png")));
            rightEmptyImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("weapons/ak74RightEmpty.png")));
            this.soundFire = getClass().getClassLoader().getResource("sound/weapons/akFire.wav");
            this.soundReload = getClass().getClassLoader().getResource("sound/weapons/akReload.wav");
            this.soundReloadEmpty = getClass().getClassLoader().getResource("sound/weapons/akReloadEmpty.wav");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static Weapon_Core getServerSideWeapon() {
        return new Weapon_Core(name, 20, true, 120, 30,
                12, 30, 220);
    }
}
