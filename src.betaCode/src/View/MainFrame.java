package View;

import Controller.MainFrameController;

import javax.swing.*;
import javax.xml.crypto.dsig.spec.DigestMethodParameterSpec;
import java.awt.*;
import java.awt.event.*;
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
    //private JPanel titleBar;

    //int mouseX;
    //int mouseY;

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
        //setUndecorated(true);
        setIconImage(getImageIcon("icon.png").getImage());

        gbc = new GridBagConstraints();


        /*
        titleBar = new JPanel(new BorderLayout());
        titleBar.setBackground(Color.red);
        titleBar.setSize(getWidth(), 40);
        titleBar.setPreferredSize(new Dimension(getWidth(), 40));
        titleBar.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                setLocation(getX() + e.getX() - mouseX, getY() + e.getY() - mouseY);
            }
        });
        titleBar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });

        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.weightx = 1;
        gbc.weighty = 0.1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        //add(titleBar, gbc);

         */

        ///debug da cancellare
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
                    System.exit(42);
                }
            }
        });
    }

    public void addCenteredPanels(JPanel... panels){
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1;    gbc.weighty = 1;
        gbc.gridx = 0;      gbc.gridy = 2;
        gbc.gridwidth = 2;  gbc.gridheight = 3;
        Arrays.stream(panels).forEach(panel -> add(panel, gbc));
    }

    public void addProfilePanel(ProfilePanel pp){

        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;
        gbc.gridx = 1;
        gbc.gridy = 1;


        //titleBar.add(pp, BorderLayout.LINE_END);
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
