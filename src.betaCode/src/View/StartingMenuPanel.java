package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

public class StartingMenuPanel extends JPanel {
    private JLabel[] icons;
    private JLabel[] options;

    public StartingMenuPanel(){
        setBackground(Color.BLUE);
        setLayout(new GridBagLayout());
        InitializeComponents();
        setVisible(true);
    }

    private void InitializeComponents(){
        //Components
        icons = new JLabel[]{
                new JLabel(new ImageIcon("resources/images/MainFrame/new_gameIcon.png")),
                new JLabel(new ImageIcon("resources/images/MainFrame/settingsIcon.png")),
                new JLabel(new ImageIcon("resources/images/MainFrame/quitIcon.png"))
        };
        options = new JLabel[]{
                new JLabel(new ImageIcon("resources/images/MainFrame/new_game.png")),
                new JLabel(new ImageIcon("resources/images/MainFrame/settings.png")),
                new JLabel(new ImageIcon("resources/images/MainFrame/quit.png"))
        };

        //Listener
        Arrays.stream(options).forEach(op -> op.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                //op.setSize(op.getWidth() + 15, op.getHeight() + 15);
                //System.out.println(op.getWidth() + " " + op.getHeight());
                //aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
                //è da fare con due immagini separate
                Image i = ((ImageIcon) op.getIcon()).getImage();
                i = i.getScaledInstance(op.getWidth() + 15, op.getHeight() + 5, Image.SCALE_SMOOTH);
                op.setIcon(new ImageIcon(i));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //op.setSize(op.getWidth() - 15, op.getHeight() - 15);
                //System.out.println(op.getWidth() + " " + op.getHeight());
                Image i = ((ImageIcon) op.getIcon()).getImage();
                i = i.getScaledInstance(op.getWidth() - 15, op.getHeight() - 5, Image.SCALE_SMOOTH);
                op.setIcon(new ImageIcon(i));
            }
        }));

        //Layout
        GridBagConstraints layout = new GridBagConstraints();
        for (int i = 0 ; i<3;i++)
        {
            layout.gridx = 0;
            layout.gridy = i;
            layout.insets = new Insets(20, 0, 0, 10);
            layout.anchor = GridBagConstraints.LINE_END;
            add(icons[i], layout);
            layout.gridx = 1;
            layout.gridy = i;
            layout.insets = new Insets(20, 0, 0, 10);
            layout.anchor = GridBagConstraints.CENTER;
            add(options[i], layout);
        }
    }
}
