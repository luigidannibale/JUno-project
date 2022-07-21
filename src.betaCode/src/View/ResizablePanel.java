package View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Arrays;
import java.util.Map;
import java.util.function.BiFunction;

public class ResizablePanel extends JPanel {
    //protected Image background;
    protected MainFrame mainFrame;
    protected int panelWidth, panelHeight;
    //protected Map<MainFrame.Dimensions,Double[]> Percentages;
    protected double percentX;
    protected double percentY;

    protected int offset;
    protected ImageComponent[] icons;

    public static final BiFunction<Double, Double, Integer> percentage = (num, p) -> ((int) (num * p));

    //public ResizablePanel(Image background,MainFrame mainFrame)
    public ResizablePanel(MainFrame mainFrame)
    {
        //this.background = background;
        this.mainFrame = mainFrame;
        panelWidth = 10;
        panelHeight = 10;
    }

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

                /*
                updateSize(new Dimension(percentage.apply(parentSize.getDimension().getWidth(), Percentages.get(parentSize)[0]),
                                         percentage.apply(parentSize.getDimension().getHeight(), Percentages.get(parentSize)[1])),
                                         toSize);

                 */

                updateSize(new Dimension(percentage.apply(parentSize.getDimension().getWidth(), percentX),
                                         percentage.apply(parentSize.getDimension().getHeight(), percentY)),
                                         toSize);


            }
        });
    }

    public void updateSize(Dimension dimension, ImageComponent.Size size)
    {
        panelHeight = (int) dimension.getHeight();
        panelWidth = (int) dimension.getWidth();
        setSize(dimension);
        
        int offsetpx = panelHeight*offset/100;
        setBorder(new EmptyBorder(offsetpx,offsetpx,offsetpx,offsetpx));
        if (icons != null) Arrays.stream(icons).forEach(icon -> icon.setIcon(size));
    }

    @Override
    public Dimension getPreferredSize() { return (new Dimension(panelWidth, panelHeight)); }

}
