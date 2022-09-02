package View.Elements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GifComponent extends ImageComponent
{

    private ImageIcon gif;

    public GifComponent(String imagePath)
    {
        this(imagePath,-1,-1);
    }

    public GifComponent(String imagePath, int width, int height){
        super(imagePath + ".png", width, height, false);

        gif = new ImageIcon(imagePath + ".gif");
        if (width < 0 && height < 0){
            width = gif.getIconWidth();
            height = gif.getIconHeight();
        }

        gif = ScaleImage(gif, width, height);

        setPreferredSize(new Dimension(width,height));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { setIcon(gif); }
            @Override
            public void mouseExited(MouseEvent e) { setIcon(icona); }
        });
    }

    @Override
    public void setPreferredSize(Dimension preferredSize) {
        super.setPreferredSize(preferredSize);
        if (gif != null) gif = ScaleImage(gif, preferredSize.width, preferredSize.height);
    }
}
