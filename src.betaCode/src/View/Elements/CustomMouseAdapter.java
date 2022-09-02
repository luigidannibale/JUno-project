package View.Elements;

import Utilities.AudioManager;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CustomMouseAdapter extends MouseAdapter {

    @Override
    public void mouseClicked(MouseEvent e) {
        playClick();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        playClick();
    }

    private void playClick(){
        AudioManager.getInstance().setCommonFolder();
        AudioManager.getInstance().setEffects(AudioManager.Effects.CLICK);
    }
}
