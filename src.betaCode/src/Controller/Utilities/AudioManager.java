package Controller.Utilities;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.function.Function;

public class AudioManager
{
    /**
     * KEYS for stats
     *<ul>
     *     <li>PLAY</li>
     *     <li>DRAW_CARD</li>
     *     <li>FLIP</li>
     *     <li>UNO</li>
     *     <li>NOT_VALID</li>
     *     <li>ERROR</li>
     *     <li>WIN</li>
     *     <li>LOSS</li>
     *     <li>CLICK</li>
     *     <li>AUDIO_TEST</li>
     *     <li>REVERSE</li>
     *     <li>SKIP</li>
     *     <li>DRAW</li>
     *     <li>WILD_DRAW</li>
     *     <li>GREEN</li>
     *     <li>RED</li>
     *     <li>YELLOW</li>
     *     <li>BLUE</li>
     *</ul>
     *
     * @author D'annibale Luigi, Venturini Daniele
     */
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


    /**
     * Background music
     * <ul>
     *     <li>CALM_BACKGROUND</li>
     * </ul>
     *
     * @author D'annibale Luigi, Venturini Daniele
     */
    public enum Music
    {
        CALM_BACKGROUND
    }

    private static AudioManager instance;
    private String folder;
    private final String pathAudio = "resources/audio/";
    private Clip audioTrack;
    private Clip effectsTrack;
    /**
     * If the static instance is null, it initializes the instance of {@link AudioManager}, otherwise it returns the static instance
     * @return the static unique instance of AudioManager
     */
    public static AudioManager getInstance()
    {
        if(instance == null)
            instance = new AudioManager();
        return instance;
    }

    /**
     * Private constructor because only one instance of {@link AudioManager} can be instantiated
     */
    private AudioManager() { }

    /**
     * Sets the gamemode specific folder
     * @param folder - the folder of the gamemode
     */
    public void setFolder(String folder){this.folder = folder;}

    /**
     * Starts playing the given {@link Music} with the config volume
     * @param music - the enum constant to play
     */
    public void setAudio(Music music)
    {
        try
        {

            audioTrack = AudioSystem.getClip();                                             //creates an audio clip that plays back an audio (either file or stream)
            audioTrack.open(AudioSystem.getAudioInputStream(getAudioFile(music.name())));   //assigns the audio
            ((FloatControl) audioTrack.getControl(FloatControl.Type.MASTER_GAIN)).setValue(convert.apply(Config.musicVolume));
            audioTrack.start();
            audioTrack.loop(Clip.LOOP_CONTINUOUSLY);
        }
        catch (IOException | UnsupportedAudioFileException | LineUnavailableException ignored) { System.out.println("File not found " + music.name()); }
        catch (Exception ignored){}
    }

    /**
     * Starts playing the given {@link Effect} with the config volume
     * @param effect - the enum constant to play
     */
    public void setEffect(Effect effect)  { setEffect(effect, Config.effectsVolume); }

    /**
     * Starts playing the given {@link Effect} with the given volume
     * @param effect - the enum constant to play
     * @param volume - the volume to set
     */
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

    /**
     * Returns the full path of the file to search, combining the default audio path, the folder, the name and the extension .wav
     * @param filename - the name of the file to search
     * @param folder - the folder to search in
     * @return the full path of the file
     */
    private String getPathAudio(String filename, String folder)  { return pathAudio + "/" + folder + "/" + filename.toLowerCase() + ".wav"; }

    /**
     * Returns the audio file in the common folder
     * @param filename - the name of the file to search
     * @return the file found
     */
    private File getCommonAudioFile(String filename)  { return new File(getPathAudio(filename, "COMMON")); }

    /**
     * Returns the audio file in the gamemode specific folder
     * @param filename - the name of the file to search
     * @return the file found
     */
    private File getSpecificAudioFile(String filename)  { return new File(getPathAudio(filename,folder)); }

    /**
     * Searches for the .wav file given the filename int the gamemode specific folder.
     * If the file exists then it is returned, otherwise it returns the file in the common folder
     * @param filename - the name of the file to search
     * @return the file found
     */
    private File getAudioFile(String filename)
    {
        File specificAudioFile = getSpecificAudioFile(filename);
        return specificAudioFile.exists() ? specificAudioFile : getCommonAudioFile(filename);
    }

    /**
     * Stops the current effect playing
     */
    public void stopEffect() { effectsTrack.stop(); }

    /**
     * Sets the music volume to the given value, if the music is playing it gets updated
     * @param value - the volume from 0 to 100
     */
    public void setVolume(int value)
    { if(audioTrack != null) ((FloatControl) audioTrack.getControl(FloatControl.Type.MASTER_GAIN)).setValue(convert.apply(value)); }

    /**
     * The range of values available for the {@link FloatControl} volume goes from -80 to 6.
     * Under -50 the volume is almost mute so we convert the volume from 0 to 100 to the corresponding from -50 to 6.
     * If the volume is 0, then -80 is returned.
     */
    private Function<Integer,Float> convert = v -> v == -0 ? -80 : (v * 50 / 100.0f) - 44;
}
