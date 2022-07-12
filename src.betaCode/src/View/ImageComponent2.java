package View;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class ImageComponent2 extends JLabel implements Observer {
    public enum Size{
        SMALL,
        MEDIUM,
        BIG
    }

    Image paintedImage;
    Image image;
    Size size;


    public ImageComponent2(String imagePath, Size size){
        this.size = size;
        image = new ImageIcon(imagePath + ".png").getImage();
        setOpaque(false);
        setPreferredSize(new Dimension(350, 100));
    }

    public void paint(Size size){
        if (size == Size.MEDIUM)       paintedImage = image; //to use real image
        else if (size == Size.SMALL)   paintedImage = image.getScaledInstance(image.getWidth(this)-50, image.getHeight(this)-20, Image.SCALE_SMOOTH);
        else if (size == Size.BIG)     paintedImage = image.getScaledInstance(image.getWidth(this)+50, image.getHeight(this)+20, Image.SCALE_SMOOTH);
        super.setIcon(new ImageIcon(paintedImage));
    }

    //serve per ingrandire l'immagine quando ci passi sopra
    public void scaleUpIcon()
    {
        paintedImage = image.getScaledInstance(image.getWidth(this)+15, image.getHeight(this)+5, Image.SCALE_SMOOTH);
        super.setIcon(new ImageIcon(paintedImage));
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Size) paint((Size) arg);
    }


    /*
    public void setIcon(Size size){
        this.size = size;
        repaint();
    }

    public void scaleUpIcon(){
        offsetx += 10;
        offsety += 5;
        repaint();
    }

    public void resetIcon(){
        offsetx -= 10;
        offsety -= 5;
        repaint();
    }
       */
    /*
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, y + 5, size.getWidth() + offsetx, size.getHeigth() + offsety, null);
    }
    */
}
