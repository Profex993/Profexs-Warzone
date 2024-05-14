package shared.object.objectClasses;

import client.entity.MainPlayer;
import server.ConstantsServer;
import server.ServerCore;
import server.entity.PlayerServerSide;
import shared.packets.Packet_PlayerInputToServer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public abstract class Object {
    protected final int worldX, worldY, width, height;
    private final boolean collision, drawUnderPlayer;
    protected final boolean intractable;
    private final Rectangle solidArea, triggerArea;
    private BufferedImage image;
    private final String imagePath;
    private boolean interactText = false;

    public Object(int worldX, int worldY, int width, int height, boolean intractable, String imagePath, boolean drawUnderPlayer) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.width = width;
        this.height = height;
        this.intractable = intractable;
        this.solidArea = null;
        this.collision = false;
        this.imagePath = imagePath;
        this.drawUnderPlayer = drawUnderPlayer;
        this.triggerArea = new Rectangle(worldX - ConstantsServer.OBJECT_TRIGGER_AREA_OFFSET,
                worldY - ConstantsServer.OBJECT_TRIGGER_AREA_OFFSET,
                width + 2 * ConstantsServer.OBJECT_TRIGGER_AREA_OFFSET,
                height + ConstantsServer.OBJECT_TRIGGER_AREA_OFFSET);
    }

    public Object(int worldX, int worldY, int width, int height, boolean intractable, String imagePath, Rectangle solidArea,
                  boolean drawUnderPlayer) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.width = width;
        this.height = height;
        this.intractable = intractable;
        this.solidArea = solidArea;
        this.collision = true;
        this.imagePath = imagePath;
        this.drawUnderPlayer = drawUnderPlayer;
        this.triggerArea = new Rectangle(worldX - ConstantsServer.OBJECT_TRIGGER_AREA_OFFSET,
                worldY - ConstantsServer.OBJECT_TRIGGER_AREA_OFFSET,
                width + 2 * ConstantsServer.OBJECT_TRIGGER_AREA_OFFSET,
                (int) (height + 1.5 * ConstantsServer.OBJECT_TRIGGER_AREA_OFFSET));
    }

    public void initializeRes() throws IOException {
        image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(imagePath)));
    }

    public void tryInteracting(PlayerServerSide player, Packet_PlayerInputToServer input, ServerCore core) {
        if (!intractable) return;
        int screenX = worldX - player.getWorldX() + input.screenX();
        int screenY = worldY - player.getWorldY() + input.screenY();

        if (player.getSolidArea().intersects(triggerArea) &&
                (input.mouseX() > screenX && input.mouseX() < screenX + width &&
                        input.mouseY() > screenY && input.mouseY() < screenY + height)) {

            executeServerSide(player, core);
        }
    }

    public void updatePerSecond(ServerCore core) {

    }

    public void executeServerSide(PlayerServerSide player, ServerCore core) {
    }

    public void updateClientSide(double mouseX, double mouseY, MainPlayer player) {
        if (intractable) {
            int screenX = worldX - player.getWorldX() + player.getScreenX();
            int screenY = worldY - player.getWorldY() + player.getScreenY();
            if (mouseX > screenX && mouseX < screenX + width && (mouseY > screenY && mouseY < screenY + height)) {
                interactText = true;
            } else if (interactText) {
                interactText = false;
            }
        }
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

    public boolean isDrawUnderPlayer() {
        return drawUnderPlayer;
    }

    public int getWorldX() {
        return worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    @Override
    public int hashCode() {
        return Objects.hash(worldX, worldY, width, height, collision, intractable, solidArea, imagePath);
    }
}
