package View.Pages;

import Controller.GameChoiceController;
import View.Elements.CustomMouseAdapter;
import View.Elements.GifComponent;
import View.Elements.ImageComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;


public class GameChoicePanel extends ResizablePanel
{
    GifComponent[] gameModes;
    ImageComponent back;

    ImageComponent title;

    JLabel basicInfo, memeInfo, sevenoInfo;

    JLabel[] infoLabels = new JLabel[3];

    public GameChoicePanel()
    {
        super(1080, 552, 6);

        imagePath = "resources/images/MainFrame/GameChoicePanel/";

        setLayout(new GridBagLayout());
        setOpaque(false);
        setVisible(false);
        InitializeComponents();
        resizeComponents();
    }

    //private static final String path = "resources/images/MainFrame/GameChoicePanel/";

    void InitializeComponents()
    {

        title = new ImageComponent(imagePath + "choose gamemode.png", -1, -1, false);

        gameModes = new GifComponent[GameChoiceController.GameMode.values().length];
        ImageIcon baq = new ImageIcon(imagePath+"baq.png");
        Arrays.stream(GameChoiceController.GameMode.values()).forEach(gameMode ->
                {
                    gameModes[gameMode.ordinal()] = new GifComponent(imagePath + gameMode.name().toLowerCase(), 150, 255);
                    infoLabels[gameMode.ordinal()] = new JLabel(baq);
                });


        back = new ImageComponent(imagePath + "back.png", -1, -1, false);

        //Layout
        GridBagConstraints gbc = new GridBagConstraints();

        //------------Title
        gbc.gridx = 0;      gbc.gridy = 0;
        gbc.weightx = 0.15;  gbc.weighty = 0.1;
        gbc.gridwidth = 3;
        add(title, gbc);

        //------------Center
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.gridx = 1;      gbc.gridy = 1;
        gbc.weightx = 0.15;  gbc.weighty = 0.3;
        gbc.gridwidth = 1;
        add(getBasicGame(), gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 1;      gbc.gridy = 2;
        gbc.weightx = 0.15;  gbc.weighty = 0.3;
        add(infoLabels[0], gbc);

        //------------Left
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.gridx = 0;      gbc.gridy = 1;
        gbc.weightx = 0.15;  gbc.weighty = 0.3;
        add(getMemeGame(), gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;      gbc.gridy = 2;
        gbc.weightx = 0.15;  gbc.weighty = 0.3;
        add(infoLabels[1], gbc);

        //------------Right
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.gridx = 2;      gbc.gridy = 1;
        gbc.weightx = 0.15;  gbc.weighty = 0.3;
        add(getSevenoGame(), gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 2;      gbc.gridy = 2;
        gbc.weightx = 0.15;  gbc.weighty = 0.3;
        add(infoLabels[2], gbc);

        //------------Scappa
        gbc.anchor = GridBagConstraints.LAST_LINE_END;
        gbc.gridx = 2;      gbc.gridy = 2;
        gbc.weightx = 0.15;  gbc.weighty = 0.01;
        add(back, gbc);
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

    public GifComponent[] getGameModes() { return gameModes; }

    public JLabel[] getInfoLabels() { return infoLabels; }

    public ImageComponent getBack(){
        return back;
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
