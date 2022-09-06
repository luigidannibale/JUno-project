package View.Pages.ProfileManagement;

import Utilities.Config;
import Utilities.Utils;
import View.Elements.CircleImage;
import View.Elements.ImageComponent;
import View.Elements.ViewPlayer;
import View.Elements.VolumeSlider;
import View.Pages.ResizablePanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ProfilePanel extends ResizablePanel
{

    private ResizablePanel smallPanel;

    //private CircleImage profilePicture;
    private final Font fontName = new Font("Monsterrat", Font.PLAIN, (int) (18 * Config.scalingPercentage));
    private final Font fontLevel = new Font("Monsterrat", Font.PLAIN, (int) (13 * Config.scalingPercentage));
    private JLabel lblName;
    private JLabel lblLevel;
    private VolumeSlider levelXpBar;
    //private JButton btnQuit;
    private JLabel lblChangeIcon;
    private ImageComponent lblXp;
    private ViewPlayer player;
    private UpdatePlayerPanel updateTabbedPanel;
    private final Color backColor = new Color(55, 166, 71);
    private final Color borderColor = new Color(55, 100, 71);

    public ProfilePanel(ViewPlayer player)
    {
        super(450, 620, 0);
        imagePath = "resources/images/MainFrame/StartingMenuPanel/ProfilePanel/";
        this.player = player;

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
        //smallPanel.setPreferredSize(new Dimension(100, 100));
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


        lblXp = new ImageComponent(imagePath + "xp.png",-1,-1,false);

        levelXpBar = new VolumeSlider();
        levelXpBar.setName("levelXPbar");
        levelXpBar.setPreferredSize(new Dimension(140, 20));
        levelXpBar.setStringPainted(true);

        lblChangeIcon = new JLabel("Change icon");
        lblChangeIcon.setName("lblChangeName");
        lblChangeIcon.setFont(fontLevel);
        lblChangeIcon.setBorder(new LineBorder(Color.BLACK, 1, true));
        lblChangeIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        update();
        updateTabbedPanel = new UpdatePlayerPanel(backColor,borderColor);

        initializeMainPanel();
        resizeComponents();
    }
    public void update()
    {
        lblName.setText(Config.loggedPlayer.getName());
        double livello = Config.loggedPlayer.getStats().getLevel();
        lblLevel.setText("Livello " + (int) livello);
        levelXpBar.setValue((int) Math.round(100* (livello - (int)livello)));
    }
    public void initializeSmallPanel()
    {
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
        smallPanel.add(player.getProfilePicture(), gbc);

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
        GridBagConstraints gbc = new GridBagConstraints();

        lblName.setFont(fontName.deriveFont(fontName.getSize() * 2f));
        lblName.setText(lblName.getText());

        lblLevel.setFont(fontLevel.deriveFont(fontLevel.getSize() * 2f));
        lblLevel.setText(lblLevel.getText());

        lblChangeIcon.setFont(fontLevel.deriveFont(fontLevel.getSize() * 1.f));
        lblChangeIcon.setText(lblChangeIcon.getText());

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 4;
        gbc.weightx = 0.9;
        gbc.weighty = 0.03;
        add(player.getProfilePicture(), gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridheight = 1;
        gbc.weightx = 0.5;
        gbc.weighty = 0.01;
        add(lblChangeIcon, gbc);

        gbc.anchor = GridBagConstraints.LINE_START;
//        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.weightx = 0.5;
        gbc.weighty = 0.07;
        add(lblName, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridheight = 1;
        gbc.weightx = 0.5;
        gbc.weighty = 0.01;
        add(lblLevel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.weightx = 0.01;
        gbc.weighty = 0.01;
        add(lblXp, gbc);

        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridheight = 1;
        //gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.weightx = 0.5;
        gbc.weighty = 0.01;
        add(levelXpBar, gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridheight = 1;
        gbc.gridwidth = 3;
        gbc.weightx = 0.9;
        gbc.weighty = 0.85;
        add(updateTabbedPanel, gbc);

        updateTabbedPanel.clearTextField();
    }

    @Override
    public void resizeComponents()
    {
        for (Component component : getComponents())
        {
            System.out.println(component.getClass());
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

    public UpdatePlayerPanel getUpdateTabbedPanel(){return updateTabbedPanel;}
    public JLabel getLabelName(){ return lblName; }

    //public JButton getBtnQuit(){ return btnQuit; }

    public JLabel getLblChangeIcon(){ return lblChangeIcon; }

//    public CircleImage getProfilePicture(){ return profilePicture; }
//
//    public void setProfilePicture(CircleImage profilePicture) { this.profilePicture = profilePicture; }

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
        CircleImage profilePicture = player.getProfilePicture();
        g2.drawOval(profilePicture.getX() - thickness, profilePicture.getY() - thickness, profilePicture.getWidth() + thickness, profilePicture.getHeight() + thickness);
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        paintPanel(g, this);
    }


}
