package View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.function.BiFunction;

public class StartingMenuPanel extends JPanel {
    private final String imagePath = "resources/images/MainFrame/StartingMenuPanel/";
    private ImageComponent[] icons;
    private final Image background;

    int panelWidth = 380;
    int panelHeight = 480;

    public StartingMenuPanel(JPanel[] panels,MainFrame f){
        setLayout(new BorderLayout());
        background = new ImageIcon(imagePath+"background.png").getImage();

        InitializeComponents(panels,f);
        setVisible(true);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                BiFunction<Double, Double, Integer> percentage = (num, p) -> ((int) (num * p));
                MainFrame.Dimensions parentSize = f.getDimension();
                double widthScalingPercentage, heightScalingPercentage;
                ImageComponent.Size toSize;

                switch (parentSize){
                    case FULLHD     -> {widthScalingPercentage = 0.25; heightScalingPercentage = 0.50; toSize = ImageComponent.Size.BIG;}
                    case WIDESCREEN -> {widthScalingPercentage = 0.27; heightScalingPercentage = 0.45; toSize = ImageComponent.Size.MEDIUM;}
                    case HD         -> {widthScalingPercentage = 0.33; heightScalingPercentage = 0.55; toSize = ImageComponent.Size.SMALL;}
                    default         -> {widthScalingPercentage = 0; heightScalingPercentage = 0; toSize = ImageComponent.Size.SMALL;}
                }
                updateSize(percentage.apply(parentSize.getDimension().getWidth(), widthScalingPercentage), percentage.apply(parentSize.getDimension().getHeight(), heightScalingPercentage),toSize);
            }
        });
    }

    private void InitializeComponents(JPanel[] panels, JFrame f)
    {
        icons = new ImageComponent[]{
                new ImageComponent(imagePath + "Startgame", ImageComponent.Size.BIG),
                new ImageComponent(imagePath + "Settings", ImageComponent.Size.BIG),
                new ImageComponent(imagePath + "Quit", ImageComponent.Size.BIG)
        };

        var current = this;
        icons[0].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                changePanel(current, panels[0]);
            }
        });
        icons[1].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                changePanel(current, panels[1]);
            }
        });
        icons[2].addMouseListener(new MouseAdapter() { // Quit button
            @Override
            public void mouseClicked(MouseEvent e) {

                try //If the parent has a listener that manages the window closing it links that event, if an exception is generated the frame just get closed
                {
                    ((WindowAdapter) ((Arrays.stream(f.getWindowListeners()).toArray())[0])).windowClosing(null);
                }
                catch(Exception ex) { f.dispose(); }
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

    private void changePanel(JPanel current, JPanel p){
        if (p != null && p.isValid())
        {
            p.setVisible(true);
            current.setVisible(false);
        }
    }

    public void updateSize(int width, int heigth, ImageComponent.Size s){
        panelHeight = heigth;
        panelWidth = width;
        setSize(panelWidth,panelHeight);
        int offset = panelHeight*6/100;
        setBorder(new EmptyBorder(offset,offset,offset,0));
        Arrays.stream(icons).forEach(icon -> icon.setIcon(s));
    }


    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, panelWidth, panelHeight, null);
    }

    @Override
    public Dimension getPreferredSize() { return (new Dimension(panelWidth, panelHeight)); }




}
