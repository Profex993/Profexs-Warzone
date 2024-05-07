package shared.objects;

import client.entity.MainPlayer;
import server.ServerCore;
import server.entity.PlayerServerSide;
import shared.packets.Packet_PlayerInputToServer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Object {
    protected final int worldX, worldY, width, height;
    private final boolean collision;
    protected final boolean intractable;
    private final Rectangle solidArea;
    private BufferedImage image;
    private final String imagePath;
    private boolean interactText = false;

    public Object(int worldX, int worldY, int width, int height, boolean intractable, String imagePath) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.width = width;
        this.height = height;
        this.intractable = intractable;
        solidArea = null;
        collision = false;
        this.imagePath = imagePath;
    }

    public Object(int worldX, int worldY, int width, int height, boolean intractable, String imagePath, Rectangle solidArea) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.width = width;
        this.height = height;
        this.intractable = intractable;
        this.solidArea = solidArea;
        this.collision = true;
        this.imagePath = imagePath;
    }

    public void initializeRes() throws IOException {
        image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(imagePath)));
    }

    public void updateServerSide(PlayerServerSide player, Packet_PlayerInputToServer input, ServerCore core) {
        if (!intractable) return;
        if (input.rightClick()) {
            int screenX = worldX - player.getWorldX() + input.screenX();
            int screenY = worldY - player.getWorldY() + input.screenY();
            if (input.mouseX() > screenX && input.mouseX() < screenX + width &&
                    (input.mouseY() > screenY && input.mouseY() < screenY + height)) {
                executeServerSide(player, core);
            }
        }
    }

    public void executeServerSide(PlayerServerSide player, ServerCore core) {
    }

    public void updateClientSide(double mouseX, double mouseY, boolean interact, MainPlayer player) {
        if (intractable) {
            int screenX = worldX - player.getWorldX() + player.getScreenX();
            int screenY = worldY - player.getWorldY() + player.getScreenY();
            if (mouseX > screenX && mouseX < screenX + width && (mouseY > screenY && mouseY < screenY + height)) {
                interactText = true;
                if (interact) {
                    executeClientSide();
                }
            } else if (interactText) {
                interactText = false;
            }
        }
    }

    public void executeClientSide() {
    }

    public void draw(Graphics2D g2, MainPlayer player, int mouseX, int mouseY) {
        if (worldX + width > player.getWorldX() - player.getScreenX() && worldX - width < player.getWorldX() + player.getScreenX()
                && worldY + height > player.getWorldY() - player.getScreenY() && worldY - height < worldY + player.getScreenY()) {
            int screenX = worldX - player.getWorldX() + player.getScreenX();
            int screenY = worldY - player.getWorldY() + player.getScreenY();
            g2.drawImage(image, screenX, screenY, width, height, null);
            if (interactText) {
                g2.drawString("interact", mouseX, mouseY);
            }
        }
    }

    public boolean isCollision() {
        return collision;
    }

    public Rectangle getSolidArea() {
        return solidArea;
    }

    @Override
    public int hashCode() {
        return Objects.hash(worldX, worldY, width, height, collision, intractable, solidArea, imagePath);
    }
}
