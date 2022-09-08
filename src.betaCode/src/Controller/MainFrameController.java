package Controller;

import Model.Player.AIPlayer;
import Controller.Utilities.AudioManager;
import Controller.Utilities.Config;
import Controller.Utilities.DataAccessManager;
import View.Elements.CircularImage;
import View.Elements.ViewPlayer;
import View.Pages.MainFrame;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrameController extends Controller<MainFrame>
{
    private static MainFrameController instance = null;

    private final String imagesPath = "resources/images/MainFrame/MainframeDesignElements/";
    public enum Panels
    {
        STARTMENU,
        SETTINGS,
        GAMECHOICE,
        PROFILE,
        GAME
    }
    private StartingMenuController startingMenuController;
    private SettingsController settingsController;
    private GameChoiceController gameChoiceController;
    private GamePanelController gameController;
    private ProfilePanelController profileController;
    private ViewPlayer viewPlayer;
    private Panels currentPanel;

    public static MainFrameController getInstance(){
        if (instance == null){
            instance = new MainFrameController();
            instance.initilizeMainFrameController();
        }
        return instance;
    }

    private MainFrameController()
    { super(new MainFrame()); }

    private void initilizeMainFrameController()
    {
        DataAccessManager DAM = new DataAccessManager();
        DAM.loadInitOrDefault();
        //Config.scalingPercentage = 1;

        viewPlayer = new ViewPlayer(Config.loggedPlayer, new CircularImage(Config.savedIconPath));

        startingMenuController = new StartingMenuController();
        settingsController = new SettingsController();
        gameChoiceController = new GameChoiceController();
        profileController = new ProfilePanelController();

        view.addCenteredPanels(startingMenuController.getView(), settingsController.getView(), gameChoiceController.getView(), profileController.getView());
        view.addProfilePanel(profileController.getSmallPanel());

        view.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                if(confirmDispose())
                {
                    DAM.saveProfile(Config.loggedPlayer);
                    view.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    System.exit(0);
                }
            }
        });

        view.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
                {
                    setSettingsReturnPanel();
                    setVisiblePanel(Panels.SETTINGS);
                }
            }
        });

        setVisiblePanel(Panels.STARTMENU);

        AudioManager.getInstance().setAudio(AudioManager.Musics.CALM_BACKGROUND);
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
                new ImageIcon(imagesPath + "block.png"),
                options, options[1]);
        return (confirm == JOptionPane.YES_OPTION);
    }

    public void setVisiblePanel(MainFrameController.Panels panel)
    {
        if (currentPanel != null) switch (currentPanel)
        {
            case STARTMENU -> startingMenuController.setVisible(false);
            case SETTINGS -> settingsController.setVisible(false);
            case PROFILE -> profileController.setVisible(false);
            case GAMECHOICE -> gameChoiceController.setVisible(false);
            case GAME -> gameController.setVisible(false);
        }

        CardLayout c1 = (CardLayout) view.getContentPane().getLayout();
        switch (panel){
            case STARTMENU -> {
                startingMenuController.setVisible(true);
                c1.show(view.getContentPane(), MainFrame.Cards.MAIN.name());
            }
            case SETTINGS -> {
                settingsController.refreshSettings();
                settingsController.setVisible(true);
                c1.show(view.getContentPane(), MainFrame.Cards.SETTINGS.name());
            }
            case GAMECHOICE -> {
                gameChoiceController.setVisible(true);
                c1.show(view.getContentPane(), MainFrame.Cards.MAIN.name());
            }
            case PROFILE -> {
                profileController.setReturnPanel(currentPanel);
                profileController.setVisible(true);
                c1.show(view.getContentPane(), MainFrame.Cards.MAIN.name());
            }
            case GAME -> {
                view.dispose();
                view.setUndecorated(true);
                view.pack();
                view.setExtendedState(JFrame.MAXIMIZED_BOTH);
                view.getContentPane().repaint();
                view.getGameBackground().add(gameController.getView(), BorderLayout.CENTER);
                c1.show(view.getContentPane(), MainFrame.Cards.GAME.name());
                gameController.setVisible(true);
                view.setVisible(true);
            }
        }
        currentPanel = panel;
    }

    public void setSettingsReturnPanel(){
        if (currentPanel != Panels.SETTINGS) settingsController.setReturnPanel(currentPanel);
    }

    public void createNewGame(GameChoiceController.GameMode gameMode)
    {
        ViewPlayer[] viewPlayers = new ViewPlayer[]{
                viewPlayer,
                new ViewPlayer(new AIPlayer("Ai 1")),
                new ViewPlayer(new AIPlayer("Ai 2")),
                new ViewPlayer(new AIPlayer("Ai 3")),
        };

        gameController = new GamePanelController(gameMode, viewPlayers);
        setVisiblePanel(MainFrameController.Panels.GAME);
    }

    public void quitGame(){
        gameController.quitGame();
        profileController.getView().update();
        view.getGameBackground().remove(gameController.getView());
        view.dispose();
        view.setUndecorated(false);
        view.pack();
        view.getContentPane().repaint();
        view.setSize(view.getDimension());
        view.setLocationRelativeTo(null);
        view.setVisible(true);
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

    public ViewPlayer getViewPlayer() { return viewPlayer; }

    public void setViewPlayer(ViewPlayer viewPlayer) { this.viewPlayer = viewPlayer; }
}
