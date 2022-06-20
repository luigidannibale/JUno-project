package View;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioManager {
    static final String pathAudio = "resources/audio/";

    Clip clip;
    FloatControl floatControl;
    int volume;

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

    public void playMusic(String filename, int volume){
        setAudio(filename);
        setVolume(volume);
        play();
        loop();
    }

    public void playEffect(String filename){
        setAudio(filename);
        play();
    }

    public void setVolume(int value){
        if (floatControl != null) floatControl.setValue(value);
        volume = value;
    }

    public int getVolume(){
        return volume;
    }
}
