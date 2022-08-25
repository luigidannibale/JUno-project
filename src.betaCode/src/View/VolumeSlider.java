package View;

import Utilities.Config;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class VolumeSlider extends JProgressBar {
    JLabel icon;
    String[] fileNames;
    String path;

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

        addChangeListener(e -> {
            if (icon != null){
                String newImage = path;
                if (getValue() == 0) newImage += fileNames[0];
                else if (getValue() < 50) newImage += fileNames[1];
                else newImage += fileNames[2];
                var icona = new ImageIcon((newImage));
                icon.setIcon(ScaleImage(icona, icona.getIconWidth(), icona.getIconHeight()));
            }
        });
    }
    protected ImageIcon ScaleImage(ImageIcon image, int width, int height){
        return new ImageIcon(image.getImage().getScaledInstance((int) (width * Config.scalingPercentage), (int) (height * Config.scalingPercentage), Image.SCALE_DEFAULT));
    }
    public void setValue(int value){
        super.setValue(value);
    }

    public void setChangebleIcon(JLabel icon,String generalPath, String[] fileNames){
        this.icon = icon;
        this.path = generalPath;
        this.fileNames = fileNames;
    }

}
