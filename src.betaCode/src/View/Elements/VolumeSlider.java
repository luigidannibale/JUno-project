package View.Elements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VolumeSlider extends JProgressBar
{

    public VolumeSlider()
    {
        setPreferredSize(new Dimension((int)(200),(int)(20)));
        setStringPainted(true);
        setFont(new Font("MV Boli", Font.PLAIN, 14));
        setForeground(Color.BLUE);
        setBackground(Color.WHITE);
    }

    public void addMouseListener(){
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

    @Override
    public void setPreferredSize(Dimension preferredSize) {
        var oldH = getSize().getHeight();
        super.setPreferredSize(preferredSize);
        setSize(preferredSize);
        var font = getFont();
        setFont(new Font(font.getFontName(), font.getStyle(), (int) (font.getSize()*(preferredSize.height/oldH))));
    }

    ///public void setValue(int value){ super.setValue(value); }

    /*
    public void setChangebleIcon(JLabel icon,String generalPath, String[] fileNames){
        this.icon = icon;
        this.path = generalPath;
        this.fileNames = fileNames;
    }

     */


}
