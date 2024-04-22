package client.clientMain;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class SoundManager {
    private static Clip mainMenuSoundtrack;

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
}
