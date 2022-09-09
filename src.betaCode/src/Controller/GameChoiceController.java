package Controller;

import Controller.Utilities.AudioManager;
import View.Elements.CustomMouseAdapter;
import View.Pages.GameChoicePanel;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;

/**
 * Specialize {@link Controller} on {@link GameChoicePanel}.
 * Class used to get the inputs from the user in the {@link GameChoicePanel}.
 * @author D'annibale Luigi, Venturini Daniele
 */
public class GameChoiceController extends Controller<GameChoicePanel>
{
    /**
     * The selectable game-modes:
     * <ul>
     *     <li>CLASSIC_RULES</li>
     *     <li>MEME_RULES</li>
     *     <li>SEVENO_RULES</li>
     * </ul>
     */
    public enum GameMode
    {
        CLASSIC_RULES,
        MEME_RULES,
        SEVENO_RULES
    }

    /**
     * Creates an associated view ({@link GameChoicePanel}), updating the displayed values and adding the listeners.
     */
    public GameChoiceController()
    {
        super(new GameChoicePanel());

        URI filePath = new File("resources/rules.pdf").toURI();

        Arrays.stream(GameChoiceController.GameMode.values()).forEach(gameMode -> {
            view.getGameModes()[gameMode.ordinal()].addMouseListener(new CustomMouseAdapter(){
                @Override
                public void mouseReleased(MouseEvent e)
                {
                    super.mouseReleased(e);
                    createNewGame(gameMode);
                }
            });
            view.getInfoLabels()[gameMode.ordinal()].addMouseListener(new CustomMouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e)
                {
                    super.mouseEntered(e);
                    view.setCurrentInfo(gameMode.ordinal());
                    view.repaint();
                }

                @Override
                public void mouseExited(MouseEvent e)
                {
                    view.setCurrentInfo(-1);
                    view.repaint();
                }

                @Override
                public void mouseReleased(MouseEvent e)
                {
                    super.mouseReleased(e);
                    try { Desktop.getDesktop().browse(filePath); }
                    catch (IOException ex)  { System.out.println("CANNOT OPEN FILE " + filePath); }
                }
            });
        });

        view.getBack().addMouseListener(new CustomMouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                MainFrameController.getInstance().setVisiblePanel(MainFrameController.Panels.STARTMENU);
            }
        });
    }

    /**
     * Interacts with the {@link MainFrameController} delegating him to create a new game, <br/>
     * as the user choice it in this associated view.
     * @param gameMode
     */
    private void createNewGame(GameMode gameMode)
    {
        AudioManager.getInstance().setFolder(gameMode.name());
        MainFrameController.getInstance().createNewGame(gameMode);
    }
}
