package Utilities;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Utils {

    private Utils(){}

    public static void applyQualityRenderingHints(Graphics2D g2d) {
        //g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        //g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        //g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        //g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        //g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
    }

    public static BufferedImage toBufferedImage(Image img){
        if (img instanceof BufferedImage)
        {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        Utils.applyQualityRenderingHints(bGr);
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }

    /*
    public static BufferedImage rotateImage(Image image, int degree){

        BufferedImage img = toBufferedImage(image);

        int width = img.getWidth();
        int height = img.getHeight();

        // Creating a new buffered image
        BufferedImage newImage = new BufferedImage(
                img.getWidth(), img.getHeight(), img.getType());

        // creating Graphics in buffered image
        Graphics2D g2 = newImage.createGraphics();
        Utils.applyQualityRenderingHints(g2);
        // Rotating image by degrees using toradians()
        // method
        // and setting new dimension t it
        g2.rotate(Math.toRadians(degree), width / 2,
                height / 2);
        g2.drawImage(img, null, 0, 0);

        // Return rotated buffer image
        return newImage;
    }

     */

    public static BufferedImage rotateImage(BufferedImage originalImage, double degree) {
        int w = originalImage.getWidth();
        int h = originalImage.getHeight();
        double toRad = Math.toRadians(degree);
        int hPrime = (int) (w * Math.abs(Math.sin(toRad)) + h * Math.abs(Math.cos(toRad)));
        int wPrime = (int) (h * Math.abs(Math.sin(toRad)) + w * Math.abs(Math.cos(toRad)));

        BufferedImage rotatedImage = new BufferedImage(wPrime, hPrime, originalImage.getType());
        Graphics2D g = rotatedImage.createGraphics();
        applyQualityRenderingHints(g);
        //g.setColor(Color.LIGHT_GRAY);
        //g.fillRect(0, 0, wPrime, hPrime);  // fill entire area
        g.translate(wPrime/2, hPrime/2);
        g.rotate(toRad);
        g.translate(-w/2, -h/2);
        g.drawImage(originalImage, 0, 0, null);
        //g.dispose();  // release used resources before g is garbage-collected
        return rotatedImage;
    }

    public static Image getImage(String path){
        return new ImageIcon(path).getImage();
    }

    public static BufferedImage getBufferedImage(String path){
        try {
            return ImageIO.read(new File(path));
        }
        catch (IOException e){
            System.out.println("Immagine non trovata : "+path);
        }
        return null;
    }

    public static void wait(int milliseconds){
        new Thread(() -> {
            try {
                Thread.sleep(milliseconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
