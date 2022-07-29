package View;

import Controller.MainFrameController;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.util.Arrays;
import java.util.HashMap;

import static View.ImageComponent.Formats.PNG;

public class StartingMenuPanel extends ResizablePanel
{


    public StartingMenuPanel(MainFrameController mainFrame)
    {
        super(480, 500, 6);
        setLayout(new BorderLayout());
        imagePath = "resources/images/MainFrame/StartingMenuPanel/";
        /*
        Percentages = new HashMap<>(){{
            put(MainFrame.Dimensions.FULLHD, new Double[]{0.25,0.45});
            put(MainFrame.Dimensions.WIDESCREEN, new Double[]{0.27,0.45});
            put(MainFrame.Dimensions.HD, new Double[]{0.33,0.55});
        }};

*/
        /*percentX = 0.28;
        percentY = 0.48;*/
        //offset = 6;
        //addScalingListener();
        setOpaque(false);
        InitializeComponents();

    }

    private void InitializeComponents()
    {
        icons = new ImageComponent[]{
                new ImageComponent(imagePath + "Startgame"+".png"),
                new ImageComponent(imagePath + "Settings"+".png"),
                new ImageComponent(imagePath + "Quit"+".png")
        };

        add(icons[0], BorderLayout.NORTH);
        add(icons[1], BorderLayout.CENTER);
        add(icons[2], BorderLayout.SOUTH);
    }

    public ImageComponent getGameChoiceIcon(){
        return icons[0];
    }

    public ImageComponent getSettingIcon(){
        return icons[1];
    }

    public ImageComponent getQuitIcon(){
        return icons[2];
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        var off = 20;
        Color bluchiaro = new Color(0, 114, 187);
        Color bluscuro = new Color(7, 71, 113);
        var angle = 15;
        g.setColor(bluscuro);
        g.fillRoundRect(0, 0, panelWidth, panelHeight, angle, angle);
        g.setColor(bluchiaro);
        g.fillRect(off, off, panelWidth - off * 2, panelHeight - off * 2);
    }
}
