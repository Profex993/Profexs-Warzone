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
                worldX += 10;
                worldY += 12;
            }
            case "right" -> {
                worldX += 5;
                worldY += 24;
            }
            case "left" -> {
                worldX += 35;
                worldY += 24;
            }
            case "up" -> {
                worldX += 30;
                worldY += 25;
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
