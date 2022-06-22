package View;

import javax.swing.*;
import java.awt.*;

public class ImageComponent2 extends JLabel {

    public enum Size{
        SMALL(280, 70),
        MEDIUM(320, 85),
        BIG(400, 95);

        int width;
        int heigth;

        Size(int width, int heigth){
            this.width = width;
            this.heigth = heigth;
        }

        public int getWidth() {
            return width;
        }

        public int getHeigth() {
            return heigth;
        }
    }

    Image background;
    Size size;

    int offsetx = 0;
    int offsety = 0;

    int y;

    public ImageComponent2(String imagePath, Size size, int y){
        this.size = size;
        this.y = y;
        background = new ImageIcon(imagePath + ".png").getImage();
        setOpaque(false);
        setPreferredSize(new Dimension(350, 100));
    }

    public void setIcon(Size size){
        this.size = size;
        repaint();
    }

    public void scaleUpIcon(){
        offsetx += 10;
        offsety += 5;
        repaint();
    }

    public void resetIcon(){
        offsetx -= 10;
        offsety -= 5;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, y + 5, size.getWidth() + offsetx, size.getHeigth() + offsety, null);
    }
}
