package Controller;

import Utilities.AudioManager;
import View.Elements.CustomMouseAdapter;
import View.Pages.GameChoicePanel;

import java.awt.event.MouseEvent;
import java.util.Arrays;

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

        Arrays.stream(GameChoiceController.GameMode.values()).forEach(gameMode -> {
            view.getGameModes()[gameMode.ordinal()].addMouseListener(new CustomMouseAdapter(){
                @Override
                public void mouseReleased(MouseEvent e) {
                    super.mouseReleased(e);
                    createNewGame(mainFrame, gameMode);
                }
            });
            view.getInfoLabels()[gameMode.ordinal()].addMouseListener(new CustomMouseAdapter(){
                @Override
                public void mouseReleased(MouseEvent e) {
                    super.mouseReleased(e);
                    System.out.println(gameMode);
                }
            });
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
