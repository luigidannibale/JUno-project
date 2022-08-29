package Controller;

import Utilities.AudioManager;
import View.Pages.GameChoicePanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameChoiceController {
    public enum GameMode{
        CLASSIC_RULES,
        MEME_RULES,
        SEVENO_RULES
    }

    GameChoicePanel view;

    public GameChoiceController(MainFrameController mainFrame){
        view = new GameChoicePanel();

        view.getBasicGame().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //mainFrame.setVisiblePanel(MainFrame.Panels.GAME);
                createNewGame(mainFrame, GameMode.CLASSIC_RULES);
            }
        });

        view.getMemeGame().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                createNewGame(mainFrame, GameMode.MEME_RULES);
            }
        });

        view.getSevenoGame().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                createNewGame(mainFrame, GameMode.SEVENO_RULES);
            }
        });

        view.getIndietro().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainFrame.setVisiblePanel(MainFrameController.Panels.STARTMENU);
            }
        });
    }

    private void createNewGame(MainFrameController mainFrame, GameMode gameMode)
    {
        AudioManager.getInstance().setFolder(gameMode.name());
        mainFrame.createNewGame(gameMode);
    }

    public GameChoicePanel getView() { return view; }

    public void setVisible(boolean visible){
        view.setVisible(visible);
    }
}
