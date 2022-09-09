package View.Pages.ProfilePanels;

import Controller.MainFrameController;
import Controller.Utilities.Config;
import View.Elements.CircularImage;
import View.Elements.ImageComponent;
import View.Elements.ViewSlider;
import View.Pages.ResizablePanel;
import View.Utils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * Class used to create two {@link ResizablePanel} used to display infos about the {@link View.Elements.ViewCard}. <br>
 * A small {@link ResizablePanel} with less info is always displayed in the main background. When clicked it switches to the main panel.
 * The main panel contains more information and adds the management of the profile.
 * @author D'annibale Luigi, Venturini Daniele
 */
public class ProfilePanel extends ResizablePanel
{
    public static final String IMAGE_PATH = "resources/images/MainFrame/ProfilePanel/";
    private ResizablePanel smallPanel;

    //PAINT VARIABLES
    private final Font fontName = new Font("Monsterrat", Font.PLAIN, (int) (18 * Config.scalingPercentage));
    private final Font fontLevel = new Font("Monsterrat", Font.PLAIN, (int) (13 * Config.scalingPercentage));
    private final Color backColor = new Color(55, 166, 71);
    private final Color borderColor = new Color(55, 100, 71);

    //COMPONENTS
    private CircularImage profilePicture;
    private JLabel lblName;
    private JLabel lblLevel;
    private JLabel lblVictories;
    private JLabel lblDefeats;
    private ViewSlider levelXpBar;
    private JLabel lblChangeIcon;
    private ImageComponent lblXp;

    private PlayerInputTabbedPanels playerInputTabbedPanel;


    /**
     * Instantiate the {@link ProfilePanel} and the small panel that it contains
     */
    public ProfilePanel()
    {
        super(450, 620, 0);

        setLayout(new GridBagLayout());
        setVisible(false);

        smallPanel = new ResizablePanel(260,110,0){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                paintPanel(g);
            }
        };
        smallPanel.setOpaque(false);
        smallPanel.setLayout(new GridBagLayout());

        initializedComponents();
    }

    /**
     * Initializes and adds the components of the {@link ProfilePanel}, updated with the logged player.
     * Then it initializes the main profile panel, and it resizes all components based on the scaling percentage
     */
    private void initializedComponents()
    {
        setName("ProfilePanel");
        lblName = new JLabel();
        lblName.setName("lblName");
        lblName.setFont(fontName);
        lblName.setBorder(new EmptyBorder(3,0,0,3));

        lblLevel = new JLabel();
        lblLevel.setName("lblLevel");
        lblName.setFont(fontLevel);
        lblLevel.setBorder(new EmptyBorder(0,0,2,3));

        lblXp = new ImageComponent(IMAGE_PATH + "xp.png");

        levelXpBar = new ViewSlider();
        levelXpBar.setName("levelXPbar");
        levelXpBar.setPreferredSize(new Dimension(140, 20));
        levelXpBar.setStringPainted(true);

        lblChangeIcon = new JLabel("Change icon");
        lblChangeIcon.setName("lblChangeName");
        lblChangeIcon.setFont(fontLevel);
        lblChangeIcon.setBorder(new LineBorder(Color.BLACK, 1, true));
        lblChangeIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        lblVictories = new JLabel("");
        lblVictories.setName("lblVictories");
        lblVictories.setFont(fontLevel);

        lblDefeats = new JLabel("");
        lblDefeats.setName("lblDefeats");
        lblDefeats.setFont(fontLevel);

        update();
        playerInputTabbedPanel = new PlayerInputTabbedPanels(backColor,borderColor);

        initializeMainPanel();
        resizeComponents();
    }

    /**
     * Updates the name, level, xp, wins, lost and profile picture of the logged player
     */
    public void update()
    {
        lblName.setText(Config.loggedPlayer.getName());
        double livello = Config.loggedPlayer.getStats().getLevel();
        lblLevel.setText("Level " + (int) livello);
        levelXpBar.setValue((int) Math.round(100* (livello - (int)livello)));
        if (profilePicture != null)
            profilePicture.setCircleImage(Config.savedIconPath);
        else
            profilePicture = MainFrameController.getInstance().getViewPlayer().getProfilePicture();
        lblVictories.setText("Wins  " + Config.loggedPlayer.getStats().getVictories());
        lblDefeats.setText("Lost  " + Config.loggedPlayer.getStats().getDefeats());
    }

    /**
     * Removes the components from the main profile panel and adds them to the small profile panel
     */
    public void initializeSmallPanel()
    {
        removeAll();
        GridBagConstraints gbc = new GridBagConstraints();

        lblName.setFont(fontName);
        lblName.setText(lblName.getText());

        lblLevel.setFont(fontLevel);
        lblLevel.setText(lblLevel.getText());

        lblChangeIcon.setFont(fontLevel);
        lblChangeIcon.setText(lblChangeIcon.getText());

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 4;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        gbc.weighty = 1;
        smallPanel.add(profilePicture, gbc);

        gbc.anchor = GridBagConstraints.LINE_START;

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.6;
        gbc.weighty = 0.5;
        smallPanel.add(lblName, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.6;
        gbc.weighty = 0.5;
        smallPanel.add(lblLevel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.weightx = 0.2;
        gbc.weighty = 0.5;
        smallPanel.add(lblXp, gbc);

        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.weightx = 0.2;
        gbc.weighty = 0.5;
        smallPanel.add(levelXpBar, gbc);
    }

    /**
     * Removes the components from the small profile panell and adds them to the main profile panel
     */
    public void initializeMainPanel()
    {
        smallPanel.removeAll();
        GridBagConstraints gbc = new GridBagConstraints();

        lblName.setFont(fontName.deriveFont(fontName.getSize() * 2f));
        lblName.setText(lblName.getText());

        Font font = fontLevel.deriveFont(fontLevel.getSize() * 1.5f);
        lblLevel.setFont(font);
        lblLevel.setText(lblLevel.getText());

        lblVictories.setFont(font);
        lblVictories.setText(lblVictories.getText());

        lblDefeats.setFont(font);
        lblDefeats.setText(lblDefeats.getText());

        lblChangeIcon.setFont(fontLevel.deriveFont(fontLevel.getSize() * 1.f));
        lblChangeIcon.setText(lblChangeIcon.getText());

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 4;
        gbc.weightx = 0.5;
        gbc.weighty = 0.03;
        add(profilePicture, gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridheight = 1;
        gbc.weightx = 0.5;
        gbc.weighty = 0.01;
        add(lblChangeIcon, gbc);

        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 0.6;
        gbc.weighty = 0.07;
        add(lblName, gbc);

        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.2;
        gbc.weighty = 0.07;
        add(lblVictories, gbc);

        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.4;
        gbc.weighty = 0.07;
        add(lblDefeats, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridheight = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 0.5;
        gbc.weighty = 0.01;
        add(lblLevel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.weightx = 0.01;
        gbc.weighty = 0.01;
        add(lblXp, gbc);

        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridheight = 1;
        gbc.gridwidth = 2;
        //gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.weightx = 0.5;
        gbc.weighty = 0.01;
        add(levelXpBar, gbc);



        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridheight = 1;
        gbc.gridwidth = 4;
        gbc.weightx = 1;
        gbc.weighty = 0.8;
        add(playerInputTabbedPanel, gbc);

        playerInputTabbedPanel.clearTextField();
    }

    /**
     * Resizes every component inside based on the scaling percentage.
     * If the component is a {@link JLabel} even the font is scaled.
     */
    @Override
    public void resizeComponents()
    {
        for (Component component : getComponents())
        {
            if (component instanceof JLabel lbl)
            {
                component.setFont(lbl.getFont().deriveFont((float) (lbl.getFont().getSize() * Config.scalingPercentage)));
                component = new JLabel(lbl.getText());
            }
            component.setPreferredSize(new Dimension((int) (component.getPreferredSize().width * Config.scalingPercentage), (int) (component.getPreferredSize().height * Config.scalingPercentage)));
            component.repaint();
        }
    }

    //GETTERS
    public JPanel getSmallPanel()
    {
        initializeSmallPanel();
        return smallPanel;
    }
    public PlayerInputTabbedPanels getPlayerInputTabbedPanel(){return playerInputTabbedPanel;}
    public JLabel getLabelName(){ return lblName; }
    public JLabel getLblChangeIcon(){ return lblChangeIcon; }

    /**
     * Paints the profile panel background and a border to the profile picture
     * @param g
     */
    private void paintPanel(Graphics g)
    {
        Graphics2D g2 = (Graphics2D)g;
        Utils.applyQualityRenderingHints(g2);

        int thickness = 14;
        g2.setColor(backColor);
        g2.fillRect(thickness / 2, thickness / 2, panelWidth - thickness, panelHeight - thickness );
        g2.setStroke(new BasicStroke(thickness));
        g2.setColor(borderColor);
        g2.drawRect(0, 0, panelWidth, panelHeight);

        thickness = 2;
        g2.setStroke(new BasicStroke(thickness));
        g2.setColor(Color.BLACK);
        g2.drawOval(profilePicture.getX() - thickness, profilePicture.getY() - thickness, profilePicture.getWidth() + thickness, profilePicture.getHeight() + thickness);
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        paintPanel(g);
    }


}
