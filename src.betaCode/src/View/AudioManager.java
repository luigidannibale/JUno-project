package View;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioManager
{
    private static AudioManager instance;
    private static Clip backgroundMusic;
    private static Clip effect;
    private static FloatControl backgroundVolume;
    private static FloatControl effectVolume;
    private static final float offset = -20.0f;

    private AudioManager(){}
    public static AudioManager getInstance()
    {
        if (instance == null) instance = new AudioManager();
        return instance;
    }

    private void startAudio(Clip clip, FloatControl floatControl, String filename)
    {
        try
        {
            /*InputStream in = new FileInputStream(filename);
            AudioStream sound = new AudioStream(in);
            AudioPlayer.player.start(sound);*/
            if (clip != null && clip.isRunning()) clip.stop();

            AudioInputStream stream = AudioSystem.getAudioInputStream(new File(filename));
            clip = AudioSystem.getClip();
            clip.open(stream);
            floatControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            floatControl.setValue(offset); // Reduce volume by offset decibels.
            clip.start();
        }
        catch (IOException | UnsupportedAudioFileException | LineUnavailableException e1) { e1.printStackTrace(); }
    }
    private void setVolume(FloatControl floatControl, double volume){
        floatControl.setValue((float) volume - offset);
    }
    public void playBackroundMusic(String filename){ startAudio(backgroundMusic, backgroundVolume, filename);}
        /*try {
            if (backgroundMusic != null && backgroundMusic.isRunning()) backgroundMusic.stop();

            AudioInputStream stream = AudioSystem.getAudioInputStream(new File(filename));
            backgroundMusic = AudioSystem.getClip();
            backgroundMusic.open(stream);
            backgroundVolume = (FloatControl) backgroundMusic.getControl(FloatControl.Type.MASTER_GAIN);
            backgroundVolume.setValue(-20.0f); // Reduce volume by 20 decibels.
            backgroundMusic.start();
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e1) {
            e1.printStackTrace();
        }*/
    public void playEffect(String filename){ startAudio(effect, effectVolume, filename); }
    public void setBackgroundVolume(double volume){ setVolume(backgroundVolume, volume); }
    public void setEffectVolume(double volume){ setVolume(effectVolume, volume); }
}
