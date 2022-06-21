package View;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioManager {
    static final String pathAudio = "resources/audio/";

    Clip clip;
    FloatControl floatControl;
    Integer volume = 50;

    public void setAudio(String filename) {
        try {
            AudioInputStream stream = AudioSystem.getAudioInputStream(new File(pathAudio + filename));
            clip = AudioSystem.getClip();
            clip.open(stream);
            floatControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        }
        catch (IOException | UnsupportedAudioFileException | LineUnavailableException ignored){

        }
    }

    public void play(){
        clip.start();
    }

    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop(){
        clip.stop();
    }

    public void playMusic(String filename){
        setAudio(filename);
        setFloatControlVolume();
        play();
        loop();
    }

    public void playEffect(String filename){
        setAudio(filename);
        setFloatControlVolume();
        play();
    }

    public void setVolume(int value){
        volume = value;
    }

    //volume minimo -80, volume massimo 6
    public void setFloatControlVolume(){
        if (floatControl == null) return;

        int value = (volume * 86 / 100) - 80;
        floatControl.setValue(value);
    }

    public int getActualVolume(){
        return (volume + 80) * 100 / 86;
    }

    public int getVolume(){
        return volume;
    }
}
