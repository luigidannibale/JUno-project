package View.Pages;

import Controller.Utilities.Config;
import View.Utils;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author D'annibale Luigi, Venturini Daniele
 */
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

    public static final String IMAGE_PATH = "resources/images/MainFrame/";
    private final String pathImages = IMAGE_PATH + "MainframeDesignElements/";

    private final Dimension dimension = new Dimension(1440,920);
    private final Image background;

    private GridBagConstraints gbc;

    private final JPanel mainBackground;
    private final JPanel settingBackground;
    private final JPanel gameBackground;

    public MainFrame()
    {
        super("J Uno");
        Config.refreshScalingPercentage();
        dimension.setSize(dimension.getWidth() * Config.scalingPercentage, dimension.getHeight() * Config.scalingPercentage);

        background = Utils.getImage(pathImages + "background.png");
        setContentPane(new JPanel(){
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
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

    /**
     * Adds the {@link JPanel} at the center of the screen in the main background.
     * The settings panel is added to the setting background
     * @param panels the Panels to add
     */
    public void addCenteredPanels(JPanel... panels)
    {
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1;    gbc.weighty = 1;
        gbc.gridx = 0;      gbc.gridy = 2;
        gbc.gridwidth = 2;  gbc.gridheight = 3;
        for (JPanel panel : panels){
            if (panel instanceof SettingsPanel) settingBackground.add(panel, gbc);
            else mainBackground.add(panel, gbc);
        }
    }

    /**
     * Adds the {@link View.Pages.ProfileManagement.ProfilePanel} at the main panel
     * @param pp the Profile Panel
     */
    public void addProfilePanel(JPanel pp)
    {
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;
        gbc.gridx = 1;
        gbc.gridy = 1;
        mainBackground.add(pp, gbc);
    }

    //GETTERS
    public JPanel getGameBackground() { return gameBackground; }
    public Dimension getDimension(){ return dimension; }
}
