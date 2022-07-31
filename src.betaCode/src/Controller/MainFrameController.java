package Controller;

import View.*;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrameController {
    private final String pathImages = "resources/images/MainFrame/MainframeDesignElements/";
    public enum Panels{
        STARTMENU,
        SETTINGS,
        GAMECHOICE,
        GAME
    }

    MainFrame view;

    public AudioManager backMusic;
    public AudioManager soundEffects;

    Config config;

    StartingMenuController startingMenuController;
    SettingsController settingsController;
    GameChoiceController gameChoiceController;
    GamePanelController gameController;

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
        ProfilePanel pp = new ProfilePanel(this);

        view.addProfilePanel(pp);
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
        switch (panel){
            case STARTMENU -> startingMenuController.setVisible(true);
            case SETTINGS -> settingsController.setVisible(true);
            case GAMECHOICE -> gameChoiceController.setVisible(true);
            case GAME -> {
                gameController.getView().setSize(view.getSize());
                view.add(gameController.getView());
                view.setContentPane(gameController.getView());
                view.pack();
            }
        }
    }

    public void createNewGame(GameChoiceController.GameMode gameMode){
        gameController = new GamePanelController(gameMode);
        setVisiblePanel(MainFrameController.Panels.GAME);
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

    public void closeWindow(){
        try //If the parent has a listener that manages the window closing it links that event, if an exception is generated the frame just get closed
        {
            //((WindowAdapter) ((Arrays.stream(mf.getWindowListeners()).toArray())[0])).windowClosing(null);
            view.dispatchEvent(new WindowEvent(view, WindowEvent.WINDOW_CLOSING));
        }
        catch(Exception ex) {

            view.dispose(); }
    }

    //public MainFrame.Dimensions getCurrentDimension(){return view.getDimension();}
}
