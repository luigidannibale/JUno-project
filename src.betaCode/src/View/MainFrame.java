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
        //setContentPane(new JLabel(getImageIcon("MainFrame/background.png")));
        setContentPane(new BasicBackgroundPanel(getImageIcon("MainFrame/background.png").getImage()));
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

        /* devo fare i test non posso perdere tempo a chiudere jdialogs di conferma
        addWindowListener(new WindowAdapter()
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
        //Components
        startingPanel = new StartingMenuPanel();

        JButton start = new JButton("Start");

        JPanel p = new JPanel();
        p.setBackground(Color.orange);
        p.setVisible(true);
        p.setSize(250,250);
        p.add(start);

        JButton stop = new JButton("Stop");

        JPanel p1 = new JPanel();
        p1.setBackground(Color.green);
        p1.setVisible(true);
        p1.setSize(250,250);
        p1.add(stop);

        //Listener
        start.addActionListener(e -> View.AudioManager.getInstance().play("resources/audio/rick roll.wav"));
        stop.addActionListener(e -> View.AudioManager.getInstance().play(null));

        //Layout
        GridBagConstraints layout = new GridBagConstraints();
        layout.gridx = 1;
        layout.gridy = 1;
        layout.weightx = 0;
        layout.weighty = 0;
        add(startingPanel,layout);

        layout.gridx = 1;
        layout.gridy = 2;
        layout.weightx = 0;
        layout.weighty = 0.01;
        add(p, layout);

        layout.gridx = 1;
        layout.gridy = 0;
        layout.weightx = 0;
        layout.weighty = 0.01;
        add(p1, layout);
    }

    public class BasicBackgroundPanel extends JPanel
    {
        private final Image background;

        public BasicBackgroundPanel(Image background)
        {
            this.background = background;
            setLayout( new BorderLayout() );
        }

        @Override
        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            Graphics2D gd = (Graphics2D)g.create();
            gd.drawImage(background, 0, 0, Math.max(getWidth(), 800), Math.max(getHeight(), 600), null);
            gd.dispose();
        }

        @Override
        public Dimension getPreferredSize()
        {
            return new Dimension(background.getWidth(this), background.getHeight(this));
        }
    }
}
