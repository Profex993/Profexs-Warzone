package client.clientMain.sound;

import client.clientMain.ClientMain;
import client.entity.MainPlayer;

import javax.sound.sampled.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

/**
 * class for playing sounds
 */
public abstract class SoundManager {
    private static Clip mainMenuSoundtrack;
    public final static URL walk1, walk2, switchWeapon, chat, mainMenuUrl;

    //initialize sounds
    static {
        walk1 = Objects.requireNonNull(SoundManager.class.getClassLoader().getResource("sound/player/walk1.wav"));
        walk2 = Objects.requireNonNull(SoundManager.class.getClassLoader().getResource("sound/player/walk2.wav"));
        switchWeapon = Objects.requireNonNull(SoundManager.class.getClassLoader().getResource("sound/player/switchWeapon.wav"));
        chat = Objects.requireNonNull(SoundManager.class.getClassLoader().getResource("sound/chat.wav"));
        mainMenuUrl = Objects.requireNonNull(SoundManager.class.getClassLoader().getResource("sound/mainMenuSoundtrack.wav"));
    }

    /**
     * play mein menu soundtrack
     */
    public static void playMainMenuSoundtrack() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(mainMenuUrl);
            mainMenuSoundtrack = AudioSystem.getClip();
            mainMenuSoundtrack.open(audioInputStream);
            mainMenuSoundtrack.start();
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * stop main menu soundtrack
     */
    public static void stopMainMenuSoundtrack() {
        mainMenuSoundtrack.stop();
        mainMenuSoundtrack.close();
    }

    /**
     * play sound
     * @param url url of the sound to be played
     */
    public static void playSound(URL url) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            ClientMain.showErrorWindowAndExit("can not load resources", e);
        }
    }

    /**
     * play sound with distance
     * @param player mainPlayer to determine the distance from source
     * @param url url of the sound to be played
     * @param x int world x source
     * @param y int world y source
     * @param size size of the area where the sound can be heard
     */
    public static void playSound(MainPlayer player, URL url, int x, int y, int size) {
        Rectangle rect = new Rectangle(x - size / 2, y - size / 2, size, size);
        if (player.getSolidArea().intersects(rect)) {
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                double distance = Math.sqrt(Math.pow(player.getWorldX() - x, 2) + Math.pow(player.getWorldY() - y, 2));
                float volume = (float) Math.max(0, 1 - (distance / size / 2));
                gainControl.setValue((float) (Math.log(volume) / Math.log(10.0) * 50.0));
                clip.start();
            } catch (Exception e) {
                ClientMain.showErrorWindowAndExit("can not load resources", e);
            }
        }
    }
}
