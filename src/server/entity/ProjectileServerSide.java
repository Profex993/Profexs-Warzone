package server.entity;

import shared.ConstantsShared;

import java.awt.*;
import java.util.ArrayList;

public class ProjectileServerSide {
    private final double rotation;
    private final Rectangle solidArea;
    private final PlayerServerSide originalPlayer;
    private final int damage;

    public ProjectileServerSide(double rotation, int worldX, int worldY, PlayerServerSide originalPlayer, int damage) {
        this.rotation = rotation;
        this.solidArea = new Rectangle(worldX, worldY, 4, 4);
        this.originalPlayer = originalPlayer;
        this.damage = damage;
    }

    public void update(ArrayList<PlayerServerSide> playerList, ArrayList<ProjectileServerSide> projectileList) {
        double dx = Math.cos(rotation);
        double dy = Math.sin(rotation);

        solidArea.x += (int) (dx * ConstantsShared.projectileSpeed);
        solidArea.y += (int) (dy * ConstantsShared.projectileSpeed);

        for (PlayerServerSide player : playerList) {
            if (solidArea.intersects(player.getSolidArea()) && this.originalPlayer != player) {
                player.removeHealth(damage, originalPlayer);
                projectileList.remove(this);
            }
        }
    }
}
