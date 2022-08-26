package View.Animations;

import Model.Cards.CardColor;
import Utilities.Config;
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
    private int width;
    private int height;
    private int imageWidth;

    private String path;

    BufferedImage image;


    public RotatingAnimation(String path, int x, int y){
        this.x = x;
        this.y = y;
        this.path = path;

        image = Utils.getBufferedImage(path + "four/four.png");

        if (image == null) return;

        imageColor(CardColor.BLUE);

        imageWidth = (int) (image.getWidth() * Config.scalingPercentage);
        height = (int) (image.getHeight() * Config.scalingPercentage);

        start();
    }

    @Override
    public void run(){
        while(isAlive()){
            degree += Math.toRadians(increment);
            //System.out.println(degree);
            if (degree > 360) degree = 0;

            super.sleep();
        }
    }

    public void imageColor(CardColor c)
    {
        if (c == CardColor.WILD) image = Utils.getBufferedImage(path + "four/four.png");
        else image = Utils.getBufferedImage(path + "four/" + c.name() + ".png");
    }

    //dont like this
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
