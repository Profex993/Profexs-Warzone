package client.entity;

import client.clientMain.GamePanel;
import client.clientMain.KeyHandler;
import client.clientMain.MouseHandler;
import client.clientMain.UpdateManager;
import shared.ServerOutputToClient;
import shared.weapon.abstracts.WeaponGenerator;

import java.awt.*;

public class MainPlayer extends Entity {
    private final MouseHandler mouseHandler;
    private final KeyHandler keyHandler;
    private boolean singleFireLock = true;
    public MainPlayer(String name, String playerModel, int worldX, int worldY, MouseHandler mouseHandler, KeyHandler keyHandler) {
        super(name, playerModel);
        screenX = GamePanel.getScreenWidth() / 2 - (48 / 2);
        screenY = GamePanel.getScreenHeight() / 2 - (48 / 2);
        this.worldX = worldX;
        this.worldY = worldY;
        this.mouseHandler = mouseHandler;
        this.keyHandler = keyHandler;
    }

    public void updateFromServerInput(ServerOutputToClient input) {
        this.worldX = input.x();
        this.worldY = input.y();
        this.direction = input.direction();
        this.directionFace = input.directionFace();
        this.walkAnimNum = input.walkAnimNum();

        if (weapon == null || !weapon.getName().equals(input.weapon())) {
            weapon = WeaponGenerator.getWeaponByName(input.weapon());
        }
    }

    public void update() {
        if (mouseHandler.leftClick) {
            if (!weapon.isAutomatic() && singleFireLock) {
                weapon.triggerBlast(UpdateManager.tick);
                singleFireLock = false;
            } else if (weapon.isAutomatic()) {
                weapon.triggerBlast(UpdateManager.tick);
            }
        } else if (!singleFireLock) {
            singleFireLock = true;
        }

        if (keyHandler.reload) {
            weapon.triggerReload(UpdateManager.tick);
        } else if (weapon.isReloading()) {
            weapon.reload(UpdateManager.tick);
        }
    }

    public void draw(Graphics2D g2) {
        super.draw(g2);
        if (weapon != null) {
            weapon.draw(g2, directionFace, screenX, screenY, (int) mouseHandler.getX(), (int) mouseHandler.getY(), UpdateManager.tick);
        }
    }

    public String getName() {
        return name;
    }

    public int getWorldX() {
        return worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    public int getScreenX() {
        return screenX;
    }

    public int getScreenY() {
        return screenY;
    }
}
