package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

public class StartingMenuPanel extends JPanel {
    private String ImagesPath = "resources/images/MainFrame/panel/";
    private ImageComponent[] icons;
    private JLabel[] options;
    private Image background;

    public StartingMenuPanel(){
        //setLayout(new GridBagLayout());
        //InitializeComponents();



        background = new ImageIcon("resources/images/MainFrame/panel/background.png").getImage();
        setSize(500,600);
        setVisible(true);
    }

    private void InitializeComponents(){
        //Components
        icons = new ImageComponent[]{
                new ImageComponent("Startgame", ImageComponent.Size.SMALL),
                new ImageComponent("Settings", ImageComponent.Size.SMALL),
                new ImageComponent("Quit", ImageComponent.Size.SMALL)
        };


        GridBagConstraints layout = new GridBagConstraints();
        for (int i = 0 ; i<3;i++)
        {
            layout.gridx = 0;
            layout.gridy = i;
            layout.insets = new Insets(20, 0, 0, 10);
            layout.anchor = GridBagConstraints.LINE_START;
            add(icons[i], layout);
        }

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

        //Layout

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, background.getWidth(null), background.getHeight(null), null);
    }

    @Override
    public Dimension getPreferredSize()
    {
        return (new Dimension(background.getWidth(null), background.getHeight(null)));
    }

}
