package View;

import Controller.MainFrameController;
import Utilities.Utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class ProfilePanel extends ResizablePanel {

<<<<<<< HEAD
    JPanel p1;
    CircleImage profilePicture;
    JLabel name;
    JLabel level;
    JProgressBar xpBar;
=======
    private CircleImage profilePicture;
    private JLabel name;
    private JLabel level;
    private JProgressBar xpBar;
>>>>>>> d2b2d31b67b9ec4aff119c2e8430bbea3894ec7b


    public ProfilePanel(MainFrameController mfc){
<<<<<<< Updated upstream
        super(230, 95, 0);
=======
        super(225, 90, 0);
        setPreferredSize(new Dimension(225, 90));
>>>>>>> Stashed changes
        imagePath = "resources/images/MainFrame/StartingMenuPanel/ProfilePanel/";

        setLayout(new FlowLayout());
        //setLayout(new GridBagLayout());
        InitializeComponents();
    }
    private void InitializeComponents()
    {

        /*
        icons = new ImageComponent[]{
                new ImageComponent(imagePath + "1"),
                new ImageComponent(imagePath + "2"),
                new ImageComponent(imagePath + "3"),
                new ImageComponent(imagePath + "4")
        };
        add(icons[0]);
        add(icons[1]);
        add(icons[2]);
        add(icons[3]);
         */

        GridBagConstraints gbc = new GridBagConstraints();

        profilePicture = new CircleImage(imagePath + "discard.png", 60, 60);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 3;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        add(profilePicture, gbc);

        name = new JLabel("Anonymous");
        name.setFont(new Font(name.getFont().getName(), Font.PLAIN, 18));
        name.setBorder(new EmptyBorder(3,0,0,3));
        name.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //cambia account
                panelHeight += !p1.isVisible()?p1.getHeight():0;
                p1.setVisible(true);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setFont(0);
                name.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setFont(-1);
            }

            //UNDERLINE 0 = sottolineato
            //UNDERLINE -1 = non sottolineato
            void setFont(int onOff){
                Font font = name.getFont();
                Map attributes = font.getAttributes();
                attributes.put(TextAttribute.UNDERLINE, onOff);
                name.setFont(font.deriveFont(attributes));
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.weightx = 0.7;
        gbc.weighty = 0.7;
        add(name, gbc);

        level = new JLabel("Level 1 ty");
        level.setBorder(new EmptyBorder(0,0,0,3));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.7;
        gbc.gridheight = 1;
        gbc.weighty = 0.9;
        add(level, gbc);

        xpBar = new JProgressBar();
        xpBar.setPreferredSize(new Dimension(80, 15));
        xpBar.setStringPainted(true);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridheight = 1;
        gbc.weightx = 0.7;
        gbc.weighty = 0.9;
        add(xpBar, gbc);

        p1 = new JPanel();
        p1.setPreferredSize(new Dimension(300, 200));
        p1.setSize(300,200);
        p1.setVisible(false);
        p1.setBackground(Color.darkGray);
        var b = new JLabel("è uno sballo");

        b.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                panelHeight -= p1.isVisible()?p1.getHeight():0;
                p1.setVisible(false);
            }
        });
        p1.add(b);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.7;
        gbc.gridwidth = 2;
        gbc.weighty = 0.9;
        add(p1,gbc);


    }

    void DrawLoggedPanel(){

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //g.setColor(Color.green);

        Graphics2D g2 = (Graphics2D)g;
        Utils.applyQualityRenderingHints(g2);
        g2.setColor(new Color(55, 166, 71));
        g2.fillRect(0,0, getWidth(), getHeight());
        int thickness = 10;
        g2.setColor(new Color(55, 100, 71));
        g2.setStroke(new BasicStroke(thickness));
        g2.drawRect(0, -thickness, getWidth()+thickness, getHeight()+thickness);

        thickness = 2;
        g2.setStroke(new BasicStroke(thickness));
        g2.setColor(Color.BLACK);
        g2.drawOval(profilePicture.getX() - thickness, profilePicture.getY() - thickness, profilePicture.getWidth() + thickness, profilePicture.getHeight() + thickness);
    }

    public class CircleImage extends JComponent {

        private Image imm;

        private int width;
        private int height;

        public CircleImage(String path, int width, int height){
            this.width = width;
            this.height = height;
            setPreferredSize(new Dimension(width, height));

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
