package Controller;

import Utilities.AudioManager;
import View.Elements.CustomMouseAdapter;
import View.Pages.GameChoicePanel;

import java.awt.event.MouseEvent;

public class GameChoiceController extends Controller<GameChoicePanel>
{
    public enum GameMode{
        CLASSIC_RULES,
        MEME_RULES,
        SEVENO_RULES
    }

    public GameChoiceController(MainFrameController mainFrame)
    {
        super(new GameChoicePanel());

        view.getBasicGame().addMouseListener(new CustomMouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                createNewGame(mainFrame, GameMode.CLASSIC_RULES);
            }
        });
        view.getMemeGame().addMouseListener(new CustomMouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                createNewGame(mainFrame, GameMode.MEME_RULES);
            }
        });
        view.getSevenoGame().addMouseListener(new CustomMouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                createNewGame(mainFrame, GameMode.SEVENO_RULES);
            }
        });
        view.getBack().addMouseListener(new CustomMouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                mainFrame.setVisiblePanel(MainFrameController.Panels.STARTMENU);
            }
        });
    }

    private void createNewGame(MainFrameController mainFrame, GameMode gameMode)
    {
        AudioManager.getInstance().setFolder(gameMode.name());
        mainFrame.createNewGame(gameMode);
    }
}
