package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ImageComponent3 extends JComponent {

    Image icon;
    int width;
    int height;

    public ImageComponent3(String path){
        icon = new ImageIcon(path).getImage();
        //width = icon.getWidth(null);
        //height = icon.getHeight(null);
        width = 150;
        height = 225;
        setSize(width, height);
        setPreferredSize(new Dimension(width, height));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(icon, 0, 0, width, height, this);
    }

    @Override
    public Dimension getPreferredSize() { return (new Dimension(width, height)); }
}
