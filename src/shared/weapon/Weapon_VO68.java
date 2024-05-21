package shared.weapon;

import shared.object.MapObject_Weapon_VO68;
import shared.weapon.weaponClasses.Weapon_Core;
import shared.weapon.weaponClasses.Weapon_Rifle;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

@SuppressWarnings("unused")
public class Weapon_VO68 extends Weapon_Rifle {
    public static final String name = "VO68";

    public Weapon_VO68() {
        super(name, 9, true, 80, 30, true, 10, 30,
                220, false);
    }

    @Override
    protected void getRes() {
        super.getRes();
        try {
            topImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("weapons/vo68Top.png")));
            leftImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("weapons/vo68Left.png")));
            rightImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("weapons/vo68Right.png")));
            leftEmptyImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("weapons/vo68LeftEmpty.png")));
            rightEmptyImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("weapons/vo68RightEmpty.png")));
            this.soundFire = getClass().getClassLoader().getResource("sound/weapons/kaFire.wav");
            this.soundReload = getClass().getClassLoader().getResource("sound/weapons/voReload.wav");
            this.soundReloadEmpty = getClass().getClassLoader().getResource("sound/weapons/voReloadEmpty.wav");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static Weapon_Core getServerSideWeapon() {
        return new Weapon_Core(name, 9, true, 80, 30,
                12, 30, 220, MapObject_Weapon_VO68.class);
    }
}
