package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
@Deprecated
public class GifComponentDeprecated extends JComponent {
    private final Image gif;
    private final Image png;
    private final int width;
    private final int height;
    private boolean showGif = false;

    public GifComponentDeprecated(String path){
        gif = new ImageIcon(path + ".gif").getImage();
        png = new ImageIcon(path + ".png").getImage();
        width = 150;
        height = 225;
        setSize(width, height);
        setPreferredSize(new Dimension(width, height));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                showGif = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                showGif = false;
                repaint();
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
