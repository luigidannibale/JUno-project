package View.Animations;

import Controller.Utilities.Config;

import javax.swing.*;
import java.awt.*;

public class TextAnimation extends Animation{

    private final Image gif;

    private final int width;
    private final int height;

    private final int x;
    private final int y;

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
    @Override
    public void run() { super.sleep(); }
    @Override
    public void paint(Graphics2D g) { g.drawImage(gif, x, y, width, height, null); }
}
