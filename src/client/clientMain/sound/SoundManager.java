package client.clientMain.sound;

import client.clientMain.ClientMain;
import client.entity.MainPlayer;

import javax.sound.sampled.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class SoundManager {
    private static Clip mainMenuSoundtrack;
    public static URL walk1, walk2;

    static {
        walk1 = Objects.requireNonNull(SoundManager.class.getClassLoader().getResource("sound/player/walk1.wav"));
        walk2 = Objects.requireNonNull(SoundManager.class.getClassLoader().getResource("sound/player/walk2.wav"));
    }

    public static void playMainMenuSoundtrack() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(Objects.requireNonNull(SoundManager.class.getClassLoader().getResource("sound/mainMenuSoundtrack.wav")));
            mainMenuSoundtrack = AudioSystem.getClip();
            mainMenuSoundtrack.open(audioInputStream);
            mainMenuSoundtrack.start();
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        }
    }

    public static void stopMainMenuSoundtrack() {
        mainMenuSoundtrack.stop();
        mainMenuSoundtrack.close();
    }

    public static void playSound(URL url) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            ClientMain.closeSocket();
            throw new RuntimeException(e);
        }
    }

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
                ClientMain.closeSocket();
                throw new RuntimeException(e);
            }
        }
    }
}
