package View;

import Controller.MainFrameController;
import Utilities.Utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ProfilePanel extends ResizablePanel {

    private JPanel smallPanel;

    private CircleImage profilePicture;
    private JLabel name;
    private JLabel level;
    private JProgressBar xpBar;

    private JButton quit;
    private JLabel changeIcon;

    private final Color lightGreen = new Color(55, 166, 71);
    private final Color darkGreen = new Color(55, 100, 71);

    public ProfilePanel(MainFrameController mfc){
        super(350, 450, 0);
        imagePath = "resources/images/MainFrame/StartingMenuPanel/ProfilePanel/";

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

    private void InitializedComponents(){
        //Shared
        profilePicture = new CircleImage(imagePath + "discard.png", 60, 60);

        name = new JLabel("Anonymous");
        name.setFont(new Font(name.getFont().getName(), Font.PLAIN, 18));
        name.setBorder(new EmptyBorder(3,0,0,3));

        level = new JLabel("Level 1 ty");
        level.setBorder(new EmptyBorder(0,0,0,3));

        xpBar = new JProgressBar();
        xpBar.setPreferredSize(new Dimension(80, 15));
        xpBar.setStringPainted(true);

        //Main Panel
        quit = new JButton("X");
        quit.setFont(new Font(name.getFont().getName(), Font.BOLD, 18));
        quit.setForeground(Color.RED);
        quit.setBackground(lightGreen);

        changeIcon = new JLabel("Change icon");
        changeIcon.setFont(new Font(name.getFont().getName(), Font.PLAIN, 13));
        changeIcon.setBorder(new LineBorder(Color.BLACK, 1, true));
        changeIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public void InitializeSmallPanel()
    {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 3;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        smallPanel.add(profilePicture, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.weightx = 0.7;
        gbc.weighty = 0.5;
        smallPanel.add(name, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.7;
        gbc.weighty = 0.5;
        smallPanel.add(level, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridheight = 1;
        gbc.weightx = 0.7;
        gbc.weighty = 0.5;
        smallPanel.add(xpBar, gbc);
    }

    public void InitializeMainPanel()
    {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0.1;
        gbc.weighty = 0.01;
        add(quit, gbc);

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
        add(changeIcon, gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.weightx = 0.5;
        gbc.weighty = 0.07;
        add(name, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridheight = 1;
        gbc.weightx = 0.5;
        gbc.weighty = 0.01;
        add(level, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridheight = 1;
        gbc.weightx = 0.5;
        gbc.weighty = 0.01;
        add(xpBar, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridheight = 1;
        gbc.gridwidth = 3;
        gbc.weightx = 0.9;
        gbc.weighty = 0.85;
        add(new JPanel(), gbc);
    }

    public JPanel getSmallPanel(){
        InitializeSmallPanel();
        return smallPanel;
    }

    public JLabel getLabelName(){
        return name;
    }

    public JButton getQuit(){
        return quit;
    }

    public JLabel getChangeIcon(){
        return changeIcon;
    }

    public CircleImage getProfilePicture(){
        return profilePicture;
    }

    private void paintPanel(Graphics g, JPanel panel){
        Graphics2D g2 = (Graphics2D)g;
        Utils.applyQualityRenderingHints(g2);
        g2.setColor(lightGreen);
        g2.fillRect(0,0, panel.getWidth(), panel.getHeight());
        int thickness = 10;
        g2.setColor(darkGreen);
        g2.setStroke(new BasicStroke(thickness));
        g2.drawRect(0, -thickness, panel.getWidth()+thickness, panel.getHeight()+thickness);

        thickness = 2;
        g2.setStroke(new BasicStroke(thickness));
        g2.setColor(Color.BLACK);
        g2.drawOval(profilePicture.getX() - thickness, profilePicture.getY() - thickness, profilePicture.getWidth() + thickness, profilePicture.getHeight() + thickness);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintPanel(g, this);
    }

    public class CircleImage extends JComponent {

        private Image imm;

        private int width;
        private int height;

        public CircleImage(String path, int width, int height){
            this.width = width;
            this.height = height;
            setPreferredSize(new Dimension(width, height));
            setCircleImage(path);
        }

        public void setCircleImage(String path){
            try {
                BufferedImage master = ImageIO.read(new File(path));
                Image scaled = master.getScaledInstance(width, height, Image.SCALE_SMOOTH);

                int diameter = Math.min(width, height);
                BufferedImage mask = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

                Graphics2D g2d = mask.createGraphics();
                Utils.applyQualityRenderingHints(g2d);

                g2d.fillOval(0, 0, diameter - 1, diameter - 1);
                g2d.dispose();

                BufferedImage masked = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
                g2d = masked.createGraphics();
                Utils.applyQualityRenderingHints(g2d);
                int x = (diameter - width) / 2;
                int y = (diameter - height) / 2;
                g2d.drawImage(scaled, x, y, null);
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_IN));
                g2d.drawImage(mask, 0, 0, null);
                g2d.dispose();

                imm = masked;
                //setIcon(new ImageIcon(masked));
                repaint();
            }
            catch (IOException e) {
                System.out.println("Cannot find image");
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            Utils.applyQualityRenderingHints(g2);
            g2.drawImage(imm, 0, 0, width, height, this);
        }
    }
}
