package server.entity;

import server.CollisionManager;
import shared.ConstantsShared;

import java.awt.*;
import java.util.ArrayList;

public class ProjectileServerSide {
    private final double rotation;
    private final Rectangle solidArea;
    private final PlayerServerSide originalPlayer;
    private final int damage;
    private final CollisionManager collisionManager;

    public ProjectileServerSide(double rotation, int worldX, int worldY, PlayerServerSide originalPlayer, int damage,
                                CollisionManager collisionManager) {
        this.rotation = rotation;
        this.solidArea = new Rectangle(worldX, worldY, 4, 4);
        this.originalPlayer = originalPlayer;
        this.damage = damage;
        this.collisionManager = collisionManager;
    }

    public void update(ArrayList<PlayerServerSide> playerList, ArrayList<ProjectileServerSide> projectileList) {
        solidArea.x += (int) (Math.cos(rotation) * ConstantsShared.projectileSpeed);
        solidArea.y += (int) (Math.sin(rotation) * ConstantsShared.projectileSpeed);

        for (PlayerServerSide player : playerList) {
            if (solidArea.intersects(player.getSolidArea()) && this.originalPlayer != player) {
                player.removeHealth(damage, originalPlayer);
                projectileList.remove(this);
            } else if (collisionManager.checkTileProjectile(solidArea) || collisionManager.checkObjectProjectile(this)) {
                projectileList.remove(this);
            }
        }
    }

    public Rectangle getSolidArea() {
        return solidArea;
    }
}
