package View;

import Model.Rules.UnoGameRules;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

public class GameChoicePanel extends ResizablePanel{

    public GameChoicePanel(MainFrame mainFrame) {
        super(mainFrame);
        setLayout(new GridBagLayout());
        super.Percentages = new HashMap<>(){{
            put(MainFrame.Dimensions.FULLHD, new Double[]{0.60,0.65});
            put(MainFrame.Dimensions.WIDESCREEN, new Double[]{0.75,0.60});
            put(MainFrame.Dimensions.HD, new Double[]{0.65,0.60});
        }};
        offset = 6;
        addScalingListener();
        setBackground(Color.pink);
        //setOpaque(false);
        InitializeComponents();
    }

    private static final String pathDeck = "resources/images/White_deck/";

    void InitializeComponents(){
        //Components
        ImageComponent2 basicGame = new ImageComponent2(pathDeck + "01.png", ImageComponent2.Size.MEDIUM);
        ImageComponent2 memeGame = new ImageComponent2("", ImageComponent2.Size.MEDIUM);
        ImageComponent2 sevenoGame = new ImageComponent2("", ImageComponent2.Size.MEDIUM);

        JLabel indietro = new JLabel("CLICCA QUI PER SCAPPARE DAI CALABRESI");

        //Listeners
        basicGame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainFrame.SetVisiblePanel(MainFrame.Panels.GAME);
                setVisible(false);
            }
        });

        indietro.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainFrame.SetVisiblePanel(MainFrame.Panels.STARTMENU);
                setVisible(false);
            }
        });

        //Layout
        GridBagConstraints gbc = new GridBagConstraints();

        //------------Center
        gbc.gridx = 0;      gbc.gridy = 0;
        gbc.weightx = 0.5;  gbc.weighty = 0.5;
        add(basicGame, gbc);

        //------------Left
        gbc.gridx = 1;      gbc.gridy = 0;
        gbc.weightx = 0.5;  gbc.weighty = 0.5;
        add(basicGame, gbc);

        //------------Right
        gbc.gridx = 2;      gbc.gridy = 0;
        gbc.weightx = 0.5;  gbc.weighty = 0.5;
        add(basicGame, gbc);

        //------------Right
        gbc.gridx = 3;      gbc.gridy = 1;
        gbc.weightx = 0.1;  gbc.weighty = 0.1;
        add(basicGame, gbc);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        /*
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(new Color(50, 166, 92));
        g2.fillRoundRect(0,0, panelWidth, panelHeight, 15, 15);
        g2.setStroke(new BasicStroke(5));
        g2.setColor(new Color(19, 80, 41));
        g2.drawRoundRect(0,0,panelWidth,panelHeight, 15, 15);
        */

    }
}
