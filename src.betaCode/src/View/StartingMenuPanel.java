package View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class StartingMenuPanel extends JPanel {
    private String ImagesPath = "resources/images/MainFrame/panel/";
    private ImageComponent[] icons;
    private JLabel[] options;
    private Image background;

    int panelWidth = 380;
    int panelHeight = 480;

    public <T extends JPanel> StartingMenuPanel(T[] panels,JFrame f){
        setLayout(new BorderLayout());
        setSize(panelWidth,panelHeight);
        background = new ImageIcon("resources/images/MainFrame/panel/background.png").getImage();

        InitializeComponents(panels,f);
        setVisible(true);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                double compWidth = e.getComponent().getParent().getSize().getWidth(), compHeight = e.getComponent().getParent().getSize().getHeight();
                /*
                    1920x1080 --> 480x380  (450x110)
                    1440x920  --> 430x320  (400x 95)
                    1080x720  --> 350x300  (320x 85)
                    720x560   --> 320x280  (290x 80)
                */
                if (compWidth > 1800 && compHeight > 900)
                    updateSize(440,480,ImageComponent.Size.BIG);
                else if (compWidth > 1400 && compHeight >850)
                    updateSize(360,420, ImageComponent.Size.MEDIUM);
                else if (compWidth < 850 && compHeight < 600)
                    updateSize(300,300,ImageComponent.Size.SMALL);
                else
                    updateSize(320,360,ImageComponent.Size.MEDIUM);
            }
        });


    }

    private <T extends JPanel> void InitializeComponents(T[] panels, JFrame f)
    {
        icons = new ImageComponent[]{
                new ImageComponent("Startgame", ImageComponent.Size.BIG),
                new ImageComponent("Settings", ImageComponent.Size.BIG),
                new ImageComponent("Quit", ImageComponent.Size.BIG)
        };

        icons[0].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                changePanel(panels[0]);
            }
        });
        icons[1].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                changePanel(panels[1]);
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

        //icons[0].setBorder(new EmptyBorder(25, 0, 0, 0));
        //icons[icons.length-1].setBorder(new EmptyBorder(0, 0, 25, 0));
        add(icons[0], BorderLayout.NORTH);
        add(icons[1], BorderLayout.CENTER);
        add(icons[2], BorderLayout.SOUTH);

        //debug
        /*
        Arrays.stream(icons).forEach(icon -> {
            icon.setBorder(new LineBorder(Color.black));
        } );
        */

        Arrays.stream(icons).forEach(op -> op.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { op.scaleUpIcon(); }
            @Override
            public void mouseExited(MouseEvent e)  { op.resetIcon(); }
        }));
    }

    private void changePanel(JPanel p){
        if (p != null && p.isValid())
        {
            p.setVisible(true);
            this.setVisible(false);
        }
    }

    public void updateSize(int width, int heigth, ImageComponent.Size s){
        panelHeight = heigth;
        panelWidth = width;
        setSize(panelWidth,panelHeight);
        int offset = panelHeight*6/100;
        setBorder(new EmptyBorder(offset,0,offset,0));
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
