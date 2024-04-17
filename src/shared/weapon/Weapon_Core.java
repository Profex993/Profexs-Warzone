package shared.weapon;

import server.entity.ProjectileServerSide;

import java.util.ArrayList;

public class Weapon_Core {
    private final int damage;
    protected final int desiredWidth, desiredHeight;
    private final boolean automatic;

    public Weapon_Core(int damage, boolean automatic, int desiredWidth, int desiredHeight) {
        this.damage = damage;
        this.automatic = automatic;
        this.desiredWidth = desiredWidth;
        this.desiredHeight = desiredHeight;
    }

    public void shoot(ArrayList<ProjectileServerSide> projectileList, int worldX, int worldY, String direction, int mouseX, int mouseY,
                      String id, int screenX, int screenY) {

        switch (direction) {
            case "down" -> {
                screenX += 10;
                screenY += 12;
            }
            case "right" -> {
                screenX += 5;
                screenY += 24;
            }
            case "left" -> {
                screenX += 35;
                screenY += 24;
            }
            case "up" -> {
                screenX += 30;
                screenY += 25;
            }
        }

        mouseY -= 30;
        if (direction.equals("down")) {
            mouseX -= 15;
        } else {
            mouseX -= 30;
        }
        double rotation = Math.atan2(mouseY - screenY, mouseX - screenX);
        projectileList.add(new ProjectileServerSide(rotation, worldX, worldY, id));
    }

    public boolean isAutomatic() {
        return automatic;
    }
}
