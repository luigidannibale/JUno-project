package View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
        //setLayout(new BorderLayout());
        addScalingListener();
        InitializeComponents();
        setVisible(true);
    }

    private void InitializeComponents()
    {
        var label = new JLabel("go back");
        label.setFont(new Font("Sans Serif", Font.PLAIN, 20));
        label.setForeground(Color.white);
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                changePanel(mainFrame.getStartingPanel());
            }
        });

        /*
        //var slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        JSlider slider  = new JSlider(JSlider.HORIZONTAL, 0, 100, 50){
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(getBackground());
                g2d.setComposite(AlphaComposite.SrcOver.derive(0.5f));
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.dispose();

                super.paintComponent(g);
            }
        };
        slider.setSize(new Dimension(120, 20));
        slider.setMajorTickSpacing(10);
        slider.setMinorTickSpacing(1);
        //slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setOpaque(false);
        //slider.setBackground(Color.BLUE);

        */
        var slider = new JProgressBar();
        //slider.setSize(new Dimension(200, 20));
        slider.setValue(50);
        slider.setPreferredSize(new Dimension(200, 20));
        //slider.setMinimumSize(new Dimension(200, 20));
        slider.setStringPainted(true);
        slider.setFont(new Font("MV Boli", Font.PLAIN, 14));
        slider.setForeground(Color.blue);
        slider.setBackground(Color.WHITE);
        slider.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                var x = e.getX();
                var max = slider.getWidth();
                var perc = 100 * x / max;
                perc = perc < 0 ? 0 : Math.min(perc, 100);
                slider.setValue(perc);
                System.out.println("x:" + "100=" + x + ":" + max);
                System.out.println("x = " + perc);
            }

            /*
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println(e.getX());
            }*/
        });

        add(label);
        add(slider);
    }


}
