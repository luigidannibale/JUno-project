package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.util.Arrays;
import java.util.HashMap;

public class StartingMenuPanel extends ResizablePanel
{
    private static final String imagePath = "resources/images/MainFrame/StartingMenuPanel/";

    public StartingMenuPanel(JPanel[] panels,MainFrame mainFrame)
    {
        super(mainFrame);
        setLayout(new BorderLayout());
        Percentages = new HashMap<>(){{
            put(MainFrame.Dimensions.FULLHD, new Double[]{0.25,0.45});
            put(MainFrame.Dimensions.WIDESCREEN, new Double[]{0.27,0.45});
            put(MainFrame.Dimensions.HD, new Double[]{0.33,0.55});
        }};
        offset = 6;
        addScalingListener();
        setOpaque(false);
        InitializeComponents(panels);
        setVisible(true);
    }

    private void InitializeComponents(JPanel[] panels)
    {
        icons = new ImageComponent[]{
                new ImageComponent(imagePath + "Startgame", ImageComponent.Size.BIG),
                new ImageComponent(imagePath + "Settings", ImageComponent.Size.BIG),
                new ImageComponent(imagePath + "Quit", ImageComponent.Size.BIG)
        };

        //NewGamePanel
        icons[0].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //changePanel(panels[0]);
                mainFrame.setGameScreen(true);
            }
        });
        //SettingPanel
        icons[1].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                changePanel(panels[1]);
            }
        });
        //Quit
        icons[2].addMouseListener(new MouseAdapter() { // Quit button
            @Override
            public void mouseClicked(MouseEvent e) {

                try //If the parent has a listener that manages the window closing it links that event, if an exception is generated the frame just get closed
                {
                    ((WindowAdapter) ((Arrays.stream(mainFrame.getWindowListeners()).toArray())[0])).windowClosing(null);
                }
                catch(Exception ex) { mainFrame.dispose(); }
            }
        });

        add(icons[0], BorderLayout.NORTH);
        add(icons[1], BorderLayout.CENTER);
        add(icons[2], BorderLayout.SOUTH);

        Arrays.stream(icons).forEach(op -> op.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { op.scaleUpIcon(); }
            @Override
            public void mouseExited(MouseEvent e)  { op.resetIcon(); }
        }));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        var off = 20;
        Color bluchiaro = new Color(0, 114, 187);
        Color bluscuro = new Color(7, 71, 113);
        var angle = 15;
        g.setColor(bluscuro);
        //g.fillRect();
        g.fillRoundRect(0, 0, panelWidth, panelHeight, angle, angle);
        g.setColor(bluchiaro);
        g.fillRect(off, off, panelWidth - off * 2, panelHeight - off * 2);
    }
}
