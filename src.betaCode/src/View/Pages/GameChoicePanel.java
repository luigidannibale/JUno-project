package View.Pages;

import Controller.GameChoiceController;
import Controller.MainFrameController;
import View.Elements.GifComponent;

import javax.swing.*;
import java.awt.*;


public class GameChoicePanel extends ResizablePanel{

    GifComponent basicGame,memeGame, sevenoGame;
    GifComponent gameModes[];
    JLabel indietro;

    JLabel title;

    JLabel basicInfo, memeInfo, sevenoInfo;

    public GameChoicePanel() {
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
        //basicGame = new GifComponent(imagePath + "ClassicRules", 150, 225);
        //basicGame.AddSclingOnHoveringGif();
        //basicGame = new GifComponent(imagePath + "ClassicRules");

        //memeGame = new GifComponent(imagePath + "SeriousRules", 150, 225);
        //sevenoGame = new GifComponent(imagePath + "SevenO", 150, 225);

        gameModes = new GifComponent[GameChoiceController.GameMode.values().length];
        for (GameChoiceController.GameMode gameMode : GameChoiceController.GameMode.values()){
            gameModes[gameMode.ordinal()] = new GifComponent(imagePath + gameMode.name().toLowerCase(), 150, 255);
        }

        basicInfo = new JLabel("basic info");
        memeInfo = new JLabel("meme info");
        sevenoInfo = new JLabel("seveno info");



        indietro = new JLabel("CLICCA QUI");

        //Layout
        GridBagConstraints gbc = new GridBagConstraints();

        //------------Center
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 1;      gbc.gridy = 0;
        gbc.weightx = 0.15;  gbc.weighty = 0.7;
        add(getBasicGame(), gbc);

        gbc.anchor = GridBagConstraints.NORTH;
        gbc.gridx = 1;      gbc.gridy = 1;
        gbc.weightx = 0.15;  gbc.weighty = 0.2;
        add(basicInfo, gbc);

        //------------Left
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;      gbc.gridy = 0;
        gbc.weightx = 0.15;  gbc.weighty = 0.7;
        add(getMemeGame(), gbc);

        gbc.anchor = GridBagConstraints.NORTH;
        gbc.gridx = 0;      gbc.gridy = 1;
        gbc.weightx = 0.15;  gbc.weighty = 0.2;
        add(memeInfo, gbc);

        //------------Right
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 2;      gbc.gridy = 0;
        gbc.weightx = 0.15;  gbc.weighty = 0.7;
        add(getSevenoGame(), gbc);

        gbc.anchor = GridBagConstraints.NORTH;
        gbc.gridx = 2;      gbc.gridy = 1;
        gbc.weightx = 0.15;  gbc.weighty = 0.2;
        add(sevenoInfo, gbc);

        //------------Scappa
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.gridx = 2;      gbc.gridy = 2;
        gbc.weightx = 0.15;  gbc.weighty = 0.1;
        add(indietro, gbc);
    }

    public GifComponent getBasicGame(){
        return gameModes[GameChoiceController.GameMode.CLASSIC_RULES.ordinal()];
    }

    public GifComponent getMemeGame(){
        return gameModes[GameChoiceController.GameMode.MEME_RULES.ordinal()];
    }

    public GifComponent getSevenoGame(){
        return gameModes[GameChoiceController.GameMode.SEVENO_RULES.ordinal()];
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
