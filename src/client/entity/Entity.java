package client.entity;

import client.clientMain.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Entity {
    protected int worldX, worldY, screenX, screenY, width = 40, height = 52;
    protected final int speed = 4;
    protected String name, direction, directionFace;
    protected int walkAnimNum = 1, walkCounter = 0, idleCounter;
    protected boolean walking = false;
    protected BufferedImage walk1Right, walk2Right, walk3Right, walk1Left,
            walk2Left, walk3Left, walk1Up, walk2Up, walk3Up, walk1Down, walk2Down, walk3Down, deathImg;

    public void update() {
        if (walkCounter > 20) {
            if (walkAnimNum == 1) {
//                soundManager.play(rnd.nextInt(2));
                walkAnimNum = 2;
            } else if (walkAnimNum == 2) {
                walkAnimNum = 3;
            } else if (walkAnimNum == 3) {
//                soundManager.play(rnd.nextInt(2));
                walkAnimNum = 4;
            } else {
                walkAnimNum = 1;
            }
            walkCounter = 0;
        }

        if (idleCounter == 10) {
            walkAnimNum = 2;
        }
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(getImage(), screenX, screenY,  (int)(width * GamePanel.widthScale), (int)(height * GamePanel.heightScale), null);
        g2.drawString(name, screenX, screenY - 5);
    }

    public void setPlayerImage() {
        try {
            deathImg = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("entity/default/death.png")));
            walk1Right = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("entity/default/player2Walk1.png")));
            walk2Right = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("entity/default/player2Walk2.png")));
            walk3Right = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("entity/default/player2Walk3.png")));
            walk1Left = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("entity/default/player1Walk1.png")));
            walk2Left = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("entity/default/player1Walk2.png")));
            walk3Left = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("entity/default/player1Walk3.png")));
            walk1Up = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("entity/default/player4Walk1.png")));
            walk2Up = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("entity/default/player4Walk3.png")));
            walk3Up = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("entity/default/player4Walk2.png")));
            walk1Down = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("entity/default/player3Walk1.png")));
            walk2Down = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("entity/default/player3Walk2.png")));
            walk3Down = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("entity/default/player3Walk3.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private BufferedImage getImage() {
        switch (directionFace) {
            case "down" -> {
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
