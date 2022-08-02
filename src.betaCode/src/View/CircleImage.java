package View;

import Utilities.Utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CircleImage extends JLabel {


    public CircleImage(String path, int width, int height){
        try {
            BufferedImage master = ImageIO.read(new File(path));
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
            g2d.drawImage(scaled, x, y, null);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_IN));
            g2d.drawImage(mask, 0, 0, null);
            g2d.dispose();

            setIcon(new ImageIcon(masked));
        }
        catch (IOException e) {
            System.out.println("Cannot find image");
        }
    }
}
