package Controller;

import View.AudioManager;
import View.Config;
import View.GamePanel;
import View.MainFrame;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrameController {

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

        view.add(startingMenuController.getView());
        view.add(settingsController.getView());
        view.add(gameChoiceController.getView());

        view.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {

                config.saveConfig();
                System.exit(0);
            }
        });
    }
    private void confirmDispose()
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
    public void setVisible(){
        view.setVisible(true);
    }

    public void setVisiblePanel(MainFrame.Panels panel){
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
        setVisiblePanel(MainFrame.Panels.GAME);
    }

    public void updateSize(String s){
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
    }

    public void closeWindow(){
        try //If the parent has a listener that manages the window closing it links that event, if an exception is generated the frame just get closed
        {
            //((WindowAdapter) ((Arrays.stream(mf.getWindowListeners()).toArray())[0])).windowClosing(null);
            view.dispatchEvent(new WindowEvent(view, WindowEvent.WINDOW_CLOSING));
        }
        catch(Exception ex) { view.dispose(); }
    }

    public MainFrame.Dimensions getCurrentDimension(){return view.getCurrentDimension();}
}
