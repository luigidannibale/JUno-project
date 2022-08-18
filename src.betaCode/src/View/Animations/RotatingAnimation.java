package View.Animations;

import Utilities.Utils;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class RotatingAnimation extends Animation{

    double degree = 0;
    final int increment = -1;
    final int x;
    final int y;
    //final int width = 800;
    //final int height = 800;

    BufferedImage image;

    public RotatingAnimation(String path, int x, int y){
        this.x = x;
        this.y = y;

        image = Utils.getBufferedImage(path + "rotating image.png");

        timer.addActionListener(e ->{
            degree += Math.toRadians(increment);
            //System.out.println(degree);
            if (degree > 360) degree = 0;
        });
        timer.start();
    }

    @Override
    public void paint(Graphics2D g) {
        AffineTransform oldAT = g.getTransform();
        Composite oldC = g.getComposite();

        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));

        g.rotate(degree, x, y);                                                     //setta la rotazione dell'immagine a centro schermo
        g.translate(-image.getWidth() / 2, -image.getHeight() / 2);               //modifica l'inizio del disegno
        g.drawImage(image, x, y, null);
        //g.drawImage(image, x - width / 2, y - height / 2,width, height, null);

        g.setTransform(oldAT);
        g.setComposite(oldC);
    }
}
