package Utilities;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Function;

public class AudioManager
{
    public enum Effects
    {
        PLAY,
        DRAW,
        NOT_VALID,
        ERROR,
        WIN,
        LOSS,
        CLICK,
        AUDIO_TEST
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
        instance.setAudio(filename);
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

    public void setCommonFolder(){ setFolder("COMMON"); }

    public void setFolder(String folder){this.folder = folder;}

    public void setAudio(String filename)
    {
        try
        {
            audioTrack = AudioSystem.getClip();                                       //creates an audio clip that plays back an audio (either file or stream)
            audioTrack.open(AudioSystem.getAudioInputStream(getAudioFile(filename))); //assigns the audio
        }
        catch (IOException | UnsupportedAudioFileException | LineUnavailableException ignored) { System.out.println("File not found " + filename); }
    }

    public void setEffects(Effects effects)
    {
        setEffects(effects, Config.effectsVolume);
    }

    public void setEffects(Effects effects, int volume)
    {
        try
        {
            effectsTrack = AudioSystem.getClip();                                       //creates an audio clip that plays back an audio (either file or stream)
            effectsTrack.open(AudioSystem.getAudioInputStream(getAudioFile(effects.name()))); //assigns the audio
            //System.out.println(Config.effectsVolume + " ----> " + convert.apply(Config.effectsVolume));
            ((FloatControl) effectsTrack.getControl(FloatControl.Type.MASTER_GAIN)).setValue(convert.apply(volume));
            effectsTrack.start();
        }
        catch (IOException | UnsupportedAudioFileException | LineUnavailableException ignored) { System.out.println("File not found " + effects.name()); }
    }

    private File getAudioFile(String filename){
        //System.out.println(Path.of(pathAudio, folder, filename.toLowerCase() + ".wav"));
        //return Path.of(pathAudio, folder, filename.toLowerCase()).toFile();
        return new File(pathAudio + "/" + folder + "/" + filename.toLowerCase() + ".wav");
    }

    public void play(){ audioTrack.start(); }
    public void loop(){ audioTrack.loop(Clip.LOOP_CONTINUOUSLY); }
    public void stop(){ audioTrack.stop(); }
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
        setAudio(filename);
        play();
        loop();
    }
    public void playEffect(String filename)
    {
        assert (volume != null):"Volume is null";
        setAudio(filename);
        play();
    }
    //in teoria il volume va da -80 a 6
    //noi usiamo da -44 a 6 dato che il cambiamento a volumi più bassi è impercettibile
    Function<Integer,Float> convert = v -> (float) (v * 50 / 100.0) - 44;
    Function<Integer,Integer> deconvert = v -> (v+44) * 100/50;
    //private int convert(int volume) { return (volume * 86 / 100) - 80; }
    //nousecode
    private int deconvert(int volume) { return (volume+80) * 100/86; }

}
