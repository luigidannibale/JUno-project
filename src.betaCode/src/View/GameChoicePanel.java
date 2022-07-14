package View;

import java.awt.*;
import java.util.HashMap;

public class GameChoicePanel extends ResizablePanel{

    public GameChoicePanel(MainFrame mainFrame) {
        super(mainFrame);
        super.Percentages = new HashMap<>(){{
            put(MainFrame.Dimensions.FULLHD, new Double[]{0.57,0.60});
            put(MainFrame.Dimensions.WIDESCREEN, new Double[]{0.70,0.55});
            put(MainFrame.Dimensions.HD, new Double[]{0.60,0.55});
        }};
        offset = 6;
        addScalingListener();
        setOpaque(false);
        InitializeComponents();
    }

    void InitializeComponents(){

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke(5));
        g2.setColor(Color.green);
        g2.fillRect(0,0, panelWidth, panelHeight);
    }
}
