package View;
import Utilities.Config;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ResizablePanel extends JPanel {
    protected int panelWidth, panelHeight;
    protected String imagePath;

    public ResizablePanel(int panelWidth,int panelHeight, int borderOffset)
    {
        this.panelWidth = (int) (panelWidth * Config.scalingPercentage);
        this.panelHeight = (int) (panelHeight * Config.scalingPercentage);
        setSize(this.panelWidth, this.panelHeight);

        int borderPX = this.panelHeight*borderOffset/100;
        setBorder(new EmptyBorder(borderPX,borderPX,borderPX,borderPX));
    }

    @Override
    public Dimension getPreferredSize() { return (new Dimension(panelWidth, panelHeight)); }
}

    /*

        //protected Map<MainFrame.Dimensions,Double[]> Percentages;
    protected double percentX;
    protected double percentY;
    // volendo si puo eliminare il metodo
    void addScalingListener()
    {
        addComponentListener(new ComponentAdapter() {

            @Override
            public void componentMoved(ComponentEvent e) {
                MainFrame.Dimensions parentSize = mainFrame.getCurrentDimension();
                ImageComponent.Size toSize;

                switch (parentSize){
                    case FULLHD     -> toSize = ImageComponent.Size.BIG;
                    case WIDESCREEN -> toSize = ImageComponent.Size.MEDIUM;
                    default         -> toSize = ImageComponent.Size.SMALL;
                }


                //updateSize(new Dimension(percentage.apply(parentSize.getDimension().getWidth(), Percentages.get(parentSize)[0]),
                //                         percentage.apply(parentSize.getDimension().getHeight(), Percentages.get(parentSize)[1])),
                //                         toSize);



                updateSize(new Dimension(percentage.apply(parentSize.getDimension().getWidth(), percentX),
                                         percentage.apply(parentSize.getDimension().getHeight(), percentY)),
                                         toSize);


            }
        });
    }
    */

    /*
    public void updateSize(Dimension dimension, ImageComponent.Size size)
    {
        panelHeight = (int) dimension.getHeight();
        panelWidth = (int) dimension.getWidth();
        setSize(dimension);

        int offsetpx = panelHeight*offset/100;
        setBorder(new EmptyBorder(offsetpx,offsetpx,offsetpx,offsetpx));
        if (icons != null) Arrays.stream(icons).forEach(icon -> icon.setIcon(size));
    }*/




