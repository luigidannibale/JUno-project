package Utilities;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.function.Function;

public class AudioManager
{
    private static AudioManager instance;
    private final String pathAudio = "resources/audio/";
    private Clip audioTrack;
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

    public void setAudio(String filename)
    {
        try
        {
            audioTrack = AudioSystem.getClip();                                                        //creates an audio clip that plays back an audio (either file or stream)
            audioTrack.open(AudioSystem.getAudioInputStream(new File(pathAudio + filename))); //assigns the audio
        }
        catch (IOException | UnsupportedAudioFileException | LineUnavailableException ignored) { System.out.println("File not found"+filename); }
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
    public int getDecimalVolume(){ return convert.apply(volume); }
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
    Function<Integer,Integer> convert = v -> (v * 86 / 100) - 80;
    Function<Integer,Integer> deconvert = v -> (v+80) * 100/86;
    //private int convert(int volume) { return (volume * 86 / 100) - 80; }
    //nousecode
    private int deconvert(int volume) { return (volume+80) * 100/86; }

}
