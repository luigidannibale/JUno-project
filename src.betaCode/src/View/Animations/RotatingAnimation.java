package View.Animations;

import Model.Cards.CardColor;
import Utilities.Utils;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class RotatingAnimation extends Animation{

    private AlphaComposite transparent = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f);

    private final double SPEED = 0.5;

    private double degree = 0;
    private double increment = -SPEED;

    private int x;
    private int imageX;
    private int y;

<<<<<<< Updated upstream
    private int width;
    private int height;
    private String path;
=======
    int width;
    int height;
    String path;

    BufferedImage image;
>>>>>>> Stashed changes

    public RotatingAnimation(String path, int x, int y,CardColor c){
        this.x = x;
        this.y = y;
        this.path = path;
<<<<<<< Updated upstream
<<<<<<< Updated upstream

        image = Utils.getBufferedImage(path + "four/four.png");

        if (image == null) return;
=======

        changeColor(CardColor.BLUE);
>>>>>>> Stashed changes
=======

        changeColor(CardColor.BLUE);
>>>>>>> Stashed changes

        width = image.getWidth();
        height = image.getHeight();

        timer.addActionListener(e ->{
            degree += Math.toRadians(increment);
            //System.out.println(degree);
            if (degree > 360) degree = 0;
        });
        timer.start();
    }
<<<<<<< Updated upstream
<<<<<<< Updated upstream
    public void imageColor(CardColor c)
    {
        if (c == CardColor.WILD) image = Utils.getBufferedImage(path + "four/four.png");
        else    image = Utils.getBufferedImage(path + "four/"+c.name() +".png");
=======
    public void changeColor(CardColor c)
    {
        image = Utils.getBufferedImage(path + "four/"+ c.name()+".png");
>>>>>>> Stashed changes
=======
    public void changeColor(CardColor c)
    {
        image = Utils.getBufferedImage(path + "four/"+ c.name()+".png");
>>>>>>> Stashed changes
    }
    //dont like this
    public void changeTurn(boolean clockwise){
        if (!clockwise) {
            imageX = x + image.getWidth();
            width = -image.getWidth();
            increment = SPEED;
        }
        else{
            imageX = x;
            width = image.getWidth();
            increment = -SPEED;
        }
    }

    @Override
    public void paint(Graphics2D g) {
        AffineTransform oldAT = g.getTransform();
        Composite oldC = g.getComposite();

        g.setComposite(transparent);
        g.rotate(degree, x, y);                                                     //setta la rotazione dell'immagine a centro schermo
        g.translate(-image.getWidth() / 2, -image.getHeight() / 2);               //modifica l'inizio del disegno
        g.drawImage(image, imageX, y, width, height, null);
        //g.drawImage(image, x - width / 2, y - height / 2,width, height, null);

        g.setTransform(oldAT);
        g.setComposite(oldC);
    }
}
