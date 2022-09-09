package View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Class used to provide common utilities methods for the painting of the images on screen
 * @author D'annibale Luigi, Venturini Daniele
 */
public class Utils
{
    /**
     * Increases the quality of the {@link Graphics2D} object when painting
     * @param g2d
     */
    public static void applyQualityRenderingHints(Graphics2D g2d)
    {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    }

    /**
     * Transforms a normal {@link Image} in a {@link BufferedImage}
     * @param img the image
     * @return the buffered image
     */
    public static BufferedImage toBufferedImage(Image img)
    {
        if (img instanceof BufferedImage)
            return (BufferedImage) img;

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

    /**
     * Rotates the {@link BufferedImage} by the given degrees
     * @param originalImage
     * @param degree from 0 to 360
     * @return the rotated {@link BufferedImage}
     */
    public static BufferedImage rotateImage(BufferedImage originalImage, double degree)
    {
        int w = originalImage.getWidth();
        int h = originalImage.getHeight();
        double toRad = Math.toRadians(degree);
        int hPrime = (int) (w * Math.abs(Math.sin(toRad)) + h * Math.abs(Math.cos(toRad)));
        int wPrime = (int) (h * Math.abs(Math.sin(toRad)) + w * Math.abs(Math.cos(toRad)));

        BufferedImage rotatedImage = new BufferedImage(wPrime, hPrime, originalImage.getType());
        Graphics2D g = rotatedImage.createGraphics();
        applyQualityRenderingHints(g);

        g.translate(wPrime/2, hPrime/2);
        g.rotate(toRad);
        g.translate(-w/2, -h/2);
        g.drawImage(originalImage, 0, 0, null);
        return rotatedImage;
    }

    /**
     * Fast method to get an {@link Image} object from the given path
     * @param path the path of the image
     * @return the image
     */
    public static Image getImage(String path)  { return new ImageIcon(path).getImage(); }

    /**
     * Fast method to get a {@link BufferedImage} from the given path.
     * If the image is not found, null is returned
     * @param path the path of the image
     * @return the buffered image if it exists, false otherwise
     */
    public static BufferedImage getBufferedImage(String path)
    {
        try
        {
            return ImageIO.read(new File(path));
        }
        catch (IOException ignored){}
        return null;
    }
}