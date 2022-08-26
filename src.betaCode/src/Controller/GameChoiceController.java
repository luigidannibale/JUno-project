package Controller;

import View.Pages.GameChoicePanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameChoiceController {
    public enum GameMode{
        CLASSIC,
        MEME,
        SEVENO
    }

    GameChoicePanel view;

    public GameChoiceController(MainFrameController mainFrame){
        view = new GameChoicePanel();

        view.getBasicGame().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //mainFrame.setVisiblePanel(MainFrame.Panels.GAME);
                createNewGame(mainFrame, GameMode.CLASSIC);
            }
        });

        view.getMemeGame().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                createNewGame(mainFrame, GameMode.MEME);
            }
        });

        view.getSevenoGame().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                createNewGame(mainFrame, GameMode.SEVENO);
            }
        });

        view.getIndietro().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainFrame.setVisiblePanel(MainFrameController.Panels.STARTMENU);
            }
        });
    }

    private void createNewGame(MainFrameController mainFrame, GameMode gameMode){
        mainFrame.createNewGame(gameMode);
    }

    public GameChoicePanel getView() {
        return view;
    }

    public void setVisible(boolean visible){
        view.setVisible(visible);
    }
}
