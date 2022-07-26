package View;

import Controller.GameChoiceController;
import Controller.GamePanelController;
import Controller.SettingsController;
import Controller.StartingMenuController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame {
    public enum Dimensions{
        FULLHD(new Dimension(1920,1080)),
        WIDESCREEN(new Dimension(1440,920)),
        HD(new Dimension(1080,720));

        private final Dimension dimension;

        Dimensions(Dimension dimension){
            this.dimension = dimension;
        }

        public Dimension getDimension() {
            return dimension;
        }

        @Override
        public String toString() {
            int width = (int)getDimension().getWidth();
            int height = (int)getDimension().getHeight();
            return width + "x" + height;
        }
    }

    private Dimensions currentDimension = Dimensions.WIDESCREEN;

    private final String pathImages = "resources/images/";

    private Image background;

    public MainFrame()
    {
        super("J Uno");

        background = getImageIcon("MainFrame/background.png").getImage();
        setContentPane(new JPanel(){
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                int width = (int)currentDimension.getDimension().getWidth();
                int height = (int)currentDimension.getDimension().getHeight();
                g.drawImage(background, 0, 0, width, height, this);
            }
        });

        setLayout(new GridBagLayout());
        setSize(currentDimension.getDimension());
        setPreferredSize(currentDimension.getDimension());
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE); //setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setIconImage(getImageIcon("icon2.png").getImage());

        ProfilePanel pp = new ProfilePanel();
        GridBagConstraints gbc =  new GridBagConstraints();

        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        pp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                System.out.println("ENTRATO");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                System.out.println("USCITO");
            }
        });
        add(pp, gbc);
    }

    private ImageIcon getImageIcon(String filename){
        return new ImageIcon(pathImages + filename);
    }

    public Dimensions getCurrentDimension(){
        return currentDimension;
    }

    public void setCurrentDimension(Dimensions dimensions){
        currentDimension = dimensions;
    }
}
