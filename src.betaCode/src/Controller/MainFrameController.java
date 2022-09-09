package Controller;

import Controller.Utilities.AudioManager;
import Controller.Utilities.Config;
import Controller.Utilities.DataAccessManager;
import Model.Players.AIPlayer;
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

/**
 * Specialize {@link Controller} on {@link MainFrame}.
 * Class used to manage the communication between all the controllers.
 * It starts the actual game and sets the current visible panel. <br>
 * This class uses the Singleton Pattern because this class is used in every {@link Controller}
 * @author D'annibale Luigi, Venturini Daniele
 */
public class MainFrameController extends Controller<MainFrame>
{
    /**
     * Panels :
     * <ul>
     *     <li>STARTMENU</li>
     *     <li>SETTINGS</li>
     *     <li>GAMECHOICE</li>
     *     <li>PROFILE</li>
     *     <li>GAME</li>
     * </ul>
     */
    public enum Panels
    {
        STARTMENU,
        SETTINGS,
        GAMECHOICE,
        PROFILE,
        GAME
    }

    private static MainFrameController instance;
    private final String imagesPath = "resources/images/MainFrame/MainframeDesignElements/";
    private StartingMenuController startingMenuController;
    private SettingsController settingsController;
    private GameChoiceController gameChoiceController;
    private GamePanelController gameController;
    private ProfilePanelController profileController;
    private ViewPlayer viewPlayer;
    private Panels currentPanel;

    /**
     * @return the static instance of the {@link MainFrameController}. If is null, then the class is instantiated with all its components.
     */
    public static MainFrameController getInstance()
    {
        if (instance == null)
        {
            instance = new MainFrameController();
            instance.initializeMainFrameController();
        }
        return instance;
    }

    /**
     * Creates a new {@link MainFrameController} with its associated view ({@link MainFrame}). Used by the getInstance method.
     */
    private MainFrameController()
    { super(new MainFrame()); }

    /**
     * Loads the last used {@link Config} and initialize all the other controllers
     */
    private void initializeMainFrameController()
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

        AudioManager.getInstance().setAudio(AudioManager.Music.CALM_BACKGROUND);
    }

    /**
     * Asks the user if he is sure to close the game
     * @return the choice made
     */
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

    /**
     * It hides the current visible {@link Panels} and shows the given {@link Panels}
     * @param panel the panel to show
     */
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
                //Prepare the view to start the game
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

    /**
     * Sets the return panel of the {@link SettingsController}
     */
    public void setSettingsReturnPanel()
    {
        if (currentPanel != Panels.SETTINGS)
            settingsController.setReturnPanel(currentPanel);
    }

    /**
     * Creates a new {@link GamePanelController} with the given {@link Controller.GameChoiceController.GameMode}
     * @param gameMode
     */
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

    /**
     * Closes the game and returns the {@link MainFrame} to its previous size
     */
    public void quitGame()
    {
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

    /**
     * Calls the {@link WindowEvent} Window closing to ask the user if he is sure to close the game
     */
    public void closeWindow()
    {
        try //If the parent has a listener that manages the window closing it links that event, if an exception is generated the frame just get closed
        {
            view.dispatchEvent(new WindowEvent(view, WindowEvent.WINDOW_CLOSING));
        }
        catch(Exception ex)
        { view.dispose(); }
    }

    public ViewPlayer getViewPlayer() { return viewPlayer; }

    public void setViewPlayer(ViewPlayer viewPlayer) { this.viewPlayer = viewPlayer; }
}
