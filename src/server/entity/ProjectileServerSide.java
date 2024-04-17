package server.entity;

import shared.Constants;

import java.awt.*;
import java.util.ArrayList;

public class ProjectileServerSide {
    private final double rotation;
    private final Rectangle solidArea;
    private final String playerID;

    public ProjectileServerSide(double rotation, int worldX, int worldY, String playerID) {
        this.rotation = rotation;
        this.solidArea = new Rectangle(worldX, worldY, 4, 4);
        this.playerID = playerID;
    }

    public void update(ArrayList<PlayerServerSide> playerList, ArrayList<ProjectileServerSide> projectileList) {
        double dx = Math.cos(rotation);
        double dy = Math.sin(rotation);

        solidArea.x += (int) (dx * Constants.projectileSpeed);
        solidArea.y += (int) (dy * Constants.projectileSpeed);

        for (PlayerServerSide player : playerList) {
            if (solidArea.intersects(player.getSolidArea()) && !playerID.equals(player.getId())) {
                System.out.println("test");
                projectileList.remove(this);
            }
        }
    }
}
