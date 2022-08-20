package View;

import Controller.MainFrameController;
import Utilities.Utils;

import javax.swing.*;
import java.awt.*;


public class GameChoicePanel extends ResizablePanel{

    //ImageComponent basicGame;

    GifComponent basicGame,memeGame, sevenoGame;


    JLabel indietro;

    public GameChoicePanel(MainFrameController mainFrame) {
        super(1080, 552, 6);

        imagePath = "resources/images/MainFrame/GameChoicePanel/";

        setLayout(new GridBagLayout());
        setOpaque(false);
        setVisible(false);
        InitializeComponents();
    }

    //private static final String path = "resources/images/MainFrame/GameChoicePanel/";

    void InitializeComponents(){
        //Components
        /*
        ImageComponent2 basicGame = new ImageComponent2(path + "ClassicRules.gif", ImageComponent2.Size.SMALL);
        ImageComponent2 memeGame = new ImageComponent2("", ImageComponent2.Size.SMALL);
        ImageComponent2 sevenoGame = new ImageComponent2("", ImageComponent2.Size.SMALL);
        */
        basicGame = new GifComponent(imagePath + "ClassicRules", 150, 225);
        //basicGame.AddSclingOnHoveringGif();
        //basicGame = new GifComponent(imagePath + "ClassicRules");

        memeGame = new GifComponent(imagePath + "SeriousRules", 150, 225);
        sevenoGame = new GifComponent(imagePath + "SevenO", 150, 225);

        indietro = new JLabel("CLICCA QUI");

        //Layout
        GridBagConstraints gbc = new GridBagConstraints();

        //------------Center
        gbc.gridx = 1;      gbc.gridy = 0;
        gbc.weightx = 0.15;  gbc.weighty = 0.5;
        add(basicGame, gbc);
        //add(basicGame, BorderLayout.CENTER);

        //------------Left
        gbc.gridx = 0;      gbc.gridy = 0;
        gbc.weightx = 0.15;  gbc.weighty = 0.5;
        add(memeGame, gbc);
        //add(memeGame, BorderLayout.LINE_START);

        //------------Right
        gbc.gridx = 2;      gbc.gridy = 0;
        gbc.weightx = 0.15;  gbc.weighty = 0.5;
        add(sevenoGame, gbc);
        //add(basicGame2, BorderLayout.LINE_END);

        //------------Scappa
        gbc.gridx = 2;      gbc.gridy = 1;
        gbc.weightx = 0.15;  gbc.weighty = 0.1;
        add(indietro, gbc);
        //add(indietro, BorderLayout.PAGE_END);
    }

    public GifComponent getBasicGame(){
        return basicGame;
    }

    public GifComponent getMemeGame(){
        return memeGame;
    }

    public GifComponent getSevenoGame(){
        return sevenoGame;
    }

    public JLabel getIndietro(){
        return indietro;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(new Color(50, 166, 92));
        g2.fillRoundRect(0,0, panelWidth, panelHeight, 50, 50);
        g2.setStroke(new BasicStroke(10));
        g2.setColor(new Color(19, 80, 41));
        g2.drawRoundRect(0,0,panelWidth,panelHeight, 50, 50);
    }
}
