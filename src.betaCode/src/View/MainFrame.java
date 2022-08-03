package View;

import Utilities.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class MainFrame extends JFrame {
    public enum Cards{
        MAIN,
        SETTINGS,
        GAME;

        @Override
        public String toString() {
            return name();
        }
    }

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

    private JPanel mainPanel;
    private JPanel settingPanel;

    private JPanel gamePanel;

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
                Graphics2D g2 = (Graphics2D)g;
                Utils.applyQualityRenderingHints(g2);
                g2.drawImage(background, 0, 0, width, height, this);
            }
        });

        //setLayout(new GridBagLayout());
        setLayout(new CardLayout());
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
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setOpaque(false);
        add(mainPanel, Cards.MAIN.name());

        settingPanel = new JPanel(new GridBagLayout());
        settingPanel.setOpaque(false);
        add(settingPanel, Cards.SETTINGS.name());

        gamePanel = new JPanel(new BorderLayout());
        gamePanel.setOpaque(false);
        add(gamePanel, Cards.GAME.name());
    }

    public void addCenteredPanels(JPanel... panels){
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1;    gbc.weighty = 1;
        gbc.gridx = 0;      gbc.gridy = 2;
        gbc.gridwidth = 2;  gbc.gridheight = 3;
        //Arrays.stream(panels).filter(panel -> !(panel instanceof SettingsPanel)).forEach(panel -> mainPanel.add(panel, gbc));
        //Arrays.stream(panels).filter(panel -> panel instanceof SettingsPanel).forEach(panel -> settingPanel.add(panel, gbc));
        for (JPanel panel : panels){
            if (panel instanceof SettingsPanel) settingPanel.add(panel, gbc);
            else mainPanel.add(panel, gbc);
        }
    }

    public void addProfilePanel(ProfilePanel pp){

        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;
        gbc.gridx = 1;
        gbc.gridy = 1;


        //titleBar.add(pp, BorderLayout.LINE_END);
        mainPanel.add(pp, gbc);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JPanel getGamePanel() {
        return gamePanel;
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
