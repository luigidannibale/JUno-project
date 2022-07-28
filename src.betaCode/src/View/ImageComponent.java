package View;

import javax.swing.*;
import java.awt.*;

public class ImageComponent extends JLabel
{
    /*public enum Size{
        SMALL(48),
        MEDIUM(56),
        BIG(64);

        final int size;

        Size(int s) { size = s; }
        int getIntSize() { return size; }
        public int getBigger()  { return size == 48 ? 56:  64; }
        public int getSmaller() { return size == 64 ? 56 : 48; }
    }*/

    private final String imagePath;
    //private Size size;

    public ImageComponent(String imagePath)
    {
        this.imagePath = imagePath;
        setIcon(getImageIcon());
        setPreferredSize(new Dimension(350, 100));
    }

    public void resetIcon()
    {
        setIcon(getImageIcon());
    }
    public void setIcon()
    {
        setIcon(getImageIcon());
    }
    public void setBiggerIcon()
    {
        setIcon(getImageIcon());
    }
    public void setSmallerIcon()
    {
        setIcon(getImageIcon());
    }

    public ImageIcon getImageIcon(){
        return new ImageIcon(imagePath + ".png");
    }

    public void scaleUpIcon()
    {
        Image im = ((ImageIcon) getIcon()).getImage();
        im = im.getScaledInstance(im.getWidth(this)+15, im.getHeight(this)+5, Image.SCALE_SMOOTH);
        super.setIcon(new ImageIcon(im));
    }

}
