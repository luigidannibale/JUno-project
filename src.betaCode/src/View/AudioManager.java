package View;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioManager {
    static final String pathAudio = "resources/audio/";

    Clip clip;

    public void setAudio(String filename) {
        try {
            AudioInputStream stream = AudioSystem.getAudioInputStream(new File(pathAudio + filename));
            clip = AudioSystem.getClip();
            clip.open(stream);
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
        play();
        loop();
    }

    public void playEffect(String filename){
        setAudio(filename);
        play();
    }

    public void setVolume(int value){
        FloatControl floatControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        floatControl.setValue(value);
    }
}
