package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

public class MainFrame extends JFrame implements Observer {
    public enum Dimensions{
        FULLHD(new Dimension(1920,1080)),
        WIDESCREEN(new Dimension(1440,920)),
        HD(new Dimension(1080,720));

        private final Dimension dimension;

        Dimensions(Dimension dimension){
            this.dimension = dimension;
        }

        public Dimension getDimension() {
            return dimension;
        }
    }

    private JPanel newGamePanel;
    private SettingsPanel settingsPanel;
    private StartingMenuPanel startingPanel;
    private Dimensions currentDimension = Dimensions.WIDESCREEN;

    private final String pathImages = "resources/images/";
    private Image i = getImageIcon("MainFrame/background.png").getImage();

    public MainFrame()
    {
        super("J Uno");

        ImageBackground background = new ImageBackground(getImageIcon("MainFrame/background.png").getImage());
        background.setSize(currentDimension.getDimension());
        setContentPane(background);

        setLayout(new GridBagLayout());
        setSize(currentDimension.getDimension());
        setPreferredSize(currentDimension.getDimension());
        setResizable(false);
        setLocationRelativeTo(null);
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
        JPanel p = new JPanel();
        Button b2 = new Button("2");
        b2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                updateSize(Dimensions.HD);
            }
        });
        Button b3 = new Button("3");
        b3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                updateSize(Dimensions.WIDESCREEN);
            }
        });
        Button b4 = new Button("4");
        b4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                updateSize(Dimensions.FULLHD);
            }
        });
        //p.add(new JLabel("Per dimensionare il frame"));

        p.add(b2);
        p.add(b3);
        p.add(b4);
        p.setBackground(Color.PINK);
        add(p);


        newGamePanel = new JPanel();
        newGamePanel.setMinimumSize(new Dimension(500,100));
        newGamePanel.setPreferredSize(new Dimension(500,100));
        newGamePanel.setMaximumSize(new Dimension(500,100));
        newGamePanel.setBackground(Color.CYAN);
        newGamePanel.add(new JLabel("new game panel goes here"));
        newGamePanel.setVisible(false);

        settingsPanel = new SettingsPanel(this);
        // settingsPanel.setMinimumSize(new Dimension(500,100));
        //settingsPanel.setPreferredSize(new Dimension(500,100));
        //settingsPanel.setMinimumSize(new Dimension(500,100));
        //settingsPanel.setBackground(Color.green);
        //settingsPanel.add(new JLabel("settings panel goes here"));
        settingsPanel.setVisible(false);

        startingPanel = new StartingMenuPanel(new JPanel[]{newGamePanel,settingsPanel,startingPanel},this);

        add(newGamePanel);
        add(settingsPanel);
        add(startingPanel);

    }

    @Override
    public void update(Observable model, Object o)
    {

    }
    public void updateSize(Dimensions dimension){
        currentDimension = dimension;
        setSize(dimension.getDimension());
        setLocationRelativeTo(null);
    }

    public Dimensions getDimension(){
        return currentDimension;
    }

}
