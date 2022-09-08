package View.Animations;

import Controller.Utilities.Config;

import javax.swing.*;
import java.awt.*;

/**
 * Class used to draw an image at the center of the screen for 2 seconds.
 * Used to draw uno or exposed gif.
 * Specialize the abstract class {@link Animation}
 * @author Luigi D'Annibale, Daniele Venturini
 */
public class TextAnimation extends Animation
{

    private final Image gif;

    private final int width;
    private final int height;

    private final int x;
    private final int y;

    /**
     * Creates a new {@link TextAnimation} with the path of the image to draw at the given x and y.
     * The image is scaled based on the scaling percentage.
     * Overrides the default delay to 2 seconds.
     * @param path
     * @param x
     * @param y
     */
    public TextAnimation(String path, int x, int y)
    {
        delay = 2000;
        gif = new ImageIcon(path).getImage();

        width = (int) (gif.getWidth(null) * 2 * Config.scalingPercentage);
        height = (int) (gif.getHeight(null) * 2 * Config.scalingPercentage);

        this.x = x - width / 2;
        this.y = y - height / 2;
        start();
    }

    /**
     * It just needs to sleep for 2 seconds and then ends.
     */
    @Override
    public void run() { super.sleep(); }

    /**
     * Paints the image
     * @param g
     */
    @Override
    public void paint(Graphics2D g) { g.drawImage(gif, x, y, width, height, null); }
}
