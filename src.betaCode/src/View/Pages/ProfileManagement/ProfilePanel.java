package View.Pages.ProfileManagement;

import Controller.MainFrameController;
import Controller.Utilities.Config;
import View.Utils;
import View.Elements.CircularImage;
import View.Elements.ImageComponent;
import View.Elements.ViewSlider;
import View.Pages.ResizablePanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ProfilePanel extends ResizablePanel
{
    public static final String IMAGE_PATH = "resources/images/MainFrame/ProfilePanel/";
    private ResizablePanel smallPanel;
    private final Font fontName = new Font("Monsterrat", Font.PLAIN, (int) (18 * Config.scalingPercentage));
    private final Font fontLevel = new Font("Monsterrat", Font.PLAIN, (int) (13 * Config.scalingPercentage));
    private CircularImage profilePicture;
    private JLabel lblName;
    private JLabel lblLevel;
    private JLabel lblVictories;
    private JLabel lblDefeats;
    private ViewSlider levelXpBar;
    private JLabel lblChangeIcon;
    private ImageComponent lblXp;

    private PlayerInputTabbedPanels playerInputTabbedPanel;
    private final Color backColor = new Color(55, 166, 71);
    private final Color borderColor = new Color(55, 100, 71);

    public ProfilePanel()
    {
        super(450, 620, 0);

        //setLayout(new FlowLayout());
        setLayout(new GridBagLayout());
        setVisible(false);

        smallPanel = new ResizablePanel(260,110,0){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                paintPanel(g, this);
            }
        };
        smallPanel.setOpaque(false);
        smallPanel.setLayout(new GridBagLayout());

        initializedComponents();
    }

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

        //gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.weightx = 0.2;
        gbc.weighty = 0.5;
        smallPanel.add(levelXpBar, gbc);
    }

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

    @Override
    public void resizeComponents()
    {
        for (Component component : getComponents())
        {
            //System.out.println(component.getClass());
            if (component instanceof JLabel lbl)
            {
                component.setFont(lbl.getFont().deriveFont((float) (lbl.getFont().getSize() * Config.scalingPercentage)));
                component = new JLabel(lbl.getText());
            }
            component.setPreferredSize(new Dimension((int) (component.getPreferredSize().width * Config.scalingPercentage), (int) (component.getPreferredSize().height * Config.scalingPercentage)));
            component.repaint();
        }
    }

    public JPanel getSmallPanel()
    {
        initializeSmallPanel();
        return smallPanel;
    }

    public PlayerInputTabbedPanels getPlayerInputTabbedPanel(){return playerInputTabbedPanel;}

    public JLabel getLabelName(){ return lblName; }

    public JLabel getLblChangeIcon(){ return lblChangeIcon; }

    private void paintPanel(Graphics g, JPanel panel)
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
        paintPanel(g, this);
    }


}
