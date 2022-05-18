package View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.util.Arrays;

public class StartingMenuPanel extends JPanel {
    private String ImagesPath = "resources/images/MainFrame/panel/";
    private ImageComponent[] icons;
    private JLabel[] options;
    private Image background;

    int panelWidth = 350;
    int panelHeight = 350;

    public <T extends JPanel> StartingMenuPanel(T[] panels,JFrame f){
        //setLayout(new GridBagLayout());
        //setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setLayout(new BorderLayout());
        //setLayout(null);
        setSize(panelWidth,panelHeight);

        background = new ImageIcon("resources/images/MainFrame/panel/background.png").getImage();

        InitializeComponents(panels,f);
        setVisible(true);
    }

    private <T extends JPanel> void InitializeComponents(T[] panels,JFrame f){


        icons = new ImageComponent[]{
                new ImageComponent("Startgame", ImageComponent.Size.SMALL),
                new ImageComponent("Settings", ImageComponent.Size.SMALL),
                new ImageComponent("Quit", ImageComponent.Size.SMALL)
        };


        icons[0].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (panels[0] != null && panels[0].isValid())
                {
                    panels[0].setVisible(true);
                    setVisible(false);
                }
            }
        });
        icons[1].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (panels[1] != null && panels[1].isValid())
                {
                    panels[1].setVisible(true);
                    setVisible(false);
                }
            }
        });
        icons[2].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try
                {
                    ((WindowAdapter) ((Arrays.stream(f.getWindowListeners()).toArray())[0])).windowClosing(null);
                }
                catch(Exception ex)
                {
                    f.dispose();
                }
            }
        });

        icons[0].setBorder(new EmptyBorder(25, 0, 0, 0));
        icons[icons.length-1].setBorder(new EmptyBorder(0, 0, 25, 0));
        add(icons[0], BorderLayout.NORTH);
        add(icons[1], BorderLayout.CENTER);
        add(icons[2], BorderLayout.SOUTH);

        //Listener
        Arrays.stream(icons).forEach(op -> op.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {

                op.setIcon(ImageComponent.Size.MEDIUM);
            }
            @Override
            public void mouseExited(MouseEvent e) {

                op.setIcon(ImageComponent.Size.SMALL);
            }

        }));

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, panelWidth, panelHeight, null);
    }

    @Override
    public Dimension getPreferredSize()
    {
        return (new Dimension(panelWidth, panelHeight));
    }

}
