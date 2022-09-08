package View.Pages;
import Controller.Utilities.Config;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Arrays;

public class ResizablePanel extends JPanel
{
    protected int panelWidth, panelHeight;

    /**
     * Initialize the panel with the given width, height and border
     * @param panelWidth the width of the panel
     * @param panelHeight the height of the panel
     * @param borderOffset the width of the empty border
     */
    public ResizablePanel(int panelWidth,int panelHeight, int borderOffset)
    {
        this.panelWidth = (int) (panelWidth * Config.scalingPercentage);
        this.panelHeight = (int) (panelHeight * Config.scalingPercentage);
        setSize(this.panelWidth, this.panelHeight);

        int borderPX = this.panelHeight*borderOffset/100;
        setBorder(new EmptyBorder(borderPX,borderPX,borderPX,borderPX));
    }

    /**
     * Resize every component added to the panel based on the scaling percentage of the screen.
     */
    protected void resizeComponents()
    {
        Arrays.stream(getComponents()).forEach(component ->
        {
            //if(component.getPreferredSize().height == 0) System.out.println(component);
            component.setPreferredSize(new Dimension((int) (component.getPreferredSize().width * Config.scalingPercentage), (int) (component.getPreferredSize().height * Config.scalingPercentage)));
            component.repaint();
        });
    }

    @Override
    public Dimension getPreferredSize() { return (new Dimension(panelWidth, panelHeight)); }
}




