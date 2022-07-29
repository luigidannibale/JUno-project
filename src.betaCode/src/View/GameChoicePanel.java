package View;

import Controller.MainFrameController;

import javax.swing.*;
import java.awt.*;

import static View.ImageComponent.Formats.GIF;

public class GameChoicePanel extends ResizablePanel{


    GifComponent basicGame;
    GifComponent memeGame;
    GifComponent sevenoGame;

    JLabel indietro;

    public GameChoicePanel(MainFrameController mainFrame) {
        super(mainFrame);
        setLayout(new GridBagLayout());
        //setLayout(new BorderLayout());
        /*
        super.Percentages = new HashMap<>(){{
            put(MainFrame.Dimensions.FULLHD, new Double[]{0.60,0.65});
            put(MainFrame.Dimensions.WIDESCREEN, new Double[]{0.75,0.60});
            put(MainFrame.Dimensions.HD, new Double[]{0.65,0.60});
        }};


        percentX = 0.65;
        percentY = 0.62;
         */

        offset = 6;
        //addScalingListener();
        setOpaque(false);
        InitializeComponents();
    }

    private static final String path = "resources/images/MainFrame/GameChoicePanel/";

    void InitializeComponents(){
        //Components
        /*
        ImageComponent2 basicGame = new ImageComponent2(path + "ClassicRules.gif", ImageComponent2.Size.SMALL);
        ImageComponent2 memeGame = new ImageComponent2("", ImageComponent2.Size.SMALL);
        ImageComponent2 sevenoGame = new ImageComponent2("", ImageComponent2.Size.SMALL);
        */
        //basicGame = new ImageComponent(path + "ClassicRules",GIF);
        //basicGame.AddSclingOnHoveringGif();
        basicGame = new GifComponent(path + "ClassicRules");
        memeGame = new GifComponent(path + "SeriousRules");
        sevenoGame = new GifComponent(path + "SevenO");

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
        g2.fillRoundRect(0,0, panelWidth, panelHeight, 15, 15);
        g2.setStroke(new BasicStroke(5));
        g2.setColor(new Color(19, 80, 41));
        g2.drawRoundRect(0,0,panelWidth,panelHeight, 15, 15);
    }
}
