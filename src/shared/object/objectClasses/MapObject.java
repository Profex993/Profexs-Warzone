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

/**
 * class for map objects
 */
public abstract class MapObject {
    protected final int worldX, worldY, width, height;
    private final boolean collision, drawUnderPlayer;
    protected final boolean intractable;
    private final Rectangle solidArea, triggerArea;
    private BufferedImage image;
    private final String imagePath;
    private boolean interactText = false;

    /**
     * @param worldX int world x location
     * @param worldY int world y location
     * @param width int width
     * @param height int height
     * @param intractable boolean which determines if object is intractable
     * @param imagePath String of file path to image
     * @param drawUnderPlayer if true, object will be drawn under player
     */
    public MapObject(int worldX, int worldY, int width, int height, boolean intractable, String imagePath, boolean drawUnderPlayer) {
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

    /**
     * @param worldX int world x location
     * @param worldY int world y location
     * @param width int width
     * @param height int height
     * @param intractable boolean which determines if object is intractable
     * @param imagePath String of file path to image
     * @param drawUnderPlayer if true, object will be drawn under player
     * @param solidArea Rectangle where of solid area through which player cant go
     */
    public MapObject(int worldX, int worldY, int width, int height, boolean intractable, String imagePath, Rectangle solidArea,
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

    /**
     * load image resource
     * @throws IOException if image cant be loaded
     */
    public void initializeRes() throws IOException {
        image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(imagePath)));
    }

    /**
     * try to interact with object
     * @param player PlayerServerSide which is trying to interact
     * @param input Packet_PlayerInputToServer input
     * @param core ServerCore for accessing game components
     */
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

    /**
     * update each second
     * @param core GameCore for accessing components
     */
    public void updatePerSecond(ServerCore core) {

    }

    /**
     * execute on serverSide when interacted
     * @param player PlayerServerSide
     * @param core ServerCore for accessing components
     */
    public void executeServerSide(PlayerServerSide player, ServerCore core) {
    }

    /**
     * update each tick on server side
     * @param mouseX int mouse x location
     * @param mouseY int mouse y location
     * @param player mainPlayer
     */
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

    /**
     * draw object on map
     * @param g2 Graphics2D
     * @param player MainPlayer for determining draw location
     * @param mouseX int mouse x location
     * @param mouseY int mouse y location
     */
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
