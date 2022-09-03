package View.Pages.ProfileManagement;

import Utilities.Config;
import Utilities.Utils;
import View.Elements.CircleImage;
import View.Elements.ViewPlayer;
import View.Pages.ResizablePanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ProfilePanel extends ResizablePanel {

    private JPanel smallPanel;

    //private CircleImage profilePicture;
    private JLabel lblName;
    private JLabel lblLevel;
    private JProgressBar levelXpBar;
    //private JButton btnQuit;
    private JLabel lblChangeIcon;
    private ViewPlayer player;
    private UpdatePlayerPanel updateTabbedPanel;
    private final Color backColor = new Color(55, 166, 71);
    private final Color borderColor = new Color(55, 100, 71);

    public ProfilePanel(ViewPlayer player)
    {
        super(400, 550, 0);
        imagePath = "resources/images/MainFrame/StartingMenuPanel/ProfilePanel/";
        this.player = player;

        //setLayout(new FlowLayout());
        setLayout(new GridBagLayout());
        setVisible(false);

        smallPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                paintPanel(g, this);
            }
        };
        smallPanel.setSize(230, 95);
        smallPanel.setPreferredSize(new Dimension(230, 95));
        smallPanel.setOpaque(false);
        smallPanel.setLayout(new GridBagLayout());

        InitializedComponents();
    }

    private void InitializedComponents()
    {
        setName("ProfilePanel");
        lblName = new JLabel();
        lblName.setFont(new Font(lblName.getFont().getName(), Font.PLAIN, 18));
        lblName.setBorder(new EmptyBorder(3,0,0,3));

        lblLevel = new JLabel();
        lblLevel.setBorder(new EmptyBorder(0,0,0,3));

        levelXpBar = new JProgressBar();
        levelXpBar.setPreferredSize(new Dimension(80, 15));
        levelXpBar.setStringPainted(true);

        //Main Panel
        /*
        btnQuit = new JButton("Close");
        btnQuit.setFont(new Font(lblName.getFont().getName(), Font.BOLD, 18));
        btnQuit.setForeground(Color.RED);
        btnQuit.setBackground(backColor);
         */

        lblChangeIcon = new JLabel("Change icon");
        lblChangeIcon.setFont(new Font(lblName.getFont().getName(), Font.PLAIN, 13));
        lblChangeIcon.setBorder(new LineBorder(Color.BLACK, 1, true));
        lblChangeIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        update();
        updateTabbedPanel = new UpdatePlayerPanel(backColor,borderColor);
    }
    public void update()
    {
        lblName.setText(Config.loggedPlayer.getName());
        double livello = Config.loggedPlayer.getStats().getLevel();
        lblLevel.setText("Livello " + (int) livello);
        levelXpBar.setValue((int) Math.round(100* (livello - (int)livello)));
    }
    public void InitializeSmallPanel()
    {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 3;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        smallPanel.add(player.getProfilePicture(), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.weightx = 0.7;
        gbc.weighty = 0.5;
        smallPanel.add(lblName, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.7;
        gbc.weighty = 0.5;
        smallPanel.add(lblLevel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridheight = 1;
        gbc.weightx = 0.7;
        gbc.weighty = 0.5;
        smallPanel.add(levelXpBar, gbc);
    }

    public void InitializeMainPanel()
    {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0.1;
        gbc.weighty = 0.01;
        //add(btnQuit, gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 4;
        gbc.weightx = 0.5;
        gbc.weighty = 0.03;
        add(player.getProfilePicture(), gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridheight = 1;
        gbc.weightx = 0.5;
        gbc.weighty = 0.01;
        add(lblChangeIcon, gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.weightx = 0.5;
        gbc.weighty = 0.07;
        add(lblName, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridheight = 1;
        gbc.weightx = 0.5;
        gbc.weighty = 0.01;
        add(lblLevel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridheight = 1;
        gbc.weightx = 0.5;
        gbc.weighty = 0.01;
        add(levelXpBar, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridheight = 1;
        gbc.gridwidth = 3;

        gbc.weightx = 0.9;
        gbc.weighty = 0.85;
        add(updateTabbedPanel, gbc);

        updateTabbedPanel.clearTextField();
    }

    public JPanel getSmallPanel()
    {
        InitializeSmallPanel();
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
