package shared.weapon;

import shared.weapon.abstracts.Weapon_Rifle;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Weapon_Sks extends Weapon_Rifle {
    public Weapon_Sks() {
        super(0, false, 80, 30, false, true);
        getImg();
    }

    private void getImg() {
        try {
            topImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("weapons/sksTop.png")));
            leftImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("weapons/sksLeft.png")));
            rightImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("weapons/sksRight.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Weapon_Core getServerSideWeapon() {
        return new Weapon_Core(0, false, 80, 30);
    }
}
