package View.Elements;

import Controller.Utilities.Config;
import View.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Class used to crop an {@link Image} in a circle.
 * Primarily used to display the profile picture of the {@link ViewPlayer}
 * @author D'annibale Luigi, Venturini Daniele
 */
public class CircularImage extends JComponent
{
    private Image imm;
    private int width;
    private int height;

    /**
     * Creates a new {@link CircularImage} with the default path and size
     */
    public CircularImage() { this(Config.DEFAULT_ICON_PATH, 60, 60); }

    /**
     * Creates a new {@link CircularImage} from the given path and default size
     * @param path
     */
    public CircularImage(String path) { this(path, 60, 60); }

    /**
     * Creates a new {@link CircularImage} from the given path and size
     * @param path
     * @param width
     * @param height
     */
    public CircularImage(String path, int width, int height)
    {
        this.width = width;
        this.height = height;
        super.setPreferredSize(new Dimension(width, height));
        setCircleImage(path);
    }

    /**
     * Crops the {@link CircularImage} in a circle and paints the result
     * @param path the path of the normal image
     */
    public void setCircleImage(String path)
    {
        BufferedImage master = Utils.getBufferedImage(path);
        if (master == null) return;
        Image scaled = master.getScaledInstance(width, height, Image.SCALE_SMOOTH);

        int diameter = Math.min(width, height);
        BufferedImage mask = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = mask.createGraphics();
        Utils.applyQualityRenderingHints(g2d);

        g2d.fillOval(0, 0, diameter - 1, diameter - 1);
        g2d.dispose();

        BufferedImage masked = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        g2d = masked.createGraphics();
        Utils.applyQualityRenderingHints(g2d);
        int x = (diameter - width) / 2;
        int y = (diameter - height) / 2;
        g2d.setColor(Color.WHITE);
        g2d.fillOval(x, y, width, height);
        g2d.drawImage(scaled, x, y, null);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_IN));
        g2d.drawImage(mask, 0, 0, null);
        g2d.dispose();

        imm = masked;
        //setIcon(new ImageIcon(masked));
        repaint();
    }

    //needed to scale the image with the scaling percentage
    @Override
    public void setPreferredSize(Dimension preferredSize)
    {
        super.setPreferredSize(preferredSize);
        if (imm == null) return;
        width = preferredSize.width;
        height = preferredSize.height;
        imm = imm.getScaledInstance(width,height,Image.SCALE_DEFAULT);
        repaint();
    }

    /**
     * Paints the image at the given coordinates
     * @param g
     * @param x
     * @param y
     */
    public void paintImage(Graphics2D g, int x, int y){
        g.drawImage(imm, x, y, width, height, this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        Utils.applyQualityRenderingHints(g2);
        paintImage(g2, 0, 0);
    }
}
