package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VolumeSlider extends JProgressBar {
    public VolumeSlider()
    {
        setSize(200,20);
        //slider.setSize(new Dimension(200, 20));
        setValue(50);
        setPreferredSize(new Dimension(200, 20));
        setMinimumSize(new Dimension(200, 20));
        setMaximumSize(new Dimension(200, 20));
        setStringPainted(true);
        setFont(new Font("MV Boli", Font.PLAIN, 14));
        setForeground(Color.blue);
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

    public void setValue(int value){
        super.setValue(value);
    }

}
