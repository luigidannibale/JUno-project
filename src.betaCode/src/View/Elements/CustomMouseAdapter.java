package View.Elements;

import Controller.Utilities.AudioManager;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Class used to customize the {@link MouseAdapter} to provide a default one with the click {@link Controller.Utilities.AudioManager.Effect}
 * and hand cursor on hovering
 * @author D'annibale Luigi, Venturini Daniele
 */
public class CustomMouseAdapter extends MouseAdapter
{

    /**
     * Sets the {@link Cursor} to Hand on hovering
     * @param e
     */
    @Override
    public void mouseEntered(MouseEvent e) { e.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); }

    /**
     * Sets the default {@link Cursor} on leaving
     * @param e
     */
    @Override
    public void mouseExited(MouseEvent e) { e.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR)); }

    /**
     * Provides the click sound on click
     * @param e
     */
    @Override
    public void mouseClicked(MouseEvent e) { playClick(); }

    /**
     * Provides the click sound on click
     * @param e
     */
    @Override
    public void mouseReleased(MouseEvent e) { playClick(); }

    /**
     * Plays the click sound
     */
    private void playClick() { AudioManager.getInstance().setEffect(AudioManager.Effect.CLICK); }
}
