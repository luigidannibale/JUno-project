package View;

import Controller.MainFrameController;

import javax.swing.*;
import javax.xml.crypto.dsig.spec.DigestMethodParameterSpec;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

public class MainFrame extends JFrame {
    private final String pathImages = "resources/images/MainFrame/MainframeDesignElements/";
    /*public enum Dimensions{
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
    }*/

    //private Dimensions currentDimension = Dimensions.WIDESCREEN;
    private Dimension dimension = new Dimension(1440,920);
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
                int width = (int)dimension.getWidth();
                int height = (int)dimension.getHeight();
                g.drawImage(background, 0, 0, width, height, this);
            }
        });

        setLayout(new GridBagLayout());
        setSize(dimension);
        setPreferredSize(dimension);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setUndecorated(true);
        setIconImage(getImageIcon("icon.png").getImage());

        gbc = new GridBagConstraints();

        /*
        JPanel titleBar = new JPanel();
        titleBar.setBackground(Color.red);
       /* titleBar.setSize(1440, 920);
        titleBar.setPreferredSize(new Dimension(1440, 920));

        titleBar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                System.out.println("SONO QUAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
            }
        });

        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.weightx = 1;
        gbc.weighty = 0.1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        add(titleBar, gbc);

         */

    }

    public void addCenteredPanels(JPanel... panels){
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1;    gbc.weighty = 1;
        gbc.gridx = 0;      gbc.gridy = 2;
        gbc.gridwidth = 2;  gbc.gridheight = 3;
        Arrays.stream(panels).forEach(panel -> add(panel, gbc));
        Arrays.stream(panels).forEach(Component::repaint);
        Arrays.stream(panels).forEach(panel -> System.out.println(panel.getSize() + "----" + panel.getPreferredSize()));
    }

    public void addProfilePanel(ProfilePanel pp){
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;
        gbc.gridx = 1;
        gbc.gridy = 1;

        add(pp, gbc);
    }

    private ImageIcon getImageIcon(String filename){
        return new ImageIcon(pathImages + filename);
    }

    /*
    public Dimensions getDimension(){
        return currentDimension;
    }
    public void setCurrentDimension(Dimensions dimensions){
        currentDimension = dimensions;
    }
     */
    public Dimension getDimension(){
        return dimension;
    }
    public void setDimension(Dimension dimension){
        this.dimension = dimension;
    }
}
