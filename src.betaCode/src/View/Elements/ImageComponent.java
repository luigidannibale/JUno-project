package View.Elements;

import Controller.Utilities.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Class used to customize a {@link JLabel} with an {@link ImageIcon} that can be scaled
 * @author D'annibale Luigi, Venturini Daniele
 */
public class ImageComponent extends JLabel
{
    protected ImageIcon icon;
    private int scaleX = 15;
    private int scaleY = 5;

    /**
     * Creates a new {@link ImageComponent} from the given path and the size of the image
     * @param imagePath
     */
    public ImageComponent(String imagePath)
    {
        this(imagePath,-1,-1);
    }

    /**
     * Creates a new {@link ImageComponent} from the given path and scales the image with the given width and height.
     * If these parameters are -1, the size of the image is used.
     * @param imagePath
     * @param width
     * @param height
     */
    public ImageComponent(String imagePath, int width, int height)
    {
        icon = new ImageIcon(imagePath);
        setSize(icon.getIconWidth(), icon.getIconHeight());
        if (width <= 0 && height <= 0)
        {
            width = icon.getIconWidth();
            height = icon.getIconHeight();
        }

        setPreferredSize(new Dimension(width,height));
    }

    /**
     * Scales the image with a small offset, to provide the effect of the image getting bigger
     */
    private void scaleUpIcon()
    {
        Image im = ((ImageIcon) getIcon()).getImage();
        int width = (int) (im.getWidth(this) + scaleX * Config.scalingPercentage);
        int height = (int) (im.getHeight(this) + scaleY * Config.scalingPercentage);
        im = im.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        super.setIcon(new ImageIcon(im));
    }

    /**
     * Adds the scaling of the image on hovering. The image gets bigger on hovering and returns normal on leaving
     */
    public void addScalingOnHovering(){
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
               //setBiggerIcon();
                scaleUpIcon();
            }
            @Override
            public void mouseExited(MouseEvent e) {
                //setSmallerIcon();
                setIcon(icon);
            }});
    };

    /**
     * Scales the image with the scaling percentage
     * @param image the image to scaled
     * @return the image scaled
     */
    protected ImageIcon ScaleImage(ImageIcon image){ return new ImageIcon(image.getImage().getScaledInstance(getPreferredSize().width, getPreferredSize().height, Image.SCALE_DEFAULT)); }

    //needed to scale the image with the scaling percentage
    @Override
    public void setPreferredSize(Dimension preferredSize)
    {
        super.setPreferredSize(preferredSize);
        icon = ScaleImage(icon);
        setIcon(icon);
        repaint();
    }
}