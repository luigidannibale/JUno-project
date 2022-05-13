package View;

import javax.swing.*;

public class ImageComponent extends JLabel{

    public enum Size{
        SMALL(48),
        MEDIUM(56),
        BIG(64),
        LARGE(80);

        final int size;

        Size(int s) { size = s; }
        int getSize() { return size; }
    }

    private static String imagePath = "resources/images/MainFrame/panel/";
    private String imageName;
    private Size size;

    public ImageComponent(String imageName, Size size){
        //super(new ImageIcon(imageName + imageName + s));
        super(new ImageIcon(imagePath + imageName + size.getSize() + ".png"));
        this.imageName = imageName;
        this.size = size;
    }

    public void setIcon(Size s){
        size = s;
        setIcon(new ImageIcon(imagePath + imageName + size.getSize() + ".png"));
    }

}
