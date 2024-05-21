package shared.weapon;

import shared.object.MapObject_Weapon_ZSS;
import shared.weapon.weaponClasses.Weapon_Core;
import shared.weapon.weaponClasses.Weapon_Rifle;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

@SuppressWarnings("unused")
public class Weapon_ZSS extends Weapon_Rifle {
    public final static String name = "ZSS";
    public Weapon_ZSS() {
        super(name, 20, false, 90, 30, true, 0, 10,
                220, false);
    }

    protected void getRes() {
        super.getRes();
        try {
            topImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("weapons/vzzTop.png")));
            leftImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("weapons/vzzLeft.png")));
            rightImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("weapons/vzzRight.png")));
            leftEmptyImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("weapons/vzzLeftEmpty.png")));
            rightEmptyImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("weapons/vzzRightEmpty.png")));
            this.soundFire = getClass().getClassLoader().getResource("sound/weapons/vzzFire.wav");
            this.soundReload = getClass().getClassLoader().getResource("sound/weapons/kaReload.wav");
            this.soundReloadEmpty = getClass().getClassLoader().getResource("sound/weapons/kaReloadEmpty.wav");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Weapon_Core getServerSideWeapon() {
        return new Weapon_Core(name, 20, false, 90, 30,
                0, 10, 220, MapObject_Weapon_ZSS.class);
    }
}
