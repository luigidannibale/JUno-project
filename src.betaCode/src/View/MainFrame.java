package View;

import Controller.GameChoiceController;
import Controller.GamePanelController;
import Controller.SettingsController;
import Controller.StartingMenuController;

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

    private GamePanel newGamePanel;
    //private SettingsPanel settingsPanel;
    private SettingsController settingsController;
    //private StartingMenuPanel startingPanel;
    private StartingMenuController startingMenuController;
    private GameChoicePanel gameChoicePanel;
    private Dimensions currentDimension = Dimensions.WIDESCREEN;

    private final String pathImages = "resources/images/";

    public AudioManager backMusic;
    public AudioManager soundEffects;

    private Image background;

    public MainFrame()
    {
        super("J Uno");

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

        InitializeComponents();
    }



    private ImageIcon getImageIcon(String filename){
        return new ImageIcon(pathImages + filename);
    }

    private void InitializeComponents()
    {
        //gameChoicePanel = new GameChoicePanel(this);
        //gameChoicePanel.setVisible(false);

        //newGamePanel = new GamePanel(this);
        //newGamePanel.setVisible(false);

        //settingsPanel = new SettingsPanel(this);
        //settingsPanel.setVisible(false);

        //startingPanel = new StartingMenuPanel(this);
        //startingMenuController = new StartingMenuController(this);

        //add(gameChoicePanel);
        //add(newGamePanel);
        //add(settingsPanel);
        //add(startingPanel);
        //add(startingMenuController.getView());
    }

    public void createNewGame(GameChoiceController.GameMode gameMode){
        GamePanelController game = new GamePanelController(gameMode);
        setVisiblePanel(Panels.GAME);
    }

    public void setVisiblePanel(Panels panel){
        switch (panel){
            case STARTMENU -> startingMenuController.setVisible(true);
            case SETTINGS -> settingsController.setVisible(true);
            case GAMECHOICE -> gameChoicePanel.setVisible(true);
            case GAME -> setContentPane(newGamePanel);
        }
    }

    //da cancellare
    public void updateSize(Dimensions dimension){
        currentDimension = dimension;
        setSize(dimension.getDimension());
        setLocationRelativeTo(null);
    }

    public Dimensions getCurrentDimension(){
        return currentDimension;
    }

    public void setCurrentDimension(Dimensions dimensions){
        currentDimension = dimensions;
    }


}
