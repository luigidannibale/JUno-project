package View;

import javax.swing.*;
import java.awt.*;

public class StartingMenuPanel extends JPanel {
    private JLabel[] icons;
    private JLabel[] options;

    public StartingMenuPanel(){
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
        setBackground(Color.BLUE);
        setLayout(new GridBagLayout());
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
        setVisible(true);
    }
}
