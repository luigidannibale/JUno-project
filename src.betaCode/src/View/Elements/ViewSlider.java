package View.Elements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Class used to customize a {@link JProgressBar}.
 * @author Luigi D'Annibale, Daniele Venturini
 */
public class ViewSlider extends JProgressBar
{

    /**
     * Creates a new {@link ViewSlider} with the common attributes
     */
    public ViewSlider()
    {
        setPreferredSize(new Dimension(200, 20));
        setStringPainted(true);
        setFont(new Font("MV Boli", Font.PLAIN, 14));
        setForeground(Color.BLUE);
        setBackground(Color.WHITE);
    }

    /**
     * Method used to make the {@link ViewSlider} clickable, changing its value based on where it has been clicked.
     * Used to change volume.
     */
    public void addDefaultMouseListener()
    {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                var x = e.getX();
                var max = getWidth();
                var perc = 100 * x / max;
                perc = perc < 0 ? 0 : Math.min(perc, 100);
                setValue(perc);
            }
        });
    }

    //needed to resize with the scaling percentage
    @Override
    public void setPreferredSize(Dimension preferredSize) {
        var oldH = getSize().getHeight();
        super.setPreferredSize(preferredSize);
        setSize(preferredSize);
        var font = getFont();
        setFont(new Font(font.getFontName(), font.getStyle(), (int) (font.getSize()*(preferredSize.height/oldH))));
    }
}
