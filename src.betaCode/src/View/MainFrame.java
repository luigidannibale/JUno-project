package View;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private JPanel newGamePanel = new JPanel();
    private JPanel settingsPanel = new JPanel();
    private StartingMenuPanel startingPanel;
    private final int frameWidth = 1080;
    private final int frameHeight = 720;
    private final String pathImages = "resources/images/";

    public MainFrame(){
        super("J Uno");
        ImageBackground background = new ImageBackground(getImageIcon("MainFrame/background.png").getImage());
        setResizable(true);
        background.setSize(frameWidth, frameHeight);
        setContentPane(background);
        //setLayout(null);

        setLayout(new GridBagLayout());
        setSize(frameWidth, frameHeight);
        setLocationRelativeTo(null);    //centra il frame
        setDefaultCloseOperation(EXIT_ON_CLOSE); //setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setIconImage(getImageIcon("icon2.png").getImage());



        InitializeComponents();
        setVisible(true);

        // devo fare i test non posso perdere tempo a chiudere j dialogs di conferma
        /*addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                UIManager.put("OptionPane.background", new ColorUIResource(255,255,255));
                UIManager.put("Panel.background", new ColorUIResource(255,255,255));
                String[] options = new String[]{"Yes", "No"};
                int confirm = JOptionPane.showOptionDialog(
                        null,
                        "Do you REALLY want to exit?",
                        "Probably you just miss-clicked",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        getImageIcon("block.png"),
                        options, options[1]);
                if (confirm == JOptionPane.YES_OPTION) System.exit(0);
            }
        });*/
    }

    private ImageIcon getImageIcon(String filename){
        return new ImageIcon(pathImages + filename);
    }

    private void InitializeComponents()
    {
        newGamePanel.setMinimumSize(new Dimension(500,100));
        newGamePanel.setPreferredSize(new Dimension(500,100));
        newGamePanel.setMaximumSize(new Dimension(500,100));
        newGamePanel.setBackground(Color.CYAN);
        newGamePanel.add(new JLabel("new game panel goes here"));
        newGamePanel.setVisible(false);

        settingsPanel.setMinimumSize(new Dimension(500,100));
        settingsPanel.setPreferredSize(new Dimension(500,100));
        settingsPanel.setMinimumSize(new Dimension(500,100));
        settingsPanel.setBackground(Color.green);
        settingsPanel.add(new JLabel("settings panel goes here"));
        settingsPanel.setVisible(false);

        startingPanel = new StartingMenuPanel(new JPanel[]{newGamePanel,settingsPanel},this);
        add(newGamePanel);
        add(settingsPanel);
        add(startingPanel);
    }

}
