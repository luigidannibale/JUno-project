package Controller;

import Utilities.AudioManager;
import Utilities.Config;
import Utilities.Config2;
import View.*;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.*;

public class MainFrameController {
    private final String pathImages = "resources/images/MainFrame/MainframeDesignElements/";
    public enum Panels{
        STARTMENU,
        SETTINGS,
        GAMECHOICE,
        PROFILE,
        GAME
    }

    private MainFrame view;

    public AudioManager backMusic;
    public AudioManager soundEffects;
    //public DeckColor deckColor = DeckColor.WHITE;
    private Config config;
    private Config2 config2;

    private StartingMenuController startingMenuController;
    private SettingsController settingsController;
    private GameChoiceController gameChoiceController;
    private GamePanelController gameController;
    private ProfilePanelController profileController;

    private Panels currentPanel;

    public MainFrameController(){
        view = new MainFrame();

        backMusic = new AudioManager();
        soundEffects = new AudioManager();

        config = new Config(this);
        config.loadConfig();

        startingMenuController = new StartingMenuController(this);
        settingsController = new SettingsController(this);
        gameChoiceController = new GameChoiceController(this);
        profileController = new ProfilePanelController(this);

        view.addCenteredPanels(startingMenuController.getView(), settingsController.getView(), gameChoiceController.getView(), profileController.getView());
        view.addProfilePanel(profileController.getSmallPanel());

        view.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                if(confirmDispose()) System.exit(0);
            }
        });

        view.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
                    setSettingsReturnPanel();
                    setVisiblePanel(Panels.SETTINGS);
                }
            }
        });

        setVisiblePanel(Panels.STARTMENU);
    }

    private boolean confirmDispose()
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
                new ImageIcon(pathImages + "block.png"),
                options, options[1]);
        return (confirm == JOptionPane.YES_OPTION);
    }

    public void setVisible(){
        view.setVisible(true);
    }

    public void setVisiblePanel(MainFrameController.Panels panel){
        if (currentPanel != null) switch (currentPanel){
            case STARTMENU -> startingMenuController.setVisible(false);
            case SETTINGS -> settingsController.setVisible(false);
            case PROFILE -> profileController.getView().setVisible(false);
            case GAMECHOICE -> gameChoiceController.setVisible(false);
        }

        CardLayout c1 = (CardLayout) view.getContentPane().getLayout();
        switch (panel){
            case STARTMENU -> {
                startingMenuController.setVisible(true);
                c1.show(view.getContentPane(), MainFrame.Cards.MAIN.name());
            }
            case SETTINGS -> {
                settingsController.setVisible(true);
                c1.show(view.getContentPane(), MainFrame.Cards.SETTINGS.name());
            }
            case GAMECHOICE -> {
                gameChoiceController.setVisible(true);
                c1.show(view.getContentPane(), MainFrame.Cards.MAIN.name());
            }
            case PROFILE -> {
                profileController.getView().setVisible(true);
                c1.show(view.getContentPane(), MainFrame.Cards.MAIN.name());
            }
            case GAME -> {
                view.dispose();
                view.setUndecorated(true);
                view.pack();
                //GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
                //GraphicsDevice ev = env.getDefaultScreenDevice();
                //ev.setFullScreenWindow(view);
                view.setExtendedState(JFrame.MAXIMIZED_BOTH);
                view.getContentPane().repaint();
                view.getGameBackground().add(gameController.getView(), BorderLayout.CENTER);
                c1.show(view.getContentPane(), MainFrame.Cards.GAME.name());
                view.setVisible(true);
            }
        }
        currentPanel = panel;
    }

    public void setSettingsReturnPanel(){
        if (currentPanel != Panels.SETTINGS) settingsController.setReturnPanel(currentPanel);
    }

    public void createNewGame(GameChoiceController.GameMode gameMode){
        gameController = new GamePanelController(gameMode);
        setVisiblePanel(MainFrameController.Panels.GAME);
    }

    public void quitGame(){
        gameController.quitGame();
        view.getGameBackground().remove(gameController.getView());
        view.dispose();
        view.setUndecorated(false);
        view.pack();
        view.getContentPane().repaint();
        view.setSize(view.getDimension());
        view.setLocationRelativeTo(null);
        view.setVisible(true);
        //view.setDimension(view.getDimension());
        setVisiblePanel(Panels.STARTMENU);
    }

    public void closeWindow(){
        try //If the parent has a listener that manages the window closing it links that event, if an exception is generated the frame just get closed
        {
            view.dispatchEvent(new WindowEvent(view, WindowEvent.WINDOW_CLOSING));
        }
        catch(Exception ex)
        {
            view.dispose();
        }
    }

    public Config getConfig() {
        return config;
    }

    public void resumeGame(){
        gameController.getView().createCards();
    }

    /*public void updateSize(String s){
        MainFrame.Dimensions dim;
        switch (s) {
            case "1920x1080" -> dim = MainFrame.Dimensions.FULLHD;
            case "1080x720" -> dim = MainFrame.Dimensions.HD;
            default -> dim = MainFrame.Dimensions.WIDESCREEN;
        }
        updateSize(dim);
    }

    public void updateSize(MainFrame.Dimensions dimension){
        view.setCurrentDimension(dimension);
        view.setSize(dimension.getDimension());
        view.setLocationRelativeTo(null);
    }*/
}
