package View;

import Controller.MainFrameController;
import Utilities.Utils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.util.Map;

public class ProfilePanel extends ResizablePanel {

    CircleImage im;

    public ProfilePanel(MainFrameController mfc){
        super(225, 90, 0);
        imagePath = "resources/images/MainFrame/StartingMenuPanel/ProfilePanel/";

        setLayout(new GridBagLayout());
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

        im = new CircleImage(imagePath + "discard.png", 60, 60);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 3;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        add(im, gbc);

        JLabel nome = new JLabel("Anonymous");
        nome.setFont(new Font(nome.getFont().getName(), Font.PLAIN, 18));
        nome.setBorder(new EmptyBorder(3,0,0,3));
        nome.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //cambia account
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setFont(0);
                nome.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setFont(-1);
            }

            void setFont(int onOff){
                Font font = nome.getFont();
                Map attributes = font.getAttributes();
                attributes.put(TextAttribute.UNDERLINE, onOff);
                nome.setFont(font.deriveFont(attributes));
            }

        });
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.weightx = 0.7;
        gbc.weighty = 0.7;
        add(nome, gbc);

        JLabel livello = new JLabel("level 1");
        livello.setBorder(new EmptyBorder(0,0,0,3));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.7;
        gbc.weighty = 0.1;
        add(livello, gbc);

        JProgressBar xp = new JProgressBar();
        xp.setPreferredSize(new Dimension(80, 15));
        xp.setStringPainted(true);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 0.7;
        gbc.weighty = 0.9;
        add(xp, gbc);
    }

    void DrawLoggedPanel(){

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.green);
        g.fillRect(0,0, getWidth(), getHeight());

        Graphics2D g2 = (Graphics2D) g;
        Utils.applyQualityRenderingHints(g2);
        int thickness = 2;
        g2.setStroke(new BasicStroke(thickness));
        g2.setColor(Color.BLACK);
        g2.drawOval(im.getX() - thickness, im.getY() - thickness, im.getWidth() + thickness, im.getHeight() + thickness);
    }
}
