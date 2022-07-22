package Controller;

import View.GameChoicePanel;
import View.MainFrame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameChoiceController {
    public enum GameMode{
        CLASSIC,
        MEME,
        SEVENO
    }

    GameChoicePanel view;

    public GameChoiceController(MainFrame mainFrame){
        view = new GameChoicePanel(mainFrame);

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
                mainFrame.setVisiblePanel(MainFrame.Panels.STARTMENU);
                view.setVisible(false);
            }
        });
    }

    private void createNewGame(MainFrame mainFrame, GameMode gameMode){
        mainFrame.createNewGame(gameMode);
        view.setVisible(false);
    }

    public GameChoicePanel getView() {
        return view;
    }

    public void setVisible(boolean visible){
        view.setVisible(visible);
    }
}
