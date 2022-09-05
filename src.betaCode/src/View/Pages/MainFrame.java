package View.Pages;

import Utilities.Config;
import Utilities.Utils;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame
{
    public enum Cards
    {
        MAIN,
        SETTINGS,
        GAME;

        @Override
        public String toString() { return name(); }
    }
    
    private final String pathImages = "resources/images/MainFrame/MainframeDesignElements/";

    private final Dimension dimension = new Dimension(1440,920);
    private final Image background;
    private GridBagConstraints gbc;

    private final JPanel mainBackground;
    private final JPanel settingBackground;
    private final JPanel gameBackground;

    public MainFrame()
    {
        super("J Uno");
        //System.out.println(getToolkit().getScreenSize().toString());
        //double p = ResizablePanel.SCALING_PERCENTAGE = getToolkit().getScreenSize().getWidth()/1920;
        //ResizablePanel.SCALING_PERCENTAGE = scalingPercentage;
        //System.out.println("Scaling percentage : "+scalingPercentage);
        Config.refreshScalingPercentage();
        dimension.setSize(dimension.getWidth() * Config.scalingPercentage, dimension.getHeight() * Config.scalingPercentage);

        background = Utils.getImage(pathImages + "background.png");
        setContentPane(new JPanel(){
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                //int width = (int)dimension.getWidth();
                //int height = (int)dimension.getHeight();
                Graphics2D g2 = (Graphics2D)g;
                Utils.applyQualityRenderingHints(g2);
                g2.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            }
        });

        setLayout(new CardLayout());
        setSize(dimension);
        setPreferredSize(dimension);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        //setUndecorated(true);
        setIconImage(Utils.getImage(pathImages + "icon.png"));

        gbc = new GridBagConstraints();

        mainBackground = new JPanel(new GridBagLayout());
        mainBackground.setOpaque(false);
        add(mainBackground, Cards.MAIN.name());

        settingBackground = new JPanel(new GridBagLayout());
        settingBackground.setOpaque(false);
        add(settingBackground, Cards.SETTINGS.name());

        gameBackground = new JPanel(new BorderLayout());
        gameBackground.setOpaque(false);
        add(gameBackground, Cards.GAME.name());
    }

    public void addCenteredPanels(JPanel... panels){
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1;    gbc.weighty = 1;
        gbc.gridx = 0;      gbc.gridy = 2;
        gbc.gridwidth = 2;  gbc.gridheight = 3;
        //Arrays.stream(panels).filter(panel -> !(panel instanceof SettingsPanel)).forEach(panel -> mainPanel.add(panel, gbc));
        //Arrays.stream(panels).filter(panel -> panel instanceof SettingsPanel).forEach(panel -> settingPanel.add(panel, gbc));
        for (JPanel panel : panels){
            if (panel instanceof SettingsPanel) settingBackground.add(panel, gbc);
            else mainBackground.add(panel, gbc);
        }
    }

    public void addProfilePanel(JPanel pp){
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;
        gbc.gridx = 1;
        gbc.gridy = 1;

        mainBackground.add(pp, gbc);
    }

    public JPanel getMainBackground() {
        return mainBackground;
    }

    public JPanel getGameBackground() {
        return gameBackground;
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

    /*
    public void setDimension(Dimension dimension){
        this.dimension = dimension;
    }

     */
}
