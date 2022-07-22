package Controller;

import View.AudioManager;
import View.Config;
import View.GamePanel;
import View.MainFrame;

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

        startingMenuController = new StartingMenuController(view, this);

        settingsController = new SettingsController(view);
        settingsController.setVisible(false);

        gameChoiceController = new GameChoiceController(view);
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
                view.add(gameController.getView());
                view.setContentPane(gameController.getView());
            }
        }
    }

    public void createNewGame(GameChoiceController.GameMode gameMode){
        GamePanelController game = new GamePanelController(gameMode);
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

    public MainFrame.Dimensions getCurrentDimension(){return view.getCurrentDimension();}
}
