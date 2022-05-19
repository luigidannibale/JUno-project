package View;

import javax.swing.*;
import java.awt.*;

public class ImageBackground extends JPanel{
    private final Image background;

    public ImageBackground(Image background)
    {
        this.background = background;
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D gd = (Graphics2D)g.create();
        int w = getRootPane().getWidth(), h = getRootPane().getHeight();
        setSize(w, h);
        gd.drawImage(background, 0, 0, Math.max(w, 800), Math.max(h, 600), null);
        gd.dispose();
    }

    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(background.getWidth(this), background.getHeight(this));
    }

}
