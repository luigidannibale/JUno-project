package View;

import Controller.MainFrameController;

import javax.swing.border.EmptyBorder;
import java.awt.*;

public class StartingMenuPanel extends ResizablePanel
{
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
        setLayout(new BorderLayout());
        imagePath = "resources/images/MainFrame/StartingMenuPanel/";
        setOpaque(false);
        InitializeComponents();
    }

    private void InitializeComponents()
    {
        startGameLabel = new ImageComponent(imagePath + "Startgame.png");
        settingLabel = new ImageComponent(imagePath + "Settings.png");
        quitLabel = new ImageComponent(imagePath + "Quit.png");
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
