package View.Pages;

import View.Elements.ImageComponent;

import javax.swing.border.EmptyBorder;
import java.awt.*;

public class StartingMenuPanel extends ResizablePanel
{
    private final String pathImages = MainFrame.IMAGE_PATH +"StartingMenuPanel/";
    //Components
    private ImageComponent startGameLabel;
    private ImageComponent settingLabel;
    private ImageComponent quitLabel;


    //Paint variables
    private final Color lightBlue = new Color(0, 114, 187);
    private final Color darkBlue = new Color(7, 71, 113);
    private final int offset = 20;
    private final int angle = 15;

    public StartingMenuPanel()
    {
        super(480, 500, 6);
        setName("Starting menu panel");
        setLayout(new BorderLayout());

        setOpaque(false);
        InitializeComponents();
        resizeComponents();
    }

    private void InitializeComponents()
    {
        startGameLabel = new ImageComponent(pathImages + "Startgame.png");
        settingLabel = new ImageComponent(pathImages + "Settings.png");
        quitLabel = new ImageComponent(pathImages + "Quit.png");
        quitLabel.setBorder(new EmptyBorder(5,0,0,0));

        add(startGameLabel, BorderLayout.NORTH);
        add(settingLabel, BorderLayout.CENTER);
        add(quitLabel, BorderLayout.SOUTH);
    }

    public ImageComponent getGameChoiceIcon(){
        return startGameLabel;
    }

    public ImageComponent getSettingIcon(){
        return settingLabel;
    }

    public ImageComponent getQuitIcon(){
        return quitLabel;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(darkBlue);
        g.fillRoundRect(0, 0, panelWidth, panelHeight, angle, angle);
        g.setColor(lightBlue);
        g.fillRect(offset, offset, panelWidth - offset * 2, panelHeight - offset * 2);
    }
}
