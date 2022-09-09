package View.Elements;

import Controller.Utilities.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class ImageComponent extends JLabel
{
    protected ImageIcon icon;
    private int scaleX = 15;
    private int scaleY = 5;

    public ImageComponent(String imagePath)
    {
        this(imagePath,-1,-1);
    }

    public ImageComponent(String imagePath, int width, int height)
    {
        icon = new ImageIcon(imagePath);
        setSize(icon.getIconWidth(), icon.getIconHeight());
        if (width <= 0 && height <= 0)
        {
            width = icon.getIconWidth();
            height = icon.getIconHeight();
        }
        if (height == 0 || width == 0)
            System.out.println(imagePath);

        setPreferredSize(new Dimension(width,height));
    }

    private void scaleUpIcon()
    {
        Image im = ((ImageIcon) getIcon()).getImage();
        int width = (int) (im.getWidth(this) + scaleX * Config.scalingPercentage);
        int height = (int) (im.getHeight(this) + scaleY * Config.scalingPercentage);
        im = im.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        super.setIcon(new ImageIcon(im));
    }

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

    @Override
    public void setPreferredSize(Dimension preferredSize)
    {
        super.setPreferredSize(preferredSize);
        icon = ScaleImage(icon);
        setIcon(icon);
        repaint();
    }

    protected ImageIcon ScaleImage(ImageIcon image){ return new ImageIcon(image.getImage().getScaledInstance(getPreferredSize().width, getPreferredSize().height, Image.SCALE_DEFAULT)); }
}