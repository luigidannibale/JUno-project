package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame {
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

        @Override
        public String toString() {
            int width = (int)getDimension().getWidth();
            int height = (int)getDimension().getHeight();
            return width + "x" + height;
        }
    }

    public enum Panels{
        STARTMENU,
        SETTINGS,
        GAMECHOICE,
        GAME
    }

    private JPanel newGamePanel;
    private SettingsPanel settingsPanel;
    private StartingMenuPanel startingPanel;
    private GameChoicePanel gameChoicePanel;
    private Dimensions currentDimension = Dimensions.WIDESCREEN;

    private final String pathImages = "resources/images/";

    public AudioManager backMusic;
    public AudioManager soundEffects;

    private Config config;
    private Image background;

    public MainFrame()
    {
        super("J Uno");
        backMusic = new AudioManager();
        soundEffects = new AudioManager();

        config = new Config(this);
        config.loadConfig();

        background = getImageIcon("MainFrame/background.png").getImage();
        setContentPane(new JPanel(){
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                int width = (int)currentDimension.getDimension().getWidth();
                int height = (int)currentDimension.getDimension().getHeight();
                g.drawImage(background, 0, 0, width, height, this);
            }
        });

        setLayout(new GridBagLayout());
        setSize(currentDimension.getDimension());
        setPreferredSize(currentDimension.getDimension());
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE); //setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setIconImage(getImageIcon("icon2.png").getImage());

        // devo fare i test non posso perdere tempo a chiudere j dialogs di conferma
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                config.saveConfig();
                /*
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
                */
                System.exit(0);
            }
        });
        InitializeComponents();
    }

    public void setVisibile(){
        super.setVisible(true);

        //backMusic.playMusic("rick roll.wav");
        //soundEffects.playEffect("deck shuffle.wav");
    }

    private ImageIcon getImageIcon(String filename){
        return new ImageIcon(pathImages + filename);
    }

    private void InitializeComponents()
    {
        gameChoicePanel = new GameChoicePanel(this);
        gameChoicePanel.setVisible(false);

        newGamePanel = new GamePanel(this);
        newGamePanel.setVisible(false);

        settingsPanel = new SettingsPanel(this);
        settingsPanel.setVisible(false);

        startingPanel = new StartingMenuPanel(this);

        add(gameChoicePanel);
        add(newGamePanel);
        add(settingsPanel);
        add(startingPanel);
    }

    public void SetVisiblePanel(Panels panel){
        switch (panel){
            case STARTMENU -> startingPanel.setVisible(true);
            case SETTINGS -> settingsPanel.setVisible(true);
            case GAMECHOICE -> gameChoicePanel.setVisible(true);
            case GAME -> newGamePanel.setVisible(true);
        }
    }

    public void setGameScreen(boolean control){
        //setContentPane(control ? newGamePanel : background);
        newGamePanel.setVisible(true);
        setContentPane(newGamePanel);
    }

    public void updateSize(String s){
        Dimensions dim;
        switch (s) {
            case "1920x1080" -> dim = Dimensions.FULLHD;
            case "1080x720" -> dim = Dimensions.HD;
            default -> dim = Dimensions.WIDESCREEN;
        };
        updateSize(dim);
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
