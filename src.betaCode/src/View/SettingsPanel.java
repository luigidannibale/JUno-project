package View;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class SettingsPanel extends ResizablePanel
{
    private static final String imagePath = "resources/images/MainFrame/SettingsPanel/";

    public SettingsPanel(MainFrame mainFrame)
    {
        super(new ImageIcon(imagePath+"background.png").getImage(),mainFrame);
        super.Percentages = new HashMap<>(){{
            put(MainFrame.Dimensions.FULLHD, new Double[]{0.60,0.50});
            put(MainFrame.Dimensions.WIDESCREEN, new Double[]{0.60,0.45});
            put(MainFrame.Dimensions.HD, new Double[]{0.60,0.55});
        }};
        setLayout(new BorderLayout());
        addScalingListener();

        InitializeComponents();
        setVisible(true);
    }

    private void InitializeComponents()
    {

    }


}
