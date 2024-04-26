package client.entity;

import shared.Constants;
import shared.weapon.abstracts.Weapon;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Entity {
    protected int worldX, worldY, screenX, screenY, walkCounter = 0, idleCounter = 0;
    protected final int width = Constants.playerWidth, height = Constants.playerHeight;
    protected final String name;
    protected String direction = "down";
    protected String directionFace = "down";
    protected int walkAnimNum = 1;
    protected Weapon weapon;
    protected BufferedImage walk1Right, walk2Right, walk3Right, walk1Left,
            walk2Left, walk3Left, walk1Up, walk2Up, walk3Up, walk1Down, walk2Down, walk3Down, deathImg;
    protected boolean weaponDrawFirst;

    public void draw(Graphics2D g2) {
        g2.drawImage(getImage(), screenX, screenY, width, height, null);
        g2.drawString(name, screenX, screenY - 5);
    }

    public Entity(String name, String playerModel) {
        this.name = name;
        setPlayerImage(playerModel);
    }

    public void setPlayerImage(String dir) {
        try {
            deathImg = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("entity/" + dir + "/death.png")));
            walk1Right = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("entity/" + dir + "/player2Walk1.png")));
            walk2Right = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("entity/" + dir + "/player2Walk2.png")));
            walk3Right = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("entity/" + dir + "/player2Walk3.png")));
            walk1Left = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("entity/" + dir + "/player1Walk1.png")));
            walk2Left = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("entity/" + dir + "/player1Walk2.png")));
            walk3Left = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("entity/" + dir + "/player1Walk3.png")));
            walk1Up = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("entity/" + dir + "/player4Walk1.png")));
            walk2Up = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("entity/" + dir + "/player4Walk3.png")));
            walk3Up = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("entity/" + dir + "/player4Walk2.png")));
            walk1Down = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("entity/" + dir + "/player3Walk1.png")));
            walk2Down = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("entity/" + dir + "/player3Walk2.png")));
            walk3Down = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("entity/" + dir + "/player3Walk3.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private BufferedImage getImage() {
        switch (directionFace) {
            case "down" -> {
                weaponDrawFirst = false;
                if (walkAnimNum == 1) {
                    return walk1Down;
                } else if (walkAnimNum == 2) {
                    return walk2Down;
                } else if (walkAnimNum == 3) {
                    return walk3Down;
                } else {
                    return walk2Down;
                }
            }
            case "right" -> {
                weaponDrawFirst = false;
                if (walkAnimNum == 1) {
                    return walk1Right;
                } else if (walkAnimNum == 2) {
                    return walk2Right;
                } else if (walkAnimNum == 3) {
                    return walk3Right;
                } else {
                    return walk2Right;
                }
            }
            case "left" -> {
                weaponDrawFirst = true;
                if (walkAnimNum == 1) {
                    return walk1Left;
                } else if (walkAnimNum == 2) {
                    return walk2Left;
                } else if (walkAnimNum == 3) {
                    return walk3Left;
                } else {
                    return walk2Left;
                }
            }
            case "up" -> {
                weaponDrawFirst = true;
                if (walkAnimNum == 1) {
                    return walk1Up;
                } else if (walkAnimNum == 2) {
                    return walk2Up;
                } else if (walkAnimNum == 3) {
                    return walk3Up;
                } else {
                    return walk2Up;
                }
            }
            default -> {
                return null;
            }
        }
    }
}
