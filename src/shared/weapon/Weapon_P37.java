package shared.weapon;

import shared.object.MapObject_Weapon_P37;
import shared.weapon.weaponClasses.Weapon_Core;
import shared.weapon.weaponClasses.Weapon_Rifle;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

@SuppressWarnings("unused")
public class Weapon_P37 extends Weapon_Rifle {
    public static final String name = "P37";
    public Weapon_P37() {
        super(name, 15, true, 80, 30, true, 12, 30,
                240, false);
    }

    @Override
    protected void getRes() {
        super.getRes();
        try {
            topImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("weapons/p36Top.png")));
            leftImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("weapons/p36Left.png")));
            rightImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("weapons/p36Right.png")));
            leftEmptyImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("weapons/p36LeftEmpty.png")));
            rightEmptyImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("weapons/p36RightEmpty.png")));
            this.soundFire = getClass().getClassLoader().getResource("sound/weapons/p37Fire.wav");
            this.soundReload = getClass().getClassLoader().getResource("sound/weapons/p37Reload.wav");
            this.soundReloadEmpty = getClass().getClassLoader().getResource("sound/weapons/p37ReloadEmpty.wav");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static Weapon_Core getServerSideWeapon() {
        return new Weapon_Core(name, 15, true, 80, 30,
                12, 30, 240, MapObject_Weapon_P37.class);
    }
}
