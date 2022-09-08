package View.Animations;

import Model.Cards.Color;
import Controller.Utilities.Config;
import View.Elements.ViewCard;
import View.Utils;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * Class used to draw an image rotating on its center.
 * Used to permanently draw the direction of the turn.
 * Specialize the abstract class {@link Animation}
 * @author Luigi D'Annibale, Daniele Venturini
 */
public class RotatingAnimation extends Animation
{

    private final AlphaComposite transparent = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f);

    private final double SPEED = 0.5;

    private double degree = 0;
    private double increment = -SPEED;

    private int x;
    private int imageX;
    private int y;
    private int width;
    private int height;
    private int imageWidth;

    private String path;

    BufferedImage image;

    /**
     * Creates a new {@link RotatingAnimation} with the path of the image to draw at the given x and y.
     * It scales the image with the scaling percentage. If the image is not found then the Animation doesn't start
     * @param path the path of the image to draw
     * @param x
     * @param y
     */
    public RotatingAnimation(String path, int x, int y){
        this.x = x;
        this.y = y;
        this.path = path;

        image = Utils.getBufferedImage(path + "four/four.png");

        if (image == null) return;

        imageColor(Color.BLUE);

        imageWidth = (int) (image.getWidth() * Config.scalingPercentage);
        height = (int) (image.getHeight() * Config.scalingPercentage);

        start();
    }

    /**
     * Adds to the degree of the image the related increment.
     * If the degrees are becoming big numbers, they are resetted
     */
    @Override
    public void run(){
        while(running){
            degree += Math.toRadians(increment);
            //System.out.println(degree);
            if (Math.abs(degree) > 360) degree = 0;

            super.sleep();
        }
    }

    /**
     * Changes the image color to the given {@link Model.Cards.Card} {@link Color}
     * @param c
     */
    public void imageColor(Color c)
    {
        if (c == Color.WILD) image = Utils.getBufferedImage(path + "four/four.png");
        else image = Utils.getBufferedImage(path + "four/" + c.name() + ".png");
    }

    /**
     * Rotates how the image is drawn by 180 degrees based on the direction of the turn
     * @param clockwise true if the turn is clockwise, false otherwise
     */
    public void changeTurn(boolean clockwise){
        if (!clockwise) {
            imageX = x + imageWidth;
            width = -imageWidth;
            increment = SPEED;
        }
        else{
            imageX = x;
            width = imageWidth;
            increment = -SPEED;
        }
    }

    /**
     * Paints the image
     * @param g
     */
    @Override
    public void paint(Graphics2D g) {
        AffineTransform oldAT = g.getTransform();
        Composite oldC = g.getComposite();

        g.setComposite(transparent);
        g.rotate(degree, x, y);                                                     //setta la rotazione dell'immagine a centro schermo
        g.translate(-imageWidth / 2, -height / 2);               //modifica l'inizio del disegno
        g.drawImage(image, imageX, y, width, height, null);
        //g.drawImage(image, x - width / 2, y - height / 2,width, height, null);

        g.setTransform(oldAT);
        g.setComposite(oldC);
    }
}
