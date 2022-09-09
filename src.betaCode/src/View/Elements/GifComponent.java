package View.Elements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Class used to normally draw an {@link ImageComponent} and a gif when hovered.
 * It specializes the {@link ImageComponent}
 * @author D'annibale Luigi, Venturini Daniele
 */
public class GifComponent extends ImageComponent
{
    private ImageIcon gif;

    /**
     * Creates a new {@link GifComponent} from the given path and default size of the gif
     * @param imagePath
     */
    public GifComponent(String imagePath)
    {
        this(imagePath,-1,-1);
    }

    /**
     * Creates a new {@link GifComponent} from the given path and scales the image with the given width and height
     * @param imagePath
     * @param width
     * @param height
     */
    public GifComponent(String imagePath, int width, int height){
        super(imagePath + ".png", width, height);

        gif = new ImageIcon(imagePath + ".gif");
        if (width < 0 && height < 0){
            width = gif.getIconWidth();
            height = gif.getIconHeight();
        }

        gif = ScaleImage(gif);
        setSize(width,height);
        setPreferredSize(new Dimension(width,height));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { setIcon(gif); }
            @Override
            public void mouseExited(MouseEvent e) { setIcon(icon); }
        });
    }

    //needed to scale the image with the scaling percentage
    @Override
    public void setPreferredSize(Dimension preferredSize) {
        super.setPreferredSize(preferredSize);
        if (gif != null) gif = ScaleImage(gif);
    }
}
