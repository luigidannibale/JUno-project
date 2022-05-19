package View;

import javax.swing.*;
import java.awt.*;

public class ImageComponent extends JLabel{

    public enum Size{
        SMALL(48),
        MEDIUM(56),
        BIG(64),
        LARGE(80);

        final int size;

        Size(int s) { size = s; }
        int getIntSize() { return size; }
        public int getBigger()
        {
            switch (size){
                case 48  -> {return 56;}
                case 56 -> {return 64; }
                default     -> {return 80;}
            }
        }
        public int getSmaller()
        {
            switch (size){
                case 64   -> {return 56;}
                case 80 -> {return 64;}
                default    -> {return 48;}
            }
        }
    }

    private static final String imagePath = "resources/images/MainFrame/panel/";
    private final String imageName;
    private Size size;

    public ImageComponent(String imageName, Size size){
        //super(new ImageIcon(imageName + imageName + s));
        this.imageName = imageName;
        this.size = size;
        setIcon(getImageIcon(size.getIntSize()));
        setMaximumSize(new Dimension(350, 100));
        setMinimumSize(new Dimension(350, 100));
        setPreferredSize(new Dimension(350, 100));
    }

    public void resetIcon()
    {
        setIcon(getImageIcon(size.getIntSize()));
    }
    public void setIcon(Size s)
    {
        size = s;
        setIcon(getImageIcon(size.getIntSize()));
    }
    public void setBiggerIcon()
    {
        setIcon(getImageIcon(size.getBigger()));
    }
    public void setSmallerIcon()
    {
        setIcon(getImageIcon(size.getSmaller()));
    }

    public ImageIcon getImageIcon(int size){
        return new ImageIcon(imagePath + imageName + size + ".png");
    }

    public void scaleUpIcon()
    {
        Image im = ((ImageIcon) getIcon()).getImage();
        im = im.getScaledInstance(im.getWidth(this)+15, im.getHeight(this)+5, Image.SCALE_SMOOTH);
        super.setIcon(new ImageIcon(im));
    }

}
