package Utilities;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.function.Function;

public class AudioManager
{
    public enum Effect
    {
        PLAY,
        DRAW_CARD,
        FLIP,
        UNO,
        NOT_VALID,
        ERROR,
        WIN,
        LOSS,
        CLICK,
        AUDIO_TEST,
        REVERSE,
        SKIP,
        DRAW,
        WILD_DRAW,
        GREEN,
        RED,
        YELLOW,
        BLUE;
    }

    public enum Musics{
        CALM_BACKGROUND
    }

    private static AudioManager instance;
    private String folder;
    private final String pathAudio = "resources/audio/";
    private Clip audioTrack;
    private Clip effectsTrack;
    private Integer volume;

    public AudioManager getInstance(String filename,int volume)
    {
        if(instance == null)
            instance = new AudioManager(volume);
        //instance.setAudio(filename);
        instance.setVolume(volume);
        return instance;
    }

    public static AudioManager getInstance()
    {
        if(instance == null)
            instance = new AudioManager(50);
        return instance;
    }

    private AudioManager(int volume) { this.volume = volume; }

    public void setFolder(String folder){this.folder = folder;}

    public void setAudio(Musics music)
    {
        try
        {

            audioTrack = AudioSystem.getClip();                                             //creates an audio clip that plays back an audio (either file or stream)
            audioTrack.open(AudioSystem.getAudioInputStream(getAudioFile(music.name())));   //assigns the audio
            ((FloatControl) audioTrack.getControl(FloatControl.Type.MASTER_GAIN)).setValue(convert.apply(Config.musicVolume));
            audioTrack.start();
            audioTrack.loop(Clip.LOOP_CONTINUOUSLY);
        }
        catch (IOException | UnsupportedAudioFileException | LineUnavailableException ignored) {}
        catch (Exception e){}
    }

    public void setEffect(Effect effect)  { setEffect(effect, Config.effectsVolume); }

    public void setEffect(Effect effect, int volume)
    {
        try
        {
            effectsTrack = AudioSystem.getClip();                                       //creates an audio clip that plays back an audio (either file or stream)
            effectsTrack.open(AudioSystem.getAudioInputStream(getAudioFile(effect.name()))); //assigns the audio
            ((FloatControl) effectsTrack.getControl(FloatControl.Type.MASTER_GAIN)).setValue(convert.apply(volume));
            effectsTrack.start();
        }
        catch (IOException | UnsupportedAudioFileException | LineUnavailableException ignored) { System.out.println("File not found " + effect.name()); }
    }

    private File getCommonAudioFile(String filename)  { return new File(getPathAudio(filename, "COMMON")); }
    private File getSpecificAudioFile(String filename)  { return new File(getPathAudio(filename,folder)); }
    private String getPathAudio(String filename, String folder)  { return pathAudio + "/" + folder + "/" + filename.toLowerCase() + ".wav"; }

    private File getAudioFile(String filename)
    {
        File commonAudioFile = getSpecificAudioFile(filename);
        return commonAudioFile.exists() ? commonAudioFile : getCommonAudioFile(filename);
    }

    public void play(){ audioTrack.start(); }
    public void loop(){ audioTrack.loop(Clip.LOOP_CONTINUOUSLY); }
    public void stop(){ audioTrack.stop(); }
    public void stopEffect() { effectsTrack.stop(); }
    public void setVolume(int value)
    {
        volume = value;
        if(audioTrack != null)
            ((FloatControl) audioTrack.getControl(FloatControl.Type.MASTER_GAIN)).setValue(convert.apply(volume));
    }
    public int getVolume(){ return volume; }
    public float getDecimalVolume(){ return convert.apply(volume); }
    public void playMusic(String filename)
    {
        assert (volume != null):"Volume is null";
        //setAudio(filename);
        play();
        loop();
    }
    public void playEffect(String filename)
    {
        assert (volume != null):"Volume is null";
        //setAudio(filename);
        play();
    }
    //in teoria il volume va da -80 a 6
    //noi usiamo da -44 a 6 dato che il cambiamento a volumi più bassi è impercettibile
    Function<Integer,Float> convert = v -> v == -0 ? -80 : (v * 50 / 100.0f) - 44;
    Function<Integer,Integer> deconvert = v -> (v+44) * 100/50;
    //private int convert(int volume) { return (volume * 86 / 100) - 80; }
    //nousecode
    private int deconvert(int volume) { return (volume+80) * 100/86; }

}
