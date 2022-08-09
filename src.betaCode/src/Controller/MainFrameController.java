package Controller;

import Utilities.AudioManager;
import Utilities.Config;
import View.*;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.TimeUnit;

public class MainFrameController {
    private final String pathImages = "resources/images/MainFrame/MainframeDesignElements/";
    public enum Panels{
        STARTMENU,
        SETTINGS,
        GAMECHOICE,
        GAME
    }

    private MainFrame view;

    public AudioManager backMusic;
    public AudioManager soundEffects;
    public boolean whiteDeckOn = true;

    private Config config;

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
        settingsController.setVisible(false);

        gameChoiceController = new GameChoiceController(this);
        gameChoiceController.setVisible(false);

        //gameController = new GamePanelController(view);
/*
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.gridheight = 2;

        view.add(startingMenuController.getView(), gbc);
        view.add(settingsController.getView(), gbc);
        view.add(gameChoiceController.getView(), gbc);
 */
        view.addCenteredPanels(startingMenuController.getView(), settingsController.getView(), gameChoiceController.getView());

        view.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                if(confirmDispose())
                {
                    config.saveConfig();
                    System.exit(0);
                }

            }
        });

        ///debug da cancellare
        view.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                //System.out.println(e.getComponent());
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
                    //System.exit(42);
                    setSettingsReturnPanel();
                    setVisiblePanel(Panels.SETTINGS);
                }
            }
        });

        profileController = new ProfilePanelController(this);
        view.addProfilePanel(profileController.getSmallPanel());

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
