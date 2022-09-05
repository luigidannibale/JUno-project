package View.Elements;

import Utilities.AudioManager;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CustomMouseAdapter extends MouseAdapter {

    @Override
    public void mouseEntered(MouseEvent e) {
        e.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        e.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        playClick();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        playClick();
    }

    private void playClick(){
        //AudioManager.getInstance().setCommonFolder();
        AudioManager.getInstance().setEffects(AudioManager.Effects.CLICK);
    }
}
