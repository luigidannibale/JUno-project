package View;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class MainFrame extends JFrame {
    private final String pathImages = "resources/images/MainFrame/MainframeDesignElements/";
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
    private Image background;
    private GridBagConstraints gbc;

    public MainFrame()
    {
        super("J Uno");

        background = getImageIcon("background.png").getImage();
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
        //setLayout(new BorderLayout());
        setSize(currentDimension.getDimension());
        setPreferredSize(currentDimension.getDimension());
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE); //setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setIconImage(getImageIcon("icon2.png").getImage());

        gbc = new GridBagConstraints();
    }

    public void addCenteredPanels(JPanel... panels){
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.gridheight = 2;

        Arrays.stream(panels).forEach(panel -> add(panel, gbc));
    }

    public void addProfilePanel(ProfilePanel pp){
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;
        gbc.gridx = 1;
        gbc.gridy = 0;

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
