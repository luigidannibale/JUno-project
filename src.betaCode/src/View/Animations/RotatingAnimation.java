package View.Animations;

import Utilities.Utils;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class RotatingAnimation extends Animation{

    AlphaComposite transparent = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);

    double degree = 0;
    double increment = -0.5;

    int x;
    int turnX;
    int normalX;
    int y;

    int width;
    int turnWidth;
    int height;

    BufferedImage image;

    public RotatingAnimation(String path, int x, int y){
        this.x = x;
        this.y = y;

        image = Utils.getBufferedImage(path + "rotating image.png");

        width = image.getWidth();
        height = image.getHeight();

        normalX = x;

        turnX = x + width;
        turnWidth = -width;

        timer.addActionListener(e ->{
            degree += Math.toRadians(increment);
            //System.out.println(degree);
            if (degree > 360) degree = 0;
        });
        timer.start();
    }

    //dont like this
    public void changeTurn(boolean clockwise){
        if (!clockwise) {
            x = turnX;
            width = turnWidth;
            increment = 1;
        }
        else{
            x = normalX;
            width = image.getWidth();
            increment = -1;
        }
    }

    @Override
    public void paint(Graphics2D g) {
        AffineTransform oldAT = g.getTransform();
        Composite oldC = g.getComposite();

        g.setComposite(transparent);
        g.rotate(degree, normalX, y);                                                     //setta la rotazione dell'immagine a centro schermo
        g.translate(-image.getWidth() / 2, -image.getHeight() / 2);               //modifica l'inizio del disegno
        g.drawImage(image, x, y, width, height, null);
        //g.drawImage(image, x - width / 2, y - height / 2,width, height, null);

        g.setTransform(oldAT);
        g.setComposite(oldC);
    }
}
