package View.Elements;

import Utilities.Config;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class VolumeSlider extends JProgressBar
{

    public VolumeSlider()
    {
        setPreferredSize(new Dimension((int)(200*Config.scalingPercentage),(int)(20*Config.scalingPercentage)));
        setStringPainted(true);
        setFont(new Font("MV Boli", Font.PLAIN, 14));
        setForeground(Color.BLUE);
        setBackground(Color.WHITE);
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

    public void setValue(int value){ super.setValue(value); }

    /*
    public void setChangebleIcon(JLabel icon,String generalPath, String[] fileNames){
        this.icon = icon;
        this.path = generalPath;
        this.fileNames = fileNames;
    }

     */


}
