package View;

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
        setSize(200,20);
        setPreferredSize(new Dimension(200, 20));
        setMinimumSize(new Dimension(200, 20));
        setMaximumSize(new Dimension(200, 20));
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
                icon.setIcon(new ImageIcon((newImage)));
            }
        });
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
