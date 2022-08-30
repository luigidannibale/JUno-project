package View.Elements;

import Utilities.Utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CircleImage extends JComponent {

    private Image imm;

    private int width;
    private int height;

    public CircleImage(String path, int width, int height)
    {
        this.width = width;
        this.height = height;
        setPreferredSize(new Dimension(width, height));
        setCircleImage(path);
    }

    public CircleImage()
    {
        this("resources/images/MainFrame/StartingMenuPanel/ProfilePanel/anonymous.png", 60, 60);
    }

    public void setCircleImage(String path){
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
        catch (IOException e) {
            System.out.println("Cannot find image");
        }
    }

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
