package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ImageComponent3 extends JComponent {

    Image gif;
    Image png;
    int width;
    int height;

    boolean showGif = false;

    public ImageComponent3(String path){
        gif = new ImageIcon(path + ".gif").getImage();
        png = new ImageIcon(path + ".png").getImage();
        //width = icon.getWidth(null);
        //height = icon.getHeight(null);
        width = 150;
        height = 225;
        setSize(width, height);
        setPreferredSize(new Dimension(width, height));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                showGif = true;
            }

            @Override
            public void mouseExited(MouseEvent e) {
                showGif = false;
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(showGif ? gif : png, 0, 0, width, height, this);
    }

    @Override
    public Dimension getPreferredSize() { return (new Dimension(width, height)); }
}
