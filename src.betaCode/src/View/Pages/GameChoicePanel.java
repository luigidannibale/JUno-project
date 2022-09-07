package View.Pages;

import Controller.GameChoiceController;
import Utilities.Config;
import Utilities.Utils;
import View.Elements.GifComponent;
import View.Elements.ImageComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;




public class GameChoicePanel extends ResizablePanel
{
    private final String pathImages = MainFrame.IMAGE_PATH +"GameChoicePanel/";
    private GifComponent[] gameModes;
    private ImageComponent[] infoLabels;
    private BufferedImage[] infos;

    private ImageComponent back;
    private ImageComponent title;

    private int currentInfo = -1;
    private int infosWidth = 280;
    private int infosHeight = 350;

    public GameChoicePanel()
    {
        super(1080, 552, 6);

        setLayout(new GridBagLayout());
        setOpaque(false);
        setVisible(false);
        InitializeComponents();
        resizeComponents();

        infosWidth *= Config.scalingPercentage;
        infosHeight *= Config.scalingPercentage;
    }

    void InitializeComponents()
    {

        title = new ImageComponent(pathImages + "choose gamemode.png", -1, -1, false);

        int length = GameChoiceController.GameMode.values().length;
        gameModes = new GifComponent[length];
        infoLabels = new ImageComponent[length];
        infos = new BufferedImage[length];
        ImageIcon baq = new ImageIcon(pathImages +"baq.png");
        Arrays.stream(GameChoiceController.GameMode.values()).forEach(gameMode ->
        {
            int index = gameMode.ordinal();
            String path = pathImages + gameMode.name().toLowerCase();
            gameModes[index] = new GifComponent(path, 150, 255);
            infoLabels[index] = new ImageComponent(pathImages +"baq.png",-1,-1,false);
            //@deprecated       @quarantined
            //infoLabels[index] = new GifComponent(imagePath+"baq", 50, 52);
            //@deprecated       @quarantined
            infos[index] = Utils.getBufferedImage(path + "_info.png");
        });

        back = new ImageComponent(pathImages + "back.png", -1, -1, false);

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

        gbc.gridx = 1;      gbc.gridy = 2;
        gbc.weightx = 0.15;  gbc.weighty = 0.3;
        add(infoLabels[0], gbc);

        //------------Left
        gbc.gridx = 0;      gbc.gridy = 1;
        gbc.weightx = 0.15;  gbc.weighty = 0.3;
        add(getMemeGame(), gbc);

        gbc.gridx = 0;      gbc.gridy = 2;
        gbc.weightx = 0.15;  gbc.weighty = 0.3;
        add(infoLabels[1], gbc);

        //------------Right
        gbc.gridx = 2;      gbc.gridy = 1;
        gbc.weightx = 0.15;  gbc.weighty = 0.3;
        add(getSevenoGame(), gbc);

        gbc.gridx = 2;      gbc.gridy = 2;
        gbc.weightx = 0.15;  gbc.weighty = 0.3;
        add(infoLabels[2], gbc);

        //------------Quit
        gbc.anchor = GridBagConstraints.LAST_LINE_END;
        gbc.gridx = 2;      gbc.gridy = 2;
        gbc.weightx = 0.15;  gbc.weighty = 0.01;
        add(back, gbc);
    }

    public GifComponent getBasicGame(){ return gameModes[GameChoiceController.GameMode.CLASSIC_RULES.ordinal()]; }

    public GifComponent getMemeGame(){ return gameModes[GameChoiceController.GameMode.MEME_RULES.ordinal()]; }

    public GifComponent getSevenoGame(){ return gameModes[GameChoiceController.GameMode.SEVENO_RULES.ordinal()]; }

    public GifComponent[] getGameModes() { return gameModes; }

    public JLabel[] getInfoLabels() { return infoLabels; }

    public ImageComponent getBack(){ return back; }

    public void setCurrentInfo(int index) { this.currentInfo = index; }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(new Color(50, 166, 92));
        g2.fillRoundRect(0,0, panelWidth, panelHeight, 50, 50);
        g2.setStroke(new BasicStroke(10));
        g2.setColor(new Color(19, 80, 41));
        g2.drawRoundRect(0,0,panelWidth,panelHeight, 50, 50);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        if (currentInfo != -1){
            var gameMode = gameModes[currentInfo];
            int centerX = gameMode.getX() + (gameMode.getWidth() - infosWidth) / 2;
            int centerY = gameMode.getY() + (gameMode.getHeight() - infosHeight) / 2;
            g.drawImage(infos[currentInfo], centerX, centerY, infosWidth, infosHeight, null);
        }
    }
}
