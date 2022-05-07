import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioManager {
    private static AudioManager instance;
    private static Clip clip;

    private AudioManager(){}

    public static AudioManager getInstance(){
        if (instance == null) instance = new AudioManager();
        return instance;
    }

    public void play(String filename){
        try {
            /*InputStream in = new FileInputStream(filename);
            AudioStream sound = new AudioStream(in);
            AudioPlayer.player.start(sound);*/
            if (clip != null && clip.isRunning()) clip.stop();

            AudioInputStream stream = AudioSystem.getAudioInputStream(new File(filename));
            clip = AudioSystem.getClip();
            clip.open(stream);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-20.0f); // Reduce volume by 20 decibels.
            clip.start();
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e1) {
            e1.printStackTrace();
        }
    }
}
