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

public class GameChoiceController extends Controller<GameChoicePanel>
{
    public enum GameMode{
        CLASSIC_RULES,
        MEME_RULES,
        SEVENO_RULES
    }

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

    private void createNewGame(GameMode gameMode)
    {
        AudioManager.getInstance().setFolder(gameMode.name());
        MainFrameController.getInstance().createNewGame(gameMode);
    }
}
