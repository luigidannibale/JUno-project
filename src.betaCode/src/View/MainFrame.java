package View;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private JPanel userPanel;
    private StartingMenuPanel startingPanel;
    private final int frameWidth = 1080;
    private final int frameHeight = 720;
    private final String pathImages = "resources/images/";

    public MainFrame(){
        super("J Uno");
        //setResizable(false);
        //setLayout(new BorderLayout());
        setContentPane(new JLabel(getImageIcon("MainFrame/background.png")));
        setLayout(new GridBagLayout());
        setSize(frameWidth, frameHeight);
        setLocationRelativeTo(null);    //centra il frame
        setDefaultCloseOperation(EXIT_ON_CLOSE); //setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setIconImage(getImageIcon("icon2.png").getImage());
        GridBagConstraints layout = new GridBagConstraints();
        layout.gridx = 1;
        layout.gridy = 1;
        layout.weightx = 0;
        layout.weighty = 0;

        startingPanel = new StartingMenuPanel();
        add(startingPanel,layout);
        InitializeComponents();
        setVisible(true);


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
        JPanel p = new JPanel();
        p.setBackground(Color.orange);
        p.setVisible(true);
        p.setSize(250,250);
        JButton start = new JButton("Start");
        start.addActionListener(e -> View.AudioManager.getInstance().play("resources/audio/rick roll.wav"));
        GridBagConstraints layout = new GridBagConstraints();
        layout.gridx = 1;
        layout.gridy = 2;
        layout.weightx = 0;
        layout.weighty = 0.01;
        p.add(start);
        add(p, layout);
        JPanel p1 = new JPanel();
        p1.setBackground(Color.green);
        p1.setVisible(true);
        p1.setSize(250,250);
        JButton stop = new JButton("Stop");
        stop.addActionListener(e -> View.AudioManager.getInstance().play(null));
        layout.gridx = 1;
        layout.gridy = 0;
        layout.weightx = 0;
        layout.weighty = 0.01;
        p1.add(stop);
        add(p1, layout);
    }
        //Components
        //label = new JLabel("Prova");

        //image = new JLabel(new ImageIcon(pathImages + "White_deck/01.png"));


        //button.setFocusPainted(false);

        //Listener
        /*button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("riuscita");
            }
        });*/
        //

        //Layout
        /*

        layout.gridx = 0;
        layout.gridy = 0;
        layout.weightx = 0;
        layout.weighty = 0.01;
        add(label, layout);



        layout.gridx = 0;
        layout.gridy = 1;
        layout.weightx = 0;
        layout.weighty = 1.0;
        layout.gridwidth = 2;
        layout.gridheight = 1;
        add(image, layout);*/

}
